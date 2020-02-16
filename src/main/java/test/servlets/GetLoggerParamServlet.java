package test.servlets;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.node.ObjectNode;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Properties;

@WebServlet("/getLoggerParam")
public class GetLoggerParamServlet extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        String json = createJSONLoggerProp(req.getParameter("loggerName"));

        resp.getWriter().print(json);
    }


    /**
     *
     * @param loggerName name of logger (usually match with class name)
     * @return JSON with properties of this logger
     */
    private String createJSONLoggerProp(final String loggerName) {
        //get properties from log4j.properties
        String pathToLog4jProperties = "d:\\temp\\Java\\log4j.properties"; // TODO add setting this path from outside
        Properties log4jProp = readLog4jProperties(pathToLog4jProperties);

        // fields ends with "Key" is a keys for properties in log4j.properties
        final String loggerKey = "log4j.logger." + loggerName;
        final String additivityKey = "log4j.additivity." + loggerName;

        //check - is exist a logger with that name
        String temp = log4jProp.getProperty(loggerKey);
        if(temp == null){
            //TODO replace with json that'll be placed in the table on site
            return "[{\"Error\": \"Logger is not registered\"}]";
        }
        // the field logInfoStr contains the level of the logger and the list of appenders comma-separated
        String logInfoStr = temp.replaceAll(" ", "");
        String[] logInfoArr = logInfoStr.split(",");
        String[] appendersArr = Arrays.copyOfRange(logInfoArr, 1, logInfoArr.length);
        //get logger additivity and check - is it empty (default additivity is true)
        String additivity = log4jProp.getProperty(additivityKey);
        if (additivity == null)
            additivity = "true (default)";

        //for building json
        ObjectMapper mapper = new ObjectMapper();

        ArrayNode rootNode = mapper.createArrayNode();
        ObjectNode loggerNode = mapper.createObjectNode();
        ArrayNode appendersNamesNode = mapper.createArrayNode();

        loggerNode.put("LoggerName", loggerName);
        loggerNode.put("Additivity", additivity);
        loggerNode.put("Level", logInfoArr[0]);
        for(String s : appendersArr)
            appendersNamesNode.add(s);
        loggerNode.put("Appenders", appendersNamesNode);

        rootNode.add(loggerNode);

        for (String appenderName : appendersArr){
            ObjectNode appenderNode = mapper.createObjectNode();
            String appenderKey = "log4j.appender." + appenderName;
            appenderNode.put(appenderName, log4jProp.getProperty(appenderKey));
            for(String s : log4jProp.stringPropertyNames()) {
                if(s.contains(appenderKey) && !(s.equals(appenderKey))){
                    appenderNode.put(s.replace(appenderKey+".", ""), log4jProp.getProperty(s));
                }
            }
            rootNode.add(appenderNode);
        }

        String json;
        try {
            json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(rootNode);
        } catch (IOException ioe) {
            // TODO handle this exception
            json = "";
        }
        return json;
    }

    /**
     * method read all properties from log4j.properties file
     * @param path path to log4j.properties in your project
     * @return Propeties
     */
    private Properties readLog4jProperties (String path){
        Properties prop = new Properties();
        File log4jFile = new File(path);
        try(FileReader log4jProperties = new FileReader(log4jFile)){
            prop.load(log4jProperties);
        } catch (FileNotFoundException fnfe) {
            //TODO add exception handling
        } catch (IOException e) {
            ////TODO add exception handling
        }
        return prop;
    }
}
