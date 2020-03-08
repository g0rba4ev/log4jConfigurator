package test.util;

import test.models.Appender;
import test.models.Logger;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class PropertyWriter {

    /**
     * field stores set of appenders that already written
     * during {@link PropertyWriter#saveProps()}
     */
    private static Set<Appender> writtenAppenders = new HashSet<>();

    public static void main(String[] args) {
        PropertyReader.readProps();
        saveProps();
    }
    /**
     * save log4j configuration to {@link PropertyReader#PATH_TO_LOG4J_PROPERTIES} file
     */
    public static void saveProps() {
        PropertyWriter.writtenAppenders.clear();

        String fileName = PropertyReader.PATH_TO_LOG4J_PROPERTIES;
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) {

            //write first line: "log4j.debug=true" or "log4j.debug=false"
            bw.write(PropertyReader.DEBUG_PREFIX + "=" + PropertyReader.debug.toString());
            //write rootLogger and its appenders
            writeLogger(bw, PropertyReader.getRootLogger());

            Map<String, Logger> loggerMap = PropertyReader.getLoggerMap();
            for (String key : loggerMap.keySet()) {
                writeLogger(bw, loggerMap.get(key)); // TODO add property ordering
            }
            bw.flush();// ASK is required bw.flush() here?
        } catch (IOException e) {
            //TODO handle EXCEPTION
        }
    }

    /**
     * write {@code logger} and its appenders
     * @param bw BufferedWriter for writing logger
     * @param logger that'll be written
     * @throws IOException
     */
    private static void writeLogger(BufferedWriter bw, Logger logger) throws IOException {
        String loggerName = logger.getName();
        String key;
        if(loggerName.equals("rootLogger")) {
            key = PropertyReader.ROOT_LOGGER_PREFIX;
        } else {
            key = PropertyReader.LOGGER_PREFIX + loggerName;
        }

        StringBuilder valueBuilder = new StringBuilder();
        valueBuilder.append(logger.getLevel());
        Set<Appender> appenderSet = logger.getAppenderSet();
        if (!appenderSet.isEmpty()) {
            for (Appender a : appenderSet){
                valueBuilder.append(", ");
                valueBuilder.append(a.getName());
            }
        }
        String value = valueBuilder.toString();

        bw.newLine();
        bw.newLine(); // empty string before each logger
        bw.write(key + "=" + value);
        // write additivity for all loggers except rootLogger
        if(!key.equals(PropertyReader.ROOT_LOGGER_PREFIX)) {
            String additivityKey = PropertyReader.ADDITIVITY_PREFIX + loggerName;
            String additivityValue = logger.getAdditivity().toString();
            bw.newLine();
            bw.write(additivityKey + "=" + additivityValue);
        }

        writeAppendersAfterLogger(bw, logger);
    }

    /**
     * write all appenders that connected to {@code logger}
     * @param bw BufferedWriter for writing appenders of {@code logger}
     * @param logger whose properties will be written
     * @throws IOException
     */
    private static void writeAppendersAfterLogger(BufferedWriter bw, Logger logger) throws IOException {
        Set<Appender> appenderSet = logger.getAppenderSet();
        if (!appenderSet.isEmpty()) {
            for (Appender a : appenderSet){
                bw.newLine(); // empty string before each appender
                writeAppender(bw, a);
            }
        }
    }

    /**
     * write appender with its properties (only if it was not written earlier)
     * @param bw BufferedWriter for writing appenders of {@code logger}
     * @param appender whose properties will be written
     * @throws IOException
     */
    private static void writeAppender(BufferedWriter bw, Appender appender) throws IOException {
        if ( isAppenderAlreadyWritten(appender) )
            return;

        bw.newLine();
        bw.write(PropertyReader.APPENDER_PREFIX + appender.getName() + "=" + appender.getAppenderType());

        String prefix = PropertyReader.APPENDER_PREFIX + appender.getName() + ".";
        Map<String, String> propsMap = appender.getPropsMap();
        for (String s : propsMap.keySet()) {
            String key = prefix + s; // TODO add property ordering
            String value = propsMap.get(s);
            bw.newLine();
            bw.write(key + "=" + value);
        }
    }

    /**
     * check - is appender already written or not, and if appender isn't written -
     * add appender to written appenders set {@link PropertyWriter#writtenAppenders}
     * @param appender inspected appender
     * @return {@code true} if appender already written, otherwise {@code false}
     */
    private static boolean isAppenderAlreadyWritten(Appender appender) {
        if ( writtenAppenders.contains(appender) ) {
            return true;
        } else {
            writtenAppenders.add(appender);
            return false;
        }
    }
}
