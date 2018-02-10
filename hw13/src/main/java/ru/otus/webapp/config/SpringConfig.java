package ru.otus.webapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import ru.otus.webapp.model.cache.CacheEngine;
import ru.otus.webapp.model.cache.CacheEngineImpl;
import ru.otus.webapp.model.entity.UserDataSet;
import ru.otus.webapp.model.service.DBService;
import ru.otus.webapp.model.util.DBHelper;

@Configuration
@ComponentScan("ru.otus.webapp")
public class SpringConfig {

    @Bean
    public DBService dbService() {
        ru.otus.webapp.model.config.Configuration configuration =
            new ru.otus.webapp.model.config.Configuration();
        configuration.addClass(UserDataSet.class);
        return DBHelper.getDBServiceInstance(configuration);
    }

    @Bean
    public CacheEngine cacheEngine() {
        return new CacheEngineImpl<>(10, 0, 0, true);
    }
}
