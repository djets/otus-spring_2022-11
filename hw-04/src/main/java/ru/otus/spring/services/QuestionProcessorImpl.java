package ru.otus.spring.services;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.otus.spring.controllers.IOService;
import ru.otus.spring.model.Answer;
import ru.otus.spring.model.Question;

import java.util.HashMap;
import java.util.Map;

@Service
public class QuestionProcessorImpl implements QuestionProcessor {
    private final Map<Long, Map<Long, Boolean>> resultMap;

    private final QuestionService questionService;
    private final IOService ioService;

    public QuestionProcessorImpl(QuestionService questionService, @Qualifier("ioServiceConsole") IOService inputService) {
        this.questionService = questionService;
        this.ioService = inputService;
        resultMap = new HashMap<>();
    }

    @Override
    public Map<Long, Boolean> getResultMapByUserId(Long userId) {
        return resultMap.get(userId);
    }

    @Override
    public void askQuestions(Long userId) {
        resultMap.put(userId, new HashMap<>());
        for (Question question : questionService.getAllQuestion()) {
            int correctAnswer = 1;
            for (Answer answer : question.getAnswers()) {
                if (answer.isRightAnswer()) {
                    break;
                }
                correctAnswer++;
            }
            ioService.outString(question.toString());
            int inputInt = ioService.inputInt();
            resultMap.get(userId).put(question.getId(), correctAnswer == inputInt);
        }
    }
}
