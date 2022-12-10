package ru.otus.spring.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import ru.otus.spring.controllers.IOService;
import ru.otus.spring.controllers.IOServiceConsole;

@Configuration
public class ControllerConfig {
    @Bean
    @Qualifier("ioServiceConsole")
    public IOService getIoService() {
        return new IOServiceConsole(System.out, System.in);
    }
//    @Bean
//    public MessageSource messageSource() {
//        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
//        messageSource.setBasename("classloader:slumdog");
//        messageSource.setDefaultEncoding("UTF-8");
//        return messageSource;
//    }
}
