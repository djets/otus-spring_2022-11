package ru.otus.spring.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.spring.dao.QuestionRegistry;
import ru.otus.spring.model.Answer;
import ru.otus.spring.model.Question;

import java.util.*;
@Service
public class QuestionServiceImpl implements QuestionService {
    private final QuestionRegistry questionRegistry;

    @Autowired
    public QuestionServiceImpl(QuestionRegistry questionRegistry) {
        this.questionRegistry = questionRegistry;
    }


    @Override
    public List<Question> getAllQuestion() {
        return questionRegistry.getQuestionList();
    }

    @Override
    public Question getQuestionById(Long id) {
        return questionRegistry.getQuestionById(id);
    }

    @Override
    public Answer getAnswerById(Long id) {
        return questionRegistry.getAnswerById(id);
    }
}
