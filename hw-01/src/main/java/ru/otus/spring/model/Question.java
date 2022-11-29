package ru.otus.spring.model;

import java.util.UUID;

public class Question {
    private Long id;
    private String text;

    public Question() {
    }

    private Question(Long id, String text) {
        this.id = id;
        this.text = text;
    }

    public Long getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Question question = (Question) o;

        return text.equals(question.text);
    }

    @Override
    public int hashCode() {
        return text.hashCode();
    }

    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", text='" + text + '\'' +
                '}';
    }

    public static QuestionBuilder builder() {
        return new QuestionBuilder();
    }

    public static class QuestionBuilder {
        private Long id;
        private String text;

        public QuestionBuilder setText(final String text) {
            this.text = text;
            return this;
        }

        public Question build() {
            return new Question(this.id = UUID.randomUUID().getMostSignificantBits(), text);
        }
    }
}