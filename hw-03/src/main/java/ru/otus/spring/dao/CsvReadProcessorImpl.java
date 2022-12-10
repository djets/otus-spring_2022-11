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
@PropertySource("classpath:application.yaml")
public class CsvReadProcessorImpl implements CsvReadProcessor {
    private String configCSV;
    private final AppLocaleProperties appLocaleProperties;
    private final MessageSource messageSource;

//    public CsvReadProcessorImpl(AppLocaleProperties appLocaleProperties, @Value("${dao.csvFilename:data.csv}") String configCSV) {
//        this.appLocaleProperties = appLocaleProperties;
//        this.configCSV = configCSV;
//    }

    public CsvReadProcessorImpl(AppLocaleProperties appLocaleProperties, MessageSource messageSource) {
        this.appLocaleProperties = appLocaleProperties;
        this.messageSource = messageSource;
//        String csvLocalized = getCsvLocalized(appLocaleProperties, messageSource);
//        this.configCSV = csvLocalized;
    }

    public String getConfigCSV() {
        return configCSV;
    }

    @PostConstruct
    private void setCsvLocalized() {
        var test = this.messageSource.getMessage("test.text", null, appLocaleProperties.getLocale());
        configCSV = this.messageSource.getMessage("csv.filename", null, "data.csv", appLocaleProperties.getLocale());
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
