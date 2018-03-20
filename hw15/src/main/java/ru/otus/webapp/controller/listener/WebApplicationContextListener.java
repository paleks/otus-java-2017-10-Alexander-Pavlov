package ru.otus.webapp.controller.listener;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.otus.webapp.config.SpringConfig;
import ru.otus.webapp.messagesystem.MessageSystem;
import ru.otus.webapp.model.entity.DataSet;
import ru.otus.webapp.model.entity.UserDataSet;
import ru.otus.webapp.model.service.DBService;
import ru.otus.webapp.services.cache.CacheEngine;
import ru.otus.webapp.services.front.FrontendService;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.sql.SQLException;

@WebListener
public class WebApplicationContextListener implements ServletContextListener {

    private static final int USERS_AMOUNT = 100;

    private MessageSystem messageSystem;

    private DBService dbService;

    private Thread dbUsage;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        // Spring context initialisation
        ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);

        // Frontend service initialisation
        FrontendService frontendService = context.getBean("frontendService", FrontendService.class);
        frontendService.init();

        // Cache service initialisation
        CacheEngine<String, ? extends DataSet> cacheEngine = context.getBean("cacheEngine", CacheEngine.class);
        cacheEngine.init();

        // Start message system
        this.messageSystem = context.getBean("messageSystem", MessageSystem.class);
        this.messageSystem.start();

        this.dbService = context.getBean("dbService", DBService.class);

        // Database usage imitation thread
        this.dbUsage = new Thread(() -> {
            while(true) {
                try {
                    Thread.sleep(2000);
                    useDataBaseService();
                } catch (SQLException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        dbUsage.start();
        // Put spring context into servlet context
        sce.getServletContext().setAttribute("SpringBeansContext", context);
        System.out.println("====>>>> SERVLET CONTEXT AND SPRING APPLICATION CONTEXT ARE INITED");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        this.dbUsage.interrupt();
        this.messageSystem.dispose();
        System.out.println("====>>>> SERVLET CONTEXT AND SPRING APPLICATION CONTEXT ARE DESTROYED");
    }

    private void useDataBaseService() throws SQLException {
        long id = 0;
        for (int i = 0; i < USERS_AMOUNT; i++) {
            id = this.dbService.save(new UserDataSet("Ivan", 2));
        }
        for (long i = id; i > id - USERS_AMOUNT; i--) {
            this.dbService.load(i, UserDataSet.class);
        }
    }
}
