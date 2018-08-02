package ru.otus.L121;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import ru.otus.L121.base.AddressDataSet;
import ru.otus.L121.base.PhoneDataSet;
import ru.otus.L121.base.UserDataSet;
import ru.otus.L121.cache.CacheEngine;
import ru.otus.L121.cache.CacheEngineImpl;
import ru.otus.L121.dbservice.DBServiceHibernate;
import ru.otus.L121.dbservice.Dbservice;
import ru.otus.L121.webservice.AdminServlet;
import ru.otus.L121.webservice.LoginServlet;
import ru.otus.L121.webservice.TemplateProcessor;

public class Web {
    private final static int PORT = 8095;
    private final static String PUBLIC_HTML = "public_html";

    public static void main(String[] args) throws Exception {
        CacheEngine<Long, UserDataSet> userDStCache = new CacheEngineImpl<>(50, 5000,
                3000, false);

        Dbservice dbservice = new DBServiceHibernate(userDStCache);
        dbservice.startup();
        String status = dbservice.getLocalStatus();
        System.out.println("Status: " + status);

        UserDataSet user1 = new UserDataSet("LEO", 125,
                new AddressDataSet("Truda", 10), new PhoneDataSet("+1 234 567 8018"));
        UserDataSet user2 = new UserDataSet("HENRY", 147,
                new AddressDataSet("Moskovskaya", 45), new PhoneDataSet("+7 987 645 4545"),
                new PhoneDataSet("+67 890 344 4422"));
        UserDataSet user3 = new UserDataSet("DANIEL", 456,
                new AddressDataSet("Mira", 78), new PhoneDataSet("+67 344 4422"));

        dbservice.save(user1);
        dbservice.save(user2);
        dbservice.save(user3);

        dbservice.read(2);
        dbservice.read(2);
        dbservice.read(2);
        dbservice.read(2);

        ResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.setResourceBase(PUBLIC_HTML);

        ServletContextHandler servletContextHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
        TemplateProcessor templateProcessor = new TemplateProcessor();

        servletContextHandler.addServlet(LoginServlet.class, "/login");
        servletContextHandler.addServlet(new ServletHolder(new AdminServlet(templateProcessor, userDStCache)),
                "/admin");

        Server server = new Server(PORT);
        server.setHandler(new HandlerList(resourceHandler, servletContextHandler));

        server.start();
        server.join();

    }
}
