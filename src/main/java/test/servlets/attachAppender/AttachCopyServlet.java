package test.servlets.attachAppender;

import test.models.Appender;
import test.models.Logger;
import test.util.PropertyReader;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

@WebServlet("/attachCopyAppender")
public class AttachCopyServlet extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String loggerName = req.getParameter("loggerName");
        String existingAppenderAlias = req.getParameter("existingAppenderAlias");
        String newAppenderAlias = req.getParameter("newAppenderAlias");

        if ( loggerName.equals("") || existingAppenderAlias.equals("") || newAppenderAlias.equals("") ){
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.setHeader("Message","One of the parameters is empty");
            return;
        }

        Logger logger = PropertyReader.getLoggerByName(loggerName);
        Map<String, Appender> appenderMap = PropertyReader.getAppenderMap();
        Appender existingAppender = appenderMap.get(existingAppenderAlias);

        if ( appenderMap.containsKey(newAppenderAlias) ) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.setHeader("Message","Appender with this alias already exist");
            return;
        }

        if (logger == null) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.setHeader("Message","Logger is not found");
        } else if (existingAppender == null) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.setHeader("Message","Appender to copy not found");
        } else {
            Appender newAppender = new Appender(existingAppender); // copy existing appender
            newAppender.setAlias(newAppenderAlias);
            logger.getAppenderSet().add(newAppender); // add to logger
            appenderMap.put( newAppender.getAlias(), newAppender); // add to appender map

            resp.setStatus(HttpServletResponse.SC_OK);
            resp.setHeader("Message","Appender attached successfully");
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            resp.getWriter().print( newAppender.toJSON() );

        }
    }

}
