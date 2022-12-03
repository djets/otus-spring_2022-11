package ru.otus.spring.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.spring.controllers.IOService;
import ru.otus.spring.dao.QuestionRegistry;
import ru.otus.spring.dao.UserRegistry;
import ru.otus.spring.services.*;

@Configuration
public class ServiceConfig {

    private final IOService ioService;

    private final UserRegistry userRegistry;

    private final QuestionProcessor questionProcessor;

    @Autowired
    public ServiceConfig(@Qualifier() IOService ioService, UserRegistry userRegistry, QuestionProcessor questionProcessor) {
        this.ioService = ioService;
        this.userRegistry = userRegistry;
        this.questionProcessor = questionProcessor;
    }

    @Bean
    public UserService getUserService(UserRegistry userRegistry, IOService ioService) {
        return new UserServiceImpl(userRegistry, ioService);
    }

    @Bean
    public QuestionService getQuestionService(QuestionRegistry questionRegistry) {
        return new QuestionServiceImpl(questionRegistry);
    }

    @Bean
    public QuestionProcessor getQuestionProcessor(QuestionService questionService, IOService ioService) {
        return new QuestionProcessorImpl(questionService, ioService);
    }

    @Bean
    public ApplicationRunner getApplicationRunner(IOService ioService, UserService userService, QuestionProcessor questionProcessor) {
        return new ApplicationRunner(ioService, userService, questionProcessor);
    }

}
