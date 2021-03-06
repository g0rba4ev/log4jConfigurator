package test.servlets.autocomplete;

import test.util.PropertyReader;
import test.util.SuggestionsBuilder;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

@WebServlet("/getAppenderSuggestions")
public class AppenderSuggestionsServlet extends HttpServlet {

    private static final int NUM_REQUIRED_SUGGESTIONS = 7;

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        String term = req.getParameter("term");
        Set<String> appenderAliases = PropertyReader.getAppenderMap().keySet();
        String json = SuggestionsBuilder.getJson(term, appenderAliases, NUM_REQUIRED_SUGGESTIONS);

        resp.getWriter().write(json);
    }

}
