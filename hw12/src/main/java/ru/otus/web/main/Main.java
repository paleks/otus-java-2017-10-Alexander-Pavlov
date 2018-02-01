package ru.otus.web.main;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import ru.otus.web.controller.AdminServlet;
import ru.otus.web.model.cache.CacheEngineImpl;
import ru.otus.web.model.config.Configuration;
import ru.otus.web.model.entity.DataSet;
import ru.otus.web.model.entity.UserDataSet;
import ru.otus.web.model.util.DBHelper;

public class Main {
    private final static int PORT = 8090;
    private final static String PUBLIC_HTML = "public_html";

    public static void main(String[] args) throws Exception {

        ResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.setResourceBase(PUBLIC_HTML);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);

        // cache initialisation
        final CacheEngineImpl<String, ? extends DataSet> cacheEngine
                = new CacheEngineImpl<>(10, 0, 0, true);
        // configuration for dbservice
        Configuration configuration = new Configuration();
        configuration.addClass(UserDataSet.class);
        configuration.addCacheEngine(cacheEngine);

        context.setAttribute("cache", cacheEngine);
        context.setAttribute("dbservice", DBHelper.getDBServiceInstance(configuration));

        context.addServlet(AdminServlet.class, "/admin");

        Server server = new Server(PORT);
        server.setHandler(new HandlerList(resourceHandler, context));

        server.start();
        server.join();
    }
}
