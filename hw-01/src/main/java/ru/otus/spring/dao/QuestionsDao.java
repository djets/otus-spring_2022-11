package ru.otus.spring.dao;

import ru.otus.spring.model.Answer;
import ru.otus.spring.model.Question;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

public interface QuestionsDao {
    Optional<Map<Question, Set<Answer>>> getAll();
}
