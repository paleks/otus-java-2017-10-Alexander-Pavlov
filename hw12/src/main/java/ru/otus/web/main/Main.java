package ru.otus.web.main;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import ru.otus.web.controller.servlet.AdminServlet;
import ru.otus.web.model.cache.CacheEngine;
import ru.otus.web.model.cache.CacheEngineImpl;
import ru.otus.web.model.config.Configuration;
import ru.otus.web.model.entity.DataSet;
import ru.otus.web.model.entity.UserDataSet;
import ru.otus.web.model.service.DBService;
import ru.otus.web.model.util.DBHelper;

public class Main {
    private final static int PORT = 8090;
    private final static String PUBLIC_HTML = "public_html";

    public static void main(String[] args) throws Exception {

        ResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.setResourceBase(PUBLIC_HTML);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);

        context.setAttribute("dbservice", getDBService());
        context.addServlet(AdminServlet.class, "/admin");

        Server server = new Server(PORT);
        server.setHandler(new HandlerList(resourceHandler, context));

        server.start();
        server.join();
    }

    private static DBService getDBService() {
        Configuration configuration = new Configuration();
        configuration.addClass(UserDataSet.class);
        configuration.addCacheEngine(
                new CacheEngineImpl<>(5, 0, 0, true));
        return DBHelper.getDBServiceInstance(configuration);
    }
}
