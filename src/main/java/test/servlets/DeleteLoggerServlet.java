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
import java.util.Map;

@WebServlet("/deleteLogger")
public class DeleteLoggerServlet extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {

        String loggerName = req.getParameter("loggerName");
        Map<String, Logger> loggerMap = PropertyReader.getLoggerMap();

        if ( loggerMap.containsKey(loggerName) ) {
            loggerMap.remove(loggerName);
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.setHeader("Message","Logger deleted successful");
        } else {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.setHeader("Message","Logger with this name not found");
        }

    }

}
