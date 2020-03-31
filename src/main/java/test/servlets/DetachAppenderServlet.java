package test.servlets;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ObjectNode;
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

        String respStatus = null;
        String respMessage = null;

        Iterator<Appender> iter = appenderSet.iterator();
        if( appenderSet.isEmpty() ) {
            respStatus = "Error";
            respMessage = "Logger has no attached appenders";
        } else {
            while( iter.hasNext() ) {
                Appender appender = iter.next();
                if ( appender.getAlias().equals(detachAppenderAlias) ) {
                    appenderSet.remove(appender);
                    respStatus = "Success";
                    respMessage = "Appender detached successful";
                    break;
                }
                if ( !iter.hasNext() ) {
                    respStatus = "Error";
                    respMessage = "Detached appender not found";
                }
            }
        }


        ObjectMapper mapper = new ObjectMapper();
        ObjectNode respObj = mapper.createObjectNode();
        respObj.put("status", respStatus);
        respObj.put("message", respMessage);
        String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(respObj);

        resp.getWriter().print(json);
    }

}
