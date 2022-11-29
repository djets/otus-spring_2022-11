package ru.otus.spring.dao.impl;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import ru.otus.spring.config.ConfigCSV;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Objects;

public class AbstractDaoCSV {
    private ConfigCSV fileNameCSV;

    public ConfigCSV getFileNameCSV() {
        return fileNameCSV;
    }

    public void setFileNameCSV(ConfigCSV fileNameCSV) {
        this.fileNameCSV = fileNameCSV;
    }

    public CSVReader getCsvReader() {
        BufferedInputStream bis = new BufferedInputStream(Objects.requireNonNull
                (ClassLoader.getSystemResourceAsStream(getFileNameCSV().getFileNameCSV())));
        Reader br = new BufferedReader(new InputStreamReader(bis));
        return new CSVReaderBuilder(br).withSkipLines(1).build();
    }

}
