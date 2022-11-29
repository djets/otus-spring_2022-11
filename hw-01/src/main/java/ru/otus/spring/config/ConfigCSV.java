package ru.otus.spring.config;

public class ConfigCSV {
    public final static ConfigCSV FILE_NAME_CSV = new ConfigCSV("data.csv");
    private String fileNameCSV;

    public ConfigCSV(String fileNameCSV) {
        this.fileNameCSV = fileNameCSV;
    }

    public String getFileNameCSV() {
        return fileNameCSV;
    }

    public void setFileNameCSV(String fileNameCSV) {
        this.fileNameCSV = fileNameCSV;
    }

    public static ConfigCSV getFileNameCsv() {
        return FILE_NAME_CSV;
    }

    @Override
    public String toString() {
        return "ConfigCSV{" +
                "fileNameCSV='" + fileNameCSV + '\'' +
                '}';
    }
}
