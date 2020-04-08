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

@WebServlet("/attachNewAppender")
public class AttachNewServlet extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String loggerName = req.getParameter("loggerName");
        String appenderAlias = req.getParameter("appenderAlias");
        String appenderType = req.getParameter("appenderType");

        if ( loggerName.equals("") || appenderAlias.equals("") || appenderType.equals("") ){
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.setHeader("Message","One of the parameters is empty");
            return;
        }

        Logger logger = PropertyReader.getLoggerByName(loggerName);
        Map<String, Appender> appenderMap = PropertyReader.getAppenderMap();

        if ( appenderMap.containsKey(appenderAlias) ) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.setHeader("Message","Appender with this alias already exist");
            return;
        }

        if (logger == null) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.setHeader("Message","Logger is not found");
        } else {
            Appender newAppender = new Appender(appenderAlias, appenderType); // create new appender
            logger.getAppenderSet().add( newAppender ); // add to logger
            appenderMap.put( newAppender.getAlias(), newAppender ); // add to appender map

            resp.setStatus(HttpServletResponse.SC_OK);
            resp.setHeader("Message","Appender created and attached successfully");
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            resp.getWriter().print( newAppender.toJSON() );

        }
    }

}
