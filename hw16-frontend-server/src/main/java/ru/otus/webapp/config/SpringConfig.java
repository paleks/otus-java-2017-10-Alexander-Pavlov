package ru.otus.webapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import ru.otus.webapp.controller.socket.SocketMsgWorker;
import ru.otus.webapp.controller.websocket.MyWebSocket;

import java.io.IOException;

@Configuration
@ComponentScan("ru.otus.webapp")
public class SpringConfig {

    @Bean
    @Scope("prototype")
    public MyWebSocket myWebSocket() throws IOException {
        return new MyWebSocket(socketMsgWorker());
    }

    @Bean
    @Scope("prototype")
    public SocketMsgWorker socketMsgWorker() throws IOException {
        return new SocketMsgWorker("localhost", 5050);
    }
}
