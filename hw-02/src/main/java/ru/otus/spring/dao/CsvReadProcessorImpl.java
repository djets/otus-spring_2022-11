package ru.otus.spring.dao;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.List;
import java.util.Objects;
@Component
@PropertySource("classpath:application.properties")
public class CsvReadProcessorImpl implements CsvReadProcessor {
    private final String configCSV;

    public CsvReadProcessorImpl(@Value("${dao.csvFilename:data.csv}")String configCSV) {
        this.configCSV = configCSV;
    }

    public String getConfigCSV() {
        return configCSV;
    }

    @Override
    public List<String[]> read() {
        BufferedInputStream bis = new BufferedInputStream(Objects.requireNonNull
                (ClassLoader.getSystemResourceAsStream(getConfigCSV())));
        Reader br = new BufferedReader(new InputStreamReader(bis));
        try {
            return new CSVReader(br).readAll();
        } catch (IOException | CsvException e) {
            throw new RuntimeException(e);
        }
    }
}
