package ru.otus.spring.hw09.dao;

import java.util.List;

public interface ModelDao<T, ID> {
    void save(T t);
    void save(Long id, T t);
    T findById(ID id);
    List<T> findAll();
    void delete(T entity);
}
