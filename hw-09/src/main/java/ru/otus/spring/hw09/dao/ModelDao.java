package ru.otus.spring.hw09.dao;

import java.util.List;

public interface ModelDao<T> {
    void insert(T t);

    T getById(Class<? extends Number> id);

    List<T> getAll();

    void deleteById(Class<? extends Number> id);
}
