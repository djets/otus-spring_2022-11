package ru.otus.spring.model;

import java.util.UUID;

public class Answer {
    private Long id;
    private String text;
    private boolean rightAnswer;
    private Question question;

    public Answer() {
    }

    public Answer(String text, boolean rightAnswer, Question question) {
        this(UUID.randomUUID().getLeastSignificantBits(), text, rightAnswer, question);
    }

    public Answer(Long id, String text, boolean rightAnswer, Question question) {
        this.id = id;
        this.text = text;
        this.rightAnswer = rightAnswer;
        this.question = question;
    }

    public Long getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public boolean isRightAnswer() {
        return rightAnswer;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

}