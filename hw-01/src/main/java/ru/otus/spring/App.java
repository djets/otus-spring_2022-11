package ru.otus.spring;

import ru.otus.spring.dao.impl.AbstractDaoCSV;
import ru.otus.spring.dao.impl.AnswerDaoCSVImpl;
import ru.otus.spring.dao.impl.QuestionDaoCSVImpl;
import ru.otus.spring.model.Answer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.otus.spring.service.AnswerService;
import ru.otus.spring.service.QuestionService;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class App {
    static Logger log = LoggerFactory.getLogger(App.class);
    public static void main(String[] args) {

        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("/spring-context.xml");
        QuestionDaoCSVImpl questionDaoCSV = (QuestionDaoCSVImpl) context.getBean("questionDaoCSVImpl");
        AnswerDaoCSVImpl answerDaoCSV = (AnswerDaoCSVImpl) context.getBean("answerDaoCSVImpl");
        QuestionService questionService = context.getBean(QuestionService.class);
        AnswerService answerService = context.getBean(AnswerService.class);

        answerService.getListObject().stream().peek(answer -> {
            questionService.getListObject().forEach(question -> {
                if (question == answer.getQuestion()) {
                    answer.setQuestion(question);
                }
            });
        }).collect(Collectors.groupingBy(Answer::getQuestion, Collectors.toSet())).forEach((k, v) -> {
            var i = new AtomicInteger(1);
            System.out.println(k.getText() + "\n" + v.stream().map(Answer::getText)
                    .map(s -> i.getAndIncrement() + ": " + s).collect(Collectors.joining("\n")));
        });
    }


}
