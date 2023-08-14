package ru.otus.spring.hw19.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.spring.hw19.repository.event.CascadeSaveMongoEventListener;

@Configuration
public class CascadeSaveConfig {
    @Bean
    public CascadeSaveMongoEventListener cascadeSaveMongoEventListener() {
        return new CascadeSaveMongoEventListener();
    }
}
