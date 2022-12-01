package ru.otus.spring.dao;

import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;
import ru.otus.spring.config.ConfigCSV;
import ru.otus.spring.model.Answer;
import ru.otus.spring.model.Question;

import java.io.*;
import java.util.*;

public class QuestionsDaoCSV implements QuestionsDao {
    private ConfigCSV configCSV;

    public void setConfigCSV(ConfigCSV configCSV) {
        this.configCSV = configCSV;
    }

    public List<String[]> getListStringArray() {
        BufferedInputStream bis = new BufferedInputStream(Objects.requireNonNull(ClassLoader.getSystemResourceAsStream(configCSV.getFileNameCSV())));
        Reader br = new BufferedReader(new InputStreamReader(bis));
        try {
            return new CSVReaderBuilder(br).withSkipLines(1).build().readAll();
        } catch (IOException | CsvException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Map<Question, Set<Answer>>> getAll() {
        Map<Question, Set<Answer>> questionSetMap = new HashMap<>();
        for (int i = 0; i < getListStringArray().size(); i++) {
            var strings = getListStringArray().get(i);
            for (int n = 0; n < strings.length; n++) {
                var question = new Question(strings[0]);
                if (!questionSetMap.containsKey(question)) {
                    var answerHashSet = new HashSet<Answer>();
                    answerHashSet.add(new Answer(strings[1], Boolean.parseBoolean(strings[2]), question));
                    questionSetMap.put(question, answerHashSet);
                } else {
                    questionSetMap.get(question).add(new Answer(strings[1], Boolean.parseBoolean(strings[2]), question));
                }
            }
        }
        return Optional.of(questionSetMap);
    }
}
