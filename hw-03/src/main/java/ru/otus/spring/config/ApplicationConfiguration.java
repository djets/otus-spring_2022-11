package ru.otus.spring.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(AppLocaleProperties.class)
public class ApplicationConfiguration {
}
