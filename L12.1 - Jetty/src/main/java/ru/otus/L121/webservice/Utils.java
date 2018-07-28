package ru.otus.L121.webservice;

import javax.servlet.http.HttpServletResponse;

public class Utils {
    public void setFORBIDDEN(HttpServletResponse response) {
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
    }

    public void setOk(HttpServletResponse response) {
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
