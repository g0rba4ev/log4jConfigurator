package test.servlets;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ArrayNode;
import test.util.ClassScanner;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/getClassNames")
public class AutoCompleteServlet extends HttpServlet {
    private static final int NUM_REQUIRED_SUGGESTIONS = 7;

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        String term = req.getParameter("term");

        List<String> tempList = ClassScanner.getClassNames("d:/temp/Java/test1/");
        List<String> suggestions = new ArrayList<>();
        for (String className: tempList){
            if(suggestions.size() == NUM_REQUIRED_SUGGESTIONS){
                break;
            }
            if (className.toLowerCase().contains(term.toLowerCase())) {
                    suggestions.add(className);
            }

        }

        //build JSON full of values for autocomplete
        ObjectMapper mapper = new ObjectMapper();
        ArrayNode suggestionArrNode = mapper.createArrayNode();
        for(String s: suggestions)
            suggestionArrNode.add(s);

        resp.getWriter().write(mapper.writeValueAsString(suggestionArrNode));
    }
}
