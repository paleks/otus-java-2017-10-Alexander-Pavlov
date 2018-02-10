package ru.otus.webapp.controller.listener;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.otus.webapp.config.SpringConfig;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class WebApplicationContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);
        sce.getServletContext().setAttribute("SpringBeansContext", context);
        System.out.println("====>>>> SERVLET CONTEXT AND SPRING APPLICATION CONTEXT ARE INITED");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("====>>>> SERVLET CONTEXT AND SPRING APPLICATION CONTEXT ARE DESTROYED");
    }
}
