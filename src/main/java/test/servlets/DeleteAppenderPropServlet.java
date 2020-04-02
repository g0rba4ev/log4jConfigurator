package test.servlets;

import test.models.Appender;
import test.util.PropertyReader;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/deleteAppenderProp")
public class DeleteAppenderPropServlet extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {

        String appenderAlias = req.getParameter("appenderAlias");
        String propertyKey = req.getParameter("propertyKey");

        try {
            Appender appender = PropertyReader.getAppenderMap().get(appenderAlias);
            if ( appender.getPropsMap().remove(propertyKey) != null) {
                resp.setHeader("Message","Property deleted successfully");
            } else {
                resp.setHeader("Message","Appender does not contain such a property");
            }
        } catch (NullPointerException npe) {
            resp.setHeader("Message","Null pointer exception: appender not found");
        }
    }

}
