package test.servlets;

import test.util.PropertyReader;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/readConfig")
public class ReadConfigServlet extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        PropertyReader.readConfig();
        resp.setHeader("Message", "Configuration read successfully");
    }
}