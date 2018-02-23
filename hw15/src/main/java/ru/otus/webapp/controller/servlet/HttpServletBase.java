package ru.otus.webapp.controller.servlet;

import org.springframework.context.ApplicationContext;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

public class HttpServletBase extends HttpServlet {
    @Override
    public void init() throws ServletException {
        ApplicationContext context =
                (ApplicationContext) this.getServletContext().getAttribute("SpringBeansContext");
        context.getAutowireCapableBeanFactory().autowireBean(this);
    }
}
