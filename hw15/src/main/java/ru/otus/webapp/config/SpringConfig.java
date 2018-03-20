package ru.otus.webapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import ru.otus.webapp.messagesystem.MessageSystemContext;
import ru.otus.webapp.controller.websocket.MyWebSocket;
import ru.otus.webapp.services.cache.CacheEngine;
import ru.otus.webapp.services.front.FrontendService;
import ru.otus.webapp.services.front.FrontendServiceImpl;
import ru.otus.webapp.services.cache.CacheEngineImpl;
import ru.otus.webapp.messagesystem.MessageSystem;
import ru.otus.webapp.model.entity.UserDataSet;
import ru.otus.webapp.model.service.DBService;
import ru.otus.webapp.model.util.DBHelper;

@Configuration
@ComponentScan("ru.otus.webapp")
public class SpringConfig {

    @Bean
    public DBService dbService() {
        ru.otus.webapp.model.config.Configuration configuration = new ru.otus.webapp.model.config.Configuration();
        configuration.addClass(UserDataSet.class);
        return DBHelper.getDBServiceInstance(configuration);
    }

    @Bean
    public CacheEngine cacheEngine() {
        return new CacheEngineImpl<>(10, 0, 0, true);
    }

    @Bean
    public FrontendService frontendService() {
        return new FrontendServiceImpl();
    }

    @Bean
    public MessageSystem messageSystem() {
        return new MessageSystem();
    }

    @Bean
    public MessageSystemContext context() {
        return new MessageSystemContext();
    }

    @Bean
    public MyWebSocket myWebSocket() {
        return new MyWebSocket();
    }
}
