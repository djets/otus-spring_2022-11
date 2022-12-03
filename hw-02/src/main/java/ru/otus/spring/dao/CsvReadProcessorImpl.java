package ru.otus.spring.dao;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.*;

import static java.util.stream.Collectors.toMap;
@Component
public class CsvReadProcessorImpl implements CsvReadProcessor {

    private final String configCSV;

    @Autowired
    public CsvReadProcessorImpl(@Value("${dao.csvFilename}")String configCSV) {
        this.configCSV = configCSV;
    }

    @Override
    public List<String[]> read() {
        BufferedInputStream bis = new BufferedInputStream(Objects.requireNonNull
                (ClassLoader.getSystemResourceAsStream(configCSV)));
        Reader br = new BufferedReader(new InputStreamReader(bis));
        try {
            return new CSVReader(br).readAll();
        } catch (IOException | CsvException e) {
            throw new RuntimeException(e);
        }
    }
}
