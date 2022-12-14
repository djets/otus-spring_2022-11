package ru.otus.spring.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.spring.controllers.IOService;
import ru.otus.spring.dao.CsvReadProcessorImpl;
import ru.otus.spring.dao.QuestionRegistryImpl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class QuestionProcessorImplTest {
    Logger logger = LoggerFactory.getLogger(QuestionProcessorImplTest.class);
    private QuestionService questionService;
    @Mock
    private IOService ioService;
    private QuestionProcessorImpl questionProcessor;

    @BeforeEach
    void setUp() {
        CsvReadProcessorImpl csvReadProcessor = new CsvReadProcessorImpl("testdata.csv");
        QuestionRegistryImpl questionRegistry = new QuestionRegistryImpl(csvReadProcessor);
        questionService = new QuestionServiceImpl(questionRegistry);
        questionProcessor = new QuestionProcessorImpl(questionService, ioService);
    }

    @Test
    void whenAsksQuestionAnswerShouldBeCorrectTest() {
        Mockito.when(ioService.inputInt()).thenReturn(3);
        questionProcessor.askQuestions();
        logger.info("ResultMap size: {}", questionProcessor.getResultMap().size());
        assertThat(questionProcessor.getResultMap().size()).isEqualTo(1);
        questionProcessor.getResultMap()
                .forEach((q, b) -> {
                    logger.info("Question id:{}; Answer should be correct: {}", q, b);
                    assertThat(b).isEqualTo(true);
                });
        verify(ioService, times(1)).outString(any());
    }
}
