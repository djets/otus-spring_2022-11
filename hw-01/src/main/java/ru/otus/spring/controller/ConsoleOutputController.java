package ru.otus.spring.controller;

public class ConsoleOutputController implements OutputController{
    @Override
    public void stdout(String string) {
        System.out.print(string);
    }
}
