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
import java.util.Map;
import java.util.Set;

@WebServlet("/addAppenderProp")
public class AddAppenderPropServlet extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String appenderAlias = req.getParameter("appenderAlias");
        String key = req.getParameter("propertyKey");
        String value = req.getParameter("propertyValue");

        try {
            Appender appender = PropertyReader.getAppenderMap().get(appenderAlias);
            Map<String, String> propsMap = appender.getPropsMap();
            if ( key != null && propsMap.containsKey(key) ) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.setHeader("Message","Property already exist");
            } else if ( key.equals("") || value.equals("") ){
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.setHeader("Message","One of the parameters is empty");
            } else {
                propsMap.put(key, value);
                resp.setHeader("Message","Property added successfully");
            }
        } catch (NullPointerException npe) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.setHeader("Message","Null pointer exception: appender not found");
        }

    }

}
