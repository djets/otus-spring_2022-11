package ru.otus.spring.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.spring.controllers.IOService;
import ru.otus.spring.controllers.IOServiceConsole;

import java.io.InputStream;
import java.io.PrintStream;

@Configuration
public class ControllerConfig {
    private final PrintStream printStream;
    private final InputStream inputStream;

    @Autowired
    public ControllerConfig(PrintStream printStream, InputStream inputStream) {
        this.printStream = printStream;
        this.inputStream = inputStream;
    }

    @Bean
    public IOService getIOService() {
        return new IOServiceConsole(printStream, inputStream);
    }
}
