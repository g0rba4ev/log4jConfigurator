package test.servlets;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.node.ObjectNode;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

import test.models.Appender;
import test.models.Logger;
import test.util.PropertyReader;

@WebServlet("/getLoggerParam")
public class GetLoggerParamServlet extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        String json = createJSONLoggerProp(req.getParameter("loggerName"));

        resp.getWriter().print(json);
    }


    /**
     * creates the JSON with properties of this logger
     * @param loggerName name of logger (usually match with class name)
     * @return JSON with properties of this logger
     */
    private String createJSONLoggerProp(final String loggerName) {

        //check - is exist a logger with that name
        Logger logger;
        if(loggerName.equalsIgnoreCase("rootLogger")){
            logger = PropertyReader.getRootLogger();
        } else {
            logger = PropertyReader.getLoggerMap().get(loggerName);

        }
        if(logger == null)
            return "[{\"Error\": \"Logger is not registered\"}]";
        //for building json
        ObjectMapper mapper = new ObjectMapper();

        ArrayNode rootNode = mapper.createArrayNode();
        ObjectNode loggerNode = mapper.createObjectNode();
        ArrayNode appendersNamesNode = mapper.createArrayNode();

        rootNode.add(loggerNode);

        loggerNode.put("Name", logger.getName());
        loggerNode.put("Additivity", logger.getAdditivity().toString());
        loggerNode.put("Level",logger.getLevel());
        loggerNode.put("Appenders", appendersNamesNode);

        for (Appender appender: logger.getAppenderSet()) {
            ObjectNode appenderNode = mapper.createObjectNode();

            appenderNode.put("Alias", appender.getAlias());
            appenderNode.put("Appender", appender.getAppenderType());

            Map<String, String> appenderPropsMap = appender.getPropsMap();
            for(String key : appender.getPropsMap().keySet()){
                appenderNode.put(key, appenderPropsMap.get(key));
            }

            appendersNamesNode.add( appender.getAlias());
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

}
