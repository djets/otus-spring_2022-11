package ru.otus.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import ru.otus.spring.services.ApplicationRunner;

@SpringBootApplication(excludeName = "dispatcherServlet")
public class Main {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(Main.class, args);
        ApplicationRunner runner = context.getBean(ApplicationRunner.class);
        runner.run();
        context.close();
    }
}
