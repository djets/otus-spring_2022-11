package ru.otus.spring.service;

import com.opencsv.exceptions.CsvException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.spring.dao.impl.AnswerDaoCSVImpl;
import ru.otus.spring.dao.impl.QuestionDaoCSVImpl;
import ru.otus.spring.model.Answer;
import ru.otus.spring.model.Question;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@ExtendWith(MockitoExtension.class)
public class ServiceTest {
    Logger logger = LoggerFactory.getLogger(ServiceTest.class);
    QuestionService questionService;
    AnswerService answerService;
    @Mock
    QuestionDaoCSVImpl questionDaoCSV;
    @Mock
    AnswerDaoCSVImpl answerDaoCSV;

    @BeforeEach
    void init() {
        questionService = new QuestionService(questionDaoCSV);
        answerService = new AnswerService(answerDaoCSV);
    }

    @Test
    void getListQuestionTest() throws IOException, CsvException {
        List<Question> questionList = new ArrayList<>(Stream.iterate(1, n -> n + 1).limit(5).map(i -> Question.builder().setText("Question" + i).build()).collect(Collectors.toList()));
        Mockito.when(questionDaoCSV.getAll()).thenReturn(Optional.of(questionList));
        logger.info(questionList.stream().map(Question::getText).collect(Collectors.joining(", ")));
        Assertions.assertEquals(questionList, questionDaoCSV.getAll().get());
    }
    @Test
    void getListAnswerTest() throws IOException, CsvException {
        List<Answer> answerList = new ArrayList<>(Stream.iterate(1, n -> n + 1).limit(5).map(i -> Answer.builder().setText("Answer" + i).build()).collect(Collectors.toList()));
        Mockito.when(answerDaoCSV.getAll()).thenReturn(Optional.of(answerList));
        logger.info(answerList.stream().map(Answer::getText).collect(Collectors.joining(", ")));
        Assertions.assertEquals(answerList, answerDaoCSV.getAll().get());
    }

}
