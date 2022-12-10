package ru.otus.spring.dao;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;
import ru.otus.spring.config.AppLocaleProperties;

import javax.annotation.PostConstruct;
import java.io.*;
import java.util.List;
import java.util.Objects;

@Component
public class CsvReadProcessorImpl implements CsvReadProcessor {
    private final String configCSV;
    private final AppLocaleProperties properties;
    private final MessageSource messageSource;

    public CsvReadProcessorImpl(AppLocaleProperties appLocaleProperties, MessageSource messageSource) {
        this.properties = appLocaleProperties;
        this.messageSource = messageSource;
        this.configCSV = messageSource.getMessage("csv.filename", null, properties.getLocale());
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
