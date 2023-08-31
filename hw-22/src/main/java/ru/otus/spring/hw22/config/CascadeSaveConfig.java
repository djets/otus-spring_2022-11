package ru.otus.spring.hw22.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.spring.hw22.repository.event.CascadeSaveMongoEventListener;

@Configuration
public class CascadeSaveConfig {
    @Bean
    public CascadeSaveMongoEventListener cascadeSaveMongoEventListener() {
        return new CascadeSaveMongoEventListener();
    }
}
