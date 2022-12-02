package ru.otus.spring;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.otus.spring.controller.ConsoleOutputController;
import ru.otus.spring.dao.QuestionsDaoCSV;
import ru.otus.spring.service.QuestionServiceImpl;

public class App {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("/spring-context.xml");
        context.getBean(QuestionsDaoCSV.class);
        context.getBean(ConsoleOutputController.class);
        var questionService = context.getBean(QuestionServiceImpl.class);
        questionService.printAllQuestions();
        context.close();
    }
}
