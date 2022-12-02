package ru.otus.spring.dao.impl;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.otus.spring.dao.QuestionsDaoCSV;
import ru.otus.spring.dao.QuestionsDao;
import ru.otus.spring.model.Answer;
import ru.otus.spring.model.Question;

import java.util.HashSet;

import static org.assertj.core.api.Assertions.assertThat;

public class QuestionDaoCSVTest {
    private static ClassPathXmlApplicationContext context;
    private static QuestionsDao questionsDao;


    @BeforeAll
    static void setUp() {
        context = new ClassPathXmlApplicationContext("/spring-context.xml");
        questionsDao = (QuestionsDaoCSV) context.getBean(QuestionsDaoCSV.class);
    }

    @Test
    public void getAllTest() {
        var question = new Question("On an American $100 bill, there is portrait of American statesman?");
        var answerSet = new HashSet<Answer>();
        answerSet.add(new Answer("Benjamin Franklin", true, question));
        answerSet.add(new Answer("Franklin Roosevelt", false, question));
        answerSet.add(new Answer("George Washington", false, question));
        answerSet.add(new Answer("Abraham Lincoln", false, question));
        var questionSetMap = questionsDao.getAll().get();
        assertThat(answerSet).usingRecursiveComparison().isEqualTo(questionSetMap.get(question));
    }

    @AfterAll
    static void close() {
        context.close();
    }

}
