package ru.otus.spring.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ResourceBundleMessageSource;
import ru.otus.spring.config.AppLocaleProperties;
import ru.otus.spring.controllers.IOService;
import ru.otus.spring.dao.CsvReadProcessorImpl;
import ru.otus.spring.dao.QuestionRegistryImpl;

import java.util.Locale;

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

    private CsvReadProcessorImpl csvReadProcessor;

    @BeforeEach
    void setUp() {
        AppLocaleProperties appLocaleProperties = new AppLocaleProperties();
        appLocaleProperties.setLocale(Locale.ENGLISH);
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("test");
        messageSource.setDefaultEncoding("UTF-8");
        csvReadProcessor = new CsvReadProcessorImpl(appLocaleProperties, messageSource);
        logger.info(csvReadProcessor.getConfigCSV());
        QuestionRegistryImpl questionRegistry = new QuestionRegistryImpl(csvReadProcessor);
        questionService = new QuestionServiceImpl(questionRegistry);
        questionProcessor = new QuestionProcessorImpl(questionService, ioService);
    }

    @Test
    void whenAsksQuestionAnswerShouldBeCorrectTest() {
        Mockito.when(ioService.inputInt()).thenReturn(3);
        questionProcessor.askQuestions();
        logger.info("ResultMap size: {}", questionProcessor.getResultMapByUserId().size());
        assertThat(questionProcessor.getResultMapByUserId().size()).isEqualTo(1);
        questionProcessor.getResultMapByUserId()
                .forEach((q, b) -> {
                    logger.info("Question id:{}; Answer should be correct: {}", q, b);
                    assertThat(b).isEqualTo(true);
                });
        verify(ioService, times(1)).outString(any());
    }
}
