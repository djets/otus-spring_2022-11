package ru.otus.spring.dao.impl;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.otus.spring.controller.ConsoleOutputController;
import ru.otus.spring.model.Answer;
import ru.otus.spring.model.Question;
import ru.otus.spring.service.AnswerService;
import ru.otus.spring.service.QuestionService;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class DaoImplTest {
    static Logger logger = LoggerFactory.getLogger(DaoImplTest.class);

    private static BufferedInputStream brs;
    private static BufferedReader br;
    private static CSVReader csvReader;

    private List<Answer> answerList;
    private List<Question> questionList;
    private static ClassPathXmlApplicationContext context;
    private static QuestionService questionService;
    private static AnswerService answerService;
    private static ConsoleOutputController consoleOutput;

    @BeforeAll
    static void setUp() {
        context = new ClassPathXmlApplicationContext("/spring-context.xml");
        context.getBean(QuestionDaoCSVImpl.class);
        context.getBean(AnswerDaoCSVImpl.class);
        questionService = context.getBean(QuestionService.class);
        answerService = context.getBean(AnswerService.class);
        logger.info("context load <<<<<=========================================");
    }

    @BeforeEach
    void init() {
        try {
            getCsvReader();
            answerList = Optional.of(csvReader.readAll().stream()
                            .map(s -> Answer.builder()
                                    .setText(s[1])
                                    .setRightAnswer(Boolean.parseBoolean(s[2]))
                                    .setQuestion(Question.builder().setText(s[0]).build())
                                    .build())
                            .distinct()
                            .collect(Collectors.toList()))
                    .get();

            getCsvReader();
            questionList = Optional.of(csvReader.readAll().stream()
                            .map(s -> Question.builder()
                                    .setText(s[0])
                                    .build())
                            .distinct()
                            .collect(Collectors.toList()))
                    .get();
        } catch (IOException | CsvException e) {
            throw new RuntimeException(e);
        }
    }

    private static void getCsvReader() {
        brs = new BufferedInputStream(Objects.requireNonNull(ClassLoader.getSystemResourceAsStream("data.csv")));
        br = new BufferedReader(new InputStreamReader(brs));
        csvReader = new CSVReaderBuilder(br).withSkipLines(1).build();
    }

    @Test
    void getAllQuestionsTest() {
        assertThat(questionService.getListObject()).usingRecursiveComparison().ignoringFields("id").isEqualTo(questionList);
        logger.info("getAllQuestionsTest has passed");
    }

    @Test
    void getAllAnswersTest() {
        assertThat(answerService.getListObject()).usingRecursiveComparison().ignoringFields("id").ignoringFields("question").isEqualTo(answerList);
        logger.info("getAllAnswersTest has passed");
    }

}
