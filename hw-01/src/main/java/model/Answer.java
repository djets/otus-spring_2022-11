package model;

import java.util.UUID;

public class Answer {
    private Long id;
    private String text;
    private boolean rightAnswer;
    private Question question;

    public Answer(){};

    private Answer(Long id, String text, boolean rightAnswer, Question question) {
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

    public static AnswerBuilder builder() {
        return new AnswerBuilder();
    }

    public static class AnswerBuilder {
        private Long id;
        private String text;
        private boolean rightAnswer;
        private Question question;

        public AnswerBuilder setText(final String text) {
            this.text = text;
            return this;
        }

        public AnswerBuilder setRightAnswer(final boolean rightAnswer) {
            this.rightAnswer = rightAnswer;
            return this;
        }

        public AnswerBuilder setQuestion(final Question question) {
            this.question = question;
            return this;
        }

        public Answer build() {
            return new Answer(this.id = UUID.randomUUID().getMostSignificantBits(), text, rightAnswer, question);
        }

    }
}