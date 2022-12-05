package ru.otus.spring.model;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class Question {
    private Long id;
    private String text;
    private List<Answer> answers;

    public Question() {
    }

    public Question(String text) {
        this.id = UUID.randomUUID().getLeastSignificantBits();
        this.text = text;
    }

    public Question(Long id, String text, List<Answer> answers) {
        this.id = id;
        this.text = text;
        this.answers = answers;
    }

    public Long getId() {
        return id;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    @Override
    public String toString() {
        AtomicInteger atomicInteger = new AtomicInteger(1);
        Optional<List<Answer>> optionalAnswers = Optional.ofNullable(this.getAnswers());
        return text + "\n" + optionalAnswers.map(answerList -> answerList
                .stream()
                .map(Answer::getText)
                .map(string -> atomicInteger.getAndIncrement() + ": " + string)
                .collect(Collectors.joining("\n")) + "\n").orElse("00ps, this question has no answers");
    }
}