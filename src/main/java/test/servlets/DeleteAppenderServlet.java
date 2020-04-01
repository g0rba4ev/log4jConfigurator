package test.servlets;

import test.models.Appender;
import test.models.Logger;
import test.util.PropertyReader;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@WebServlet("/deleteAppender")
public class DeleteAppenderServlet extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        resp.setContentType("application/json");

        String appenderAlias = req.getParameter("appenderAlias");
        Map<String, Appender> appenderMap = PropertyReader.getAppenderMap();

        if ( appenderMap.containsKey(appenderAlias) ) {
            Appender appender = appenderMap.remove(appenderAlias);

            PropertyReader.getRootLogger().getAppenderSet().remove(appender);
            for (Logger logger : PropertyReader.getLoggerMap().values()) {
                logger.getAppenderSet().remove(appender);
            }
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.setHeader("Message","Appender deleted successful");
        } else {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.setHeader("Message","Appender with this alias not found");
        }

    }
}