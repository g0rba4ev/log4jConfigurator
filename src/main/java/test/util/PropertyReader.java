package test.util;

import test.models.Appender;
import test.models.Logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class PropertyReader {

    /**
     * value of parameter "log4j.debug"
     */
    static Boolean debug;
    static Logger rootLogger;
    static Map<String, Logger> loggerMap = new HashMap<>();
    static Map<String, Appender> appenderMap = new HashMap<>();

    static final String PATH_TO_LOG4J_PROPERTIES = "d:\\temp\\Java\\log4j.properties";

    static final String          DEBUG_PREFIX = "log4j.debug";
    static final String         LOGGER_PREFIX = "log4j.logger.";
    static final String     ADDITIVITY_PREFIX = "log4j.additivity.";
    static final String    ROOT_LOGGER_PREFIX = "log4j.rootLogger";
    static final String       APPENDER_PREFIX = "log4j.appender.";

    public static Map<String, Appender> getAppenderMap() {
        return appenderMap;
    }

    public static Map<String, Logger> getLoggerMap() {
        return loggerMap;
    }

    public static Logger getRootLogger() {
        return rootLogger;
    }

    public static void setRootLogger(Logger rootLogger) {
        PropertyReader.rootLogger = rootLogger;
    }

    /**
     * read configuration (properties) from {@link PropertyReader#PATH_TO_LOG4J_PROPERTIES} and
     * fill {@link PropertyReader#loggerMap} and {@link PropertyReader#appenderMap}
     *
     * (analog doConfigure(...) in log4j)
     */
    public static void readConfig() {
        loggerMap.clear();
        appenderMap.clear();

        Properties props = readLog4jProperties(PATH_TO_LOG4J_PROPERTIES);

        String debugValue = props.getProperty(DEBUG_PREFIX);
        debug = debugValue.equalsIgnoreCase("true");

        rootLogger = parseRootLogger(props);
        for (String key : props.stringPropertyNames()) {
            if(key.startsWith(LOGGER_PREFIX)) {
                Logger logger = parseLogger(props, key);
                parseAdditivityForLogger(props, logger);

                loggerMap.put(logger.getName(), logger);
            }
        }
    }

    private static Logger parseRootLogger(Properties props) {
        return parseLogger(props, ROOT_LOGGER_PREFIX);
    }

    /**
     * Parse Logger (also known as "Category") - parse its properties
     * and its appenders (by {@link PropertyReader#parseAppender}).
     * This method must work for the root category as well.
     * @param props properties from log4j.properties
     * @param key key for logger in the form {@link PropertyReader#LOGGER_PREFIX} + "loggerName"
     *            (e.g. log4j.logger.loggerName) OR for the root logger - {@link PropertyReader#ROOT_LOGGER_PREFIX}
     * @return parsed Logger
     */
    private static Logger parseLogger(Properties props, String key) {
        String loggerName;
        if (key.equals(ROOT_LOGGER_PREFIX)) {
            loggerName = "rootLogger";
        } else {
            loggerName = key.substring(LOGGER_PREFIX.length());
        }
        Logger logger = new Logger(loggerName);

        String value = props.getProperty(key);
        StringTokenizer st = new StringTokenizer(value, ",");

        if( value.equals("") )
            return logger;

        if ( !value.startsWith(",") ){
            try {
                String level = st.nextToken();
                logger.setLevel(level);
            } catch (NoSuchElementException e) {
                return logger;
            }
        }

        Appender appender;
        String appenderAlias;
        while(st.hasMoreTokens()) {
            appenderAlias = st.nextToken().trim();
            if(appenderAlias.equals(","))
                continue;

            appender = parseAppender(props, appenderAlias);
            if(appender != null) {
                logger.addAppender(appender);
            }
        }
        return logger;
    }

    /**
     * Parse the additivity option for a non-root logger
     * @param props properties from log4j.properties
     * @param logger logger
     */
    private static void parseAdditivityForLogger(Properties props, Logger logger) {
        String additivityStr = props.getProperty(ADDITIVITY_PREFIX + logger.getName());
        // touch additivity only if necessary
        if((additivityStr != null) && (!additivityStr.equals(""))) {
            /*
            change additivity only if additivityStr == "FALSE",
            otherwise don't change (additivity remains default - TRUE)
             */
            if(additivityStr.equalsIgnoreCase("false"))
                logger.setAdditivity(false);
        }
    }

    /**
     * Parse appender with {@code appenderAlias}
     * @param props properties from log4j.properties
     * @param appenderAlias appender name
     * @return parsed Appender
     */
    private static Appender parseAppender(Properties props, String appenderAlias) {
        if (appenderMap.containsKey(appenderAlias)) {
            // Appender was previously initialized
            return appenderMap.get(appenderAlias);
        } else {
            // Appender was not previously initialized.
            String appenderType = props.getProperty(APPENDER_PREFIX + appenderAlias);
            Appender appender = new Appender(appenderAlias, appenderType);

            String prefix = APPENDER_PREFIX + appenderAlias + ".";
            int len = prefix.length();

            for (String key : props.stringPropertyNames()){
                // handle only properties that start with the desired prefix.
                if (key.startsWith(prefix)) {
                    appender.addProperty(key.substring(len), props.getProperty(key));
                }
            }

            appenderMap.put(appender.getAlias(), appender);
            return appender;
        }
    }

    /**
     * read all properties from log4j.properties file
     * @param path path to log4j.properties in your project
     * @return Properties
     */
    private static Properties readLog4jProperties(String path) {
        Properties props = new Properties();
        File log4jFile = new File(path);
        try(FileReader log4jProperties = new FileReader(log4jFile)){
            props.load(log4jProperties);
        } catch (FileNotFoundException fnfe) {
            //TODO add exception handling
        } catch (IOException e) {
            ////TODO add exception handling
        }
        return props;
    }

    /**
     * get logger by name
     * @param loggerName name of required logger
     * @return {@link PropertyReader#rootLogger} or logger by name from {@link PropertyReader#loggerMap}
     */
    public static Logger getLoggerByName(String loggerName) {
        if ( loggerName.equalsIgnoreCase("rootLogger") ) {
            return rootLogger;
        } else {
            return loggerMap.get(loggerName);
        }
    }
}
