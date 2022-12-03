package ru.otus.spring.services;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import ru.otus.spring.controllers.IOService;
import ru.otus.spring.model.Answer;
import ru.otus.spring.model.Question;

import java.util.HashMap;
import java.util.Map;
@Service
public class QuestionProcessorImpl implements QuestionProcessor {
    private final Map<Long, Boolean> resultMap;

    private final QuestionService questionService;
    private final IOService ioService;

    public QuestionProcessorImpl(QuestionService questionService, IOService inputService) {
        this.questionService = questionService;
        this.ioService = inputService;
        resultMap = new HashMap<>();
    }

    @Override
    public Map<Long, Boolean> getResultMap() {
        return resultMap;
    }

    @Override
    public void askQuestions() {
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
            resultMap.put(question.getId(), correctAnswer == inputInt);
        }
    }
}
