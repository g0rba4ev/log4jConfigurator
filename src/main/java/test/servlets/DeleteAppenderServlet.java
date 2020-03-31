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
import java.util.Map;

@WebServlet("/deleteAppender")
public class DeleteAppenderServlet extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");

        String appenderAlias = req.getParameter("appenderAlias");
        Map<String, Appender> appenderMap = PropertyReader.getAppenderMap();

        String respStatus;
        String respMessage;

        if ( appenderMap.containsKey(appenderAlias) ) {
            Appender appender = appenderMap.remove(appenderAlias);

            PropertyReader.getRootLogger().getAppenderSet().remove(appender);
            for (Logger logger : PropertyReader.getLoggerMap().values()) {
                logger.getAppenderSet().remove(appender);
            }
            respStatus = "Success";
            respMessage = "Appender deleted successfully";
        } else {
            respStatus = "Error";
            respMessage = "Appender with this alias not found";
        }

        ObjectMapper mapper = new ObjectMapper();
        ObjectNode respObj = mapper.createObjectNode();
        respObj.put("status", respStatus);
        respObj.put("message", respMessage);
        String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(respObj);

        resp.getWriter().print(json);
    }
}
