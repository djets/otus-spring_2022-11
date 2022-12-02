package ru.otus.spring.model;

import java.util.Objects;

public class Answer {
    private Long id;
    private String text;
    private boolean rightAnswer;
    private Question question;

    public Answer() {
    }

    public Answer(String text, boolean rightAnswer, Question question) {
        this.id = null;
        this.text = text;
        this.rightAnswer = rightAnswer;
        this.question = question;
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

    @Override
    public String toString() {
        return "Answer{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", rightAnswer=" + rightAnswer +
                ", question=" + question +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Answer answer = (Answer) o;

        if (rightAnswer != answer.rightAnswer) return false;
        if (!text.equals(answer.text)) return false;
        return Objects.equals(question, answer.question);
    }

    @Override
    public int hashCode() {
        int result = text.hashCode();
        result = 31 * result + (rightAnswer ? 1 : 0);
        result = 31 * result + (question != null ? question.hashCode() : 0);
        return result;
    }
}