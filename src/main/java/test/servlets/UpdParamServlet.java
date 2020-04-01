package test.servlets;

import test.models.Appender;
import test.models.Logger;
import test.util.PropertyReader;
import test.util.PropertyWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

@WebServlet("/updParam")
public class UpdParamServlet extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        String objForChange = req.getParameter("objForChange");
        String objName = req.getParameter("objName");
        String key = req.getParameter("key");
        String value = req.getParameter("newValue");
        if ( objForChange.equalsIgnoreCase("Logger") ) {
            changeLogger(objName, key, value);
        } else if (objForChange.equalsIgnoreCase("Appender")) {
            changeAppender(objName, key, value);
        }

        // value will be written to value filed on web-page
        resp.getWriter().print(value);
    }

    private void changeLogger (String loggerName, String key, String value) {
        // it is understood that if a request came here, the specified object exists a priori
        Logger logger = PropertyReader.getLoggerMap().get(loggerName);
        if (key.equalsIgnoreCase("Name")) {
            String newLoggerName = value;
            Map<String, Logger> loggerMap = PropertyReader.getLoggerMap();

            logger.setName(newLoggerName);
            loggerMap.remove(loggerName);
            loggerMap.put(newLoggerName, logger);

        } else if (key.equalsIgnoreCase("Level")) {
            logger.setLevel(value);
        } else if ( key.equalsIgnoreCase("Additivity") ) {
            logger.setAdditivity(Boolean.parseBoolean(value));
        }
    }

    private void changeAppender (String alias, String key, String value) {
        Map<String, Appender> appenderMap = PropertyReader.getAppenderMap();
        // it is understood that if a request came here, the specified object exists a priori
        Appender appender = appenderMap.get(alias);

        if ( key.equalsIgnoreCase("alias") ) {
            String newAlias = value;

            appender.setAlias(newAlias);
            appenderMap.remove(alias);
            appenderMap.put(newAlias, appender);

        } else if ( key.equals("Appender") ) {
            appender.setAppenderType(value);
        } else {
            appender.getPropsMap().put(key, value);
        }
    }
}