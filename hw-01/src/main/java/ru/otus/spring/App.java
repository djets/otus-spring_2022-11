package ru.otus.spring;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.otus.spring.controller.ConsoleOutputController;
import ru.otus.spring.dao.impl.AnswerDaoCSVImpl;
import ru.otus.spring.dao.impl.QuestionDaoCSVImpl;
import ru.otus.spring.model.Answer;
import ru.otus.spring.service.AnswerService;
import ru.otus.spring.service.QuestionService;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class App {
    public static void main(String[] args) {

        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("/spring-context.xml");
        context.getBean(QuestionDaoCSVImpl.class);
        context.getBean(AnswerDaoCSVImpl.class);
        var questionService = context.getBean(QuestionService.class);
        var answerService = context.getBean(AnswerService.class);
        var consoleOutput = context.getBean(ConsoleOutputController.class);

        getAllQuestionsAndAnswers(questionService, answerService, consoleOutput);
    }

    private static void getAllQuestionsAndAnswers(QuestionService questionService, AnswerService answerService, ConsoleOutputController consoleOutput) {
        answerService.getListObject().stream().peek(answer -> {
            questionService.getListObject().forEach(question -> {
                if (question == answer.getQuestion()) {
                    answer.setQuestion(question);
                }
            });
        }).collect(Collectors.groupingBy(Answer::getQuestion, Collectors.toSet())).forEach((k, v) -> {
            var i = new AtomicInteger(1);
            consoleOutput.stdout(k.getText() + "\n" + v.stream().map(Answer::getText).map(s -> i.getAndIncrement() + ": " + s).collect(Collectors.joining("\n")));
        });
    }


}
