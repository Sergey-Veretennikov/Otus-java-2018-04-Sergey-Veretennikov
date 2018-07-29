package ru.otus.L121.webservice;

import ru.otus.L121.base.UserDataSet;
import ru.otus.L121.cache.CacheEngine;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class AdminServlet extends HttpServlet {
    private static final String ADMIN_PAGE_TEMPLATE = "admin.html";
    private final TemplateProcessor templateProcessor;
    private final CacheEngine<Long, UserDataSet> userDStCache;

    public AdminServlet(TemplateProcessor templateProcessor, CacheEngine<Long, UserDataSet> userDStCache) {
        this.templateProcessor = templateProcessor;
        this.userDStCache = userDStCache;
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (request.getSession().getAttribute("login") != null) {
            response.getWriter().println(getPage(request));
            new Utils().setOk(response);
            System.out.println(userDStCache.getMissCount());
            System.out.println(userDStCache.getHitCount());
        } else {
            response.sendRedirect("/accessDenied.html");
            new Utils().setFORBIDDEN(response);
        }
    }

    private String getPage(HttpServletRequest request) throws IOException {
        final Map<String, Object> pageVariables = createPageVariablesMap(request);
        return templateProcessor.getPage(ADMIN_PAGE_TEMPLATE, pageVariables);
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

        pageVariables.put("hitCount", userDStCache.getHitCount());
        pageVariables.put("missCount", userDStCache.getMissCount());

        return pageVariables;
    }

}
