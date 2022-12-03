package ru.otus.spring.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

@Component
public class IOServiceConsole implements IOService {
    private final PrintStream output;
    private final Scanner scanner;

    public IOServiceConsole(@Autowired PrintStream outputStream, @Autowired InputStream inputStream) {
        this.output = outputStream;
        this.scanner = new Scanner(inputStream);
    }

    @Override
    public void outString(String string) {
        output.println(string);
    }

    @Override
    public int inputInt() {
        return Integer.parseInt(scanner.nextLine());
    }

    @Override
    public String inputWithPrompt(String string) {
        output.println(string);
        return scanner.nextLine();
    }
}
