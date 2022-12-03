package ru.otus.spring.services;

import ru.otus.spring.model.User;

public interface UserService {
    Long createUser();
    User getUser(Long id);
}
