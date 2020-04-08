package test.servlets.attachAppender;

import test.models.Appender;
import test.models.Logger;
import test.util.PropertyReader;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

@WebServlet("/attachExistingAppender")
public class AttachExistingServlet extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String loggerName = req.getParameter("loggerName");
        String appenderAlias = req.getParameter("appenderAlias");

        if ( loggerName.equals("") || appenderAlias.equals("") ){
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.setHeader("Message","One of the parameters is empty");
            return;
        }

        Logger logger = PropertyReader.getLoggerByName(loggerName);
        Appender appender = PropertyReader.getAppenderMap().get(appenderAlias);

        if (logger == null) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.setHeader("Message","Logger is not found");
        } else if (appender == null) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.setHeader("Message","Appender is not found");
        } else {
            Set<Appender> appenderSet = logger.getAppenderSet();
            if ( appenderSet.contains(appender) ) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.setHeader("Message","This appender already attached to logger");
            } else {
                appenderSet.add(appender);
                resp.setStatus(HttpServletResponse.SC_OK);
                resp.setHeader("Message","Appender attached successfully");
                resp.setContentType("application/json");
                resp.setCharacterEncoding("UTF-8");
                resp.getWriter().print( appender.toJSON() );
            }
        }
    }

}
