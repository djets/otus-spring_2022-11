package ru.otus.spring.hw15;

import com.github.cloudyrock.spring.v5.EnableMongock;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableMongock
@EnableMongoRepositories
@SpringBootApplication
public class Hw15Application {
    public static void main(String[] args) {
        SpringApplication.run(Hw15Application.class);
    }

}
