package ru.otus.spring.dao;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import ru.otus.spring.model.Answer;
import ru.otus.spring.model.Question;

import java.util.*;

@Component
public class QuestionRegistryImpl implements QuestionRegistry {
    private final Map<Long, Question> idQuestionMap;
    private final Map<Long, Answer> idAnswersMap;
    private final CsvReadProcessor csvReadProcessor;

    public QuestionRegistryImpl(CsvReadProcessor csvReadProcessor) {
        this.csvReadProcessor = csvReadProcessor;
        this.idQuestionMap = new HashMap<>();
        this.idAnswersMap = new HashMap<>();
        saveQuestionsAndAnswersWithId();
    }

    @Override
    public Question getQuestionById(Long id) {
        return idQuestionMap.get(id);
    }

    @Override
    public Answer getAnswerById(Long id) {
        return null;
    }

    @Override
    public List<Question> getQuestionList() {
        return new ArrayList<>(idQuestionMap.values());
    }

    private void saveQuestionsAndAnswersWithId() {
        if (getOptionalQuestionList().isPresent()) {
            for (Question question : getOptionalQuestionList().get()) {
                idQuestionMap.put(question.getId(), question);
                question.getAnswers().forEach(answer -> idAnswersMap.put(answer.getId(), answer));
            }
        }
    }

    private Optional<List<Question>> getOptionalQuestionList() {
        List<Question> questionList = new ArrayList<>();
        List<String[]> list = csvReadProcessor.read();
        for (String[] strings : list) {
            if (questionList.stream().noneMatch(question -> question.getText().equals(strings[0]))) {
                Question question = new Question(strings[0]);
                List<Answer> answers = new ArrayList<>();
                answers.add(new Answer(strings[1], Boolean.parseBoolean(strings[2]), question));
                question.setAnswers(answers);
                questionList.add(question);
            } else {
                for (Question question : questionList) {
                    if (question.getText().equals(strings[0])) {
                        questionList.get(questionList.indexOf(question))
                                .getAnswers().add(new Answer(strings[1], Boolean.parseBoolean(strings[2]), question));
                    }
                }
            }
        }
        return Optional.of(questionList);
    }
}
