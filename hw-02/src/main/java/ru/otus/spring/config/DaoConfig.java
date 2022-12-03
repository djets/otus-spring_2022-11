package ru.otus.spring.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import ru.otus.spring.dao.*;

@Configuration
@PropertySource(value = "classpath:application.properties")
public class DaoConfig {
    private CsvReadProcessor csvReadProcessor;


    @Bean
    public CsvReadProcessor getCsvReadProcessor(){
        return csvReadProcessor;
    }

    @Bean
    public UserRegistry getUserRegistry() {
        return new UserRegistryImpl();
    }

    @Bean
    public QuestionRegistry getQuestionRegistry() {
        return new QuestionRegistryImpl(csvReadProcessor);
    }
}
