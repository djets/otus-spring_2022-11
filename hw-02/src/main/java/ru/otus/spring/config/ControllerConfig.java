package ru.otus.spring.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.spring.controllers.IOService;
import ru.otus.spring.controllers.IOServiceConsole;

@Configuration
public class ControllerConfig {
    @Bean
    @Qualifier("ioServiceConsole")
    public IOService getIoService() {
        return new IOServiceConsole(System.out, System.in);
    }
}
