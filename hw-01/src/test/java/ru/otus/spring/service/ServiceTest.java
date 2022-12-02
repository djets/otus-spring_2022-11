package ru.otus.spring.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.spring.controller.OutputController;
import ru.otus.spring.dao.QuestionsDao;
import ru.otus.spring.model.Answer;
import ru.otus.spring.model.Question;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class ServiceTest {
    QuestionServiceImpl questionService;
    @Mock
    QuestionsDao questionDaoCSV;
    @Mock
    OutputController outputController;

    Map<Question, Set<Answer>> questionMap;

    @BeforeEach
    void init() {
        var question = new Question("On an American $100 bill, there is portrait of American statesman?");
        var answerSet = new HashSet<Answer>();
        answerSet.add(new Answer("Benjamin Franklin", true, question));
        answerSet.add(new Answer("Franklin Roosevelt", false, question));
        answerSet.add(new Answer("George Washington", false, question));
        answerSet.add(new Answer("Abraham Lincoln", false, question));
        questionMap = new HashMap<>();
        questionMap.put(question, answerSet);
        questionService = new QuestionServiceImpl(questionDaoCSV, outputController);
    }

    @Test
    void getQuestions() {
        Mockito.when(questionDaoCSV.getAll()).thenReturn(Optional.of(questionMap));
        assertThat(questionMap).usingRecursiveComparison().isEqualTo(questionService.getQuestions());
    }

    @Test
    void getListAnswerTest() {
        Mockito.when(questionDaoCSV.getAll()).thenReturn(Optional.of(questionMap));
        questionService.printAllQuestions();
        Mockito.verify(outputController).stdout(Mockito.anyString());

    }

}
