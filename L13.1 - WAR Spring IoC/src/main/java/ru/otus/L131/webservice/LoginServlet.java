package ru.otus.L131.webservice;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginServlet extends HttpServlet {
    public static final String LOGIN_PARAMETER_NAME = "login";

    private static final String ADMIN_NAME = "admin";

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String requestLogin = request.getParameter(LOGIN_PARAMETER_NAME);

        if (requestLogin != null && ADMIN_NAME.equals(requestLogin)) {
            saveToSession(request, requestLogin);
            response.sendRedirect("/admin");
            new Utils().setOk(response);
        } else {
            response.sendRedirect("/accessDenied.html");
            new Utils().setFORBIDDEN(response);
        }
    }

    private void saveToSession(HttpServletRequest request, String requestLogin) {
        request.getSession().setAttribute("login", requestLogin);
    }
}
