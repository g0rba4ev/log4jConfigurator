package test.servlets;

import test.classes.ClassScanner;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/getClassNames")
public class AutoCompleteServlet extends HttpServlet {
    final int numRequiredSuggestions = 7;

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        String term = req.getParameter("term");

        List<String> tempList = ClassScanner.getClassNames("d:/temp/Java/test2/");
        List<String> suggestions = new ArrayList<>();
        for (String className: tempList){
            if(suggestions.size() == numRequiredSuggestions){
                break;
            }
            if (className.toLowerCase().contains(term.toLowerCase())) {
                    suggestions.add(className);
                }

        }

        //build JSON full of values for autocomplete
        StringBuffer jsonResponse = new StringBuffer("[");
        for(String s: suggestions){
            jsonResponse.append("\"" + s + "\", ");
        }

        jsonResponse.deleteCharAt(jsonResponse.length()-1);
        jsonResponse.deleteCharAt(jsonResponse.length()-1);
        jsonResponse.append("]");

        resp.getWriter().write(jsonResponse.toString());
    }
}
