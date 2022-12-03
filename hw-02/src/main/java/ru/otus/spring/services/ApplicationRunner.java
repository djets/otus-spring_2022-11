package ru.otus.spring.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.otus.spring.controllers.IOService;
@Component
public class ApplicationRunner {
    private final IOService ioService;
    private final UserService userService;
    private final QuestionProcessor questionsProcessor;

    @Autowired
    public ApplicationRunner(IOService ioService, UserService userService, QuestionProcessor questionsProcessor) {
        this.ioService = ioService;
        this.userService = userService;
        this.questionsProcessor = questionsProcessor;
    }
    public void run() {
        ioService.outString("Welcome to game 'Who Wants To Be A Millionaire!'\n");
        Long userId = userService.createUser();
        questionsProcessor.askQuestions();
        long count = questionsProcessor.getResultMap().values().stream().filter(Boolean::booleanValue).count();
        ioService.outString("Player: " + userService.getUser(userId) + " answered " + count + " questions correctly.");
    }
}
