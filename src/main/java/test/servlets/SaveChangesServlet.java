package test.servlets;

import test.util.PropertyWriter;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/saveChanges")
public class SaveChangesServlet extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        PropertyWriter.saveConfig();
        resp.setHeader("Message", "Properties saved successfully");
    }

}