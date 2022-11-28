package dao.impl;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Objects;

public class AbstractDaoCSV {
    private final CSVReader csvReader;
    public AbstractDaoCSV() {
        BufferedInputStream bis = new BufferedInputStream(Objects.requireNonNull(ClassLoader.getSystemResourceAsStream("data.csv")));
        Reader br = new BufferedReader(new InputStreamReader(bis));
        this.csvReader = new CSVReaderBuilder(br).withSkipLines(1).build();
    }

    public CSVReader getCsvReader() {
        return csvReader;
    }
}
