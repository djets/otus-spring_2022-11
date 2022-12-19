package ru.otus.spring.services;

import ru.otus.spring.model.Answer;
import ru.otus.spring.model.Question;

import java.util.List;

public interface QuestionService {
    List<Question> getAllQuestion();

    Question getQuestionById(Long id);

    Answer getAnswerById(Long id);
}
