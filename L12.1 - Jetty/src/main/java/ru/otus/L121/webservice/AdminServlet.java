package ru.otus.L121.webservice;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class AdminServlet extends HttpServlet {
    private static final String ADMIN_PAGE_TEMPLATE = "admin.html";
    private final TemplateProcessor templateProcessor;
    private int hitCount;
    private int missCount;

    public AdminServlet(TemplateProcessor templateProcessor, int hitCount, int missCount) {
        this.templateProcessor = templateProcessor;
        this.hitCount = hitCount;
        this.missCount = missCount;
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (request.getSession().getAttribute("login") != null) {
            response.getWriter().println(getPage(request));
            new Utils().setOk(response);
        } else {
            response.sendRedirect("/accessDenied.html");
            new Utils().setFORBIDDEN(response);
        }
    }

    private String getPage(HttpServletRequest request) throws IOException {
        final Map<String, Object> pageVariables = createPageVariablesMap(request);
        return (String) templateProcessor.getPage(ADMIN_PAGE_TEMPLATE, pageVariables);
    }

    private Map<String, Object> createPageVariablesMap(HttpServletRequest request) {
        Map<String, Object> pageVariables = new HashMap<>();
        pageVariables.put("method", request.getMethod());
        pageVariables.put("URL", request.getRequestURL().toString());
        pageVariables.put("locale", request.getLocale());
        pageVariables.put("sessionId", request.getSession().getId());
        pageVariables.put("parameters", request.getParameterMap().toString());

        String login = (String) request.getSession().getAttribute(LoginServlet.LOGIN_PARAMETER_NAME);
        pageVariables.put("login", login);

        pageVariables.put("hitCount", hitCount);
        pageVariables.put("missCount", missCount);

        return pageVariables;
    }

}
