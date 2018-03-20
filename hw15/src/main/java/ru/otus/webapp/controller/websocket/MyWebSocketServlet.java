package ru.otus.webapp.controller.websocket;

import org.eclipse.jetty.websocket.servlet.WebSocketServlet;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;
import org.springframework.context.ApplicationContext;

import javax.servlet.annotation.WebServlet;

/**
 * This class represents a servlet starting a webSocket application
 */
@WebServlet(displayName = "MyWebSocketServlet",
        name = "myWebSocketServlet",
        urlPatterns = "/socket")
public class MyWebSocketServlet extends WebSocketServlet {
    private final static int LOGOUT_TIME = 10 * 60 * 1000;

    @Override
    public void configure(WebSocketServletFactory factory) {
        factory.getPolicy().setIdleTimeout(LOGOUT_TIME);
        MyWebSocketCreator wsc = new MyWebSocketCreator();
        ApplicationContext context =
                (ApplicationContext) this.getServletContext().getAttribute("SpringBeansContext");
        context.getAutowireCapableBeanFactory().autowireBean(wsc);
        factory.setCreator(wsc);
    }
}
