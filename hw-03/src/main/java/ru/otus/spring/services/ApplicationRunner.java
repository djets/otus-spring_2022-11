package ru.otus.spring.services;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import ru.otus.spring.config.AppLocaleProperties;
import ru.otus.spring.controllers.IOService;

@Component
public class ApplicationRunner {
    private final IOService ioService;
    private final UserService userService;
    private final QuestionProcessor questionsProcessor;
    private final MessageSource messageSource;
    private final AppLocaleProperties properties;

    public ApplicationRunner(@Qualifier("ioServiceConsole") IOService ioService,
                             UserService userService, QuestionProcessor questionsProcessor,
                             MessageSource messageSource, AppLocaleProperties properties) {
        this.ioService = ioService;
        this.userService = userService;
        this.questionsProcessor = questionsProcessor;
        this.messageSource = messageSource;
        this.properties = properties;
    }
    public void run() {
        var messageGreetings = messageSource.getMessage("message.greetings", null, properties.getLocale());
        ioService.outString(messageGreetings);
        Long userId = userService.createUser();
        questionsProcessor.askQuestions();
        long count = questionsProcessor.getResultMap().values().stream().filter(Boolean::booleanValue).count();
        int totalQuestions = questionsProcessor.getResultMap().size();
        var messageResult = messageSource.getMessage("message.result",
                new Object[]{userService.getUser(userId), count, totalQuestions}, properties.getLocale());
        ioService.outString(messageResult);
    }
}
