package test.servlets.autocomplete;

import test.util.ClassScanner;
import test.util.SuggestionsBuilder;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/getClassNames")
public class AutoCompleteServlet extends HttpServlet {
    private static final int NUM_REQUIRED_SUGGESTIONS = 7;

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        String term = req.getParameter("term");

        List<String> tempList = ClassScanner.getClassNames("d:/temp/Java/test1/");
        String json = SuggestionsBuilder.getJson(term, tempList, NUM_REQUIRED_SUGGESTIONS);

        resp.getWriter().write(json);
    }

}