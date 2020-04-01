package test.servlets;
import test.models.Appender;
import test.models.Logger;
import test.util.PropertyReader;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Iterator;
import java.util.Set;

@WebServlet("/detachAppender")
public class DetachAppenderServlet extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setContentType("application/json");

        String loggerName = req.getParameter("loggerName");
        String detachAppenderAlias = req.getParameter("appenderAlias");

        Logger logger = PropertyReader.getLoggerByName(loggerName);
        Set<Appender> appenderSet = logger.getAppenderSet();

        Iterator<Appender> iter = appenderSet.iterator();
        if( appenderSet.isEmpty() ) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.setHeader("Message", "Logger has no attached appenders");
        } else {
            while( iter.hasNext() ) {
                Appender appender = iter.next();
                if ( appender.getAlias().equals(detachAppenderAlias) ) {
                    appenderSet.remove(appender);
                    resp.setStatus(HttpServletResponse.SC_OK);
                    resp.setHeader("Message","Appender detached successful");
                    break;
                }
                if ( !iter.hasNext() ) {
                    resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    resp.setHeader("Message","Detached appender with this alias not found");
                }
            }
        }
    }

}
