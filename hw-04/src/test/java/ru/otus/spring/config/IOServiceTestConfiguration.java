package ru.otus.spring.config;

import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import ru.otus.spring.controllers.IOServiceConsole;
import ru.otus.spring.services.QuestionProcessor;
import ru.otus.spring.services.QuestionProcessorImpl;
import ru.otus.spring.services.QuestionService;

@TestConfiguration
public class IOServiceTestConfiguration {
    private final Logger logger = LoggerFactory.getLogger(IOServiceTestConfiguration.class);
    @Autowired
    private QuestionService questionService;

    @MockBean
    private IOServiceConsole ioServiceConsole;

    @Bean
    @Primary
    public QuestionProcessor questionProcessor() {
        logger.info("\u001b[32m" + "When the question is asked, ioServiceConsole returns 3" + "\u001b[0m");
        Mockito.when(ioServiceConsole.inputInt()).thenReturn(3);
        return new QuestionProcessorImpl(questionService, ioServiceConsole);
    }
}
