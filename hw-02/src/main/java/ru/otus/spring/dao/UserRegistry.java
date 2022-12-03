package ru.otus.spring.dao;

import ru.otus.spring.model.User;

public interface UserRegistry {
    void saveUser(User user);
    User getUser(Long id);
}
