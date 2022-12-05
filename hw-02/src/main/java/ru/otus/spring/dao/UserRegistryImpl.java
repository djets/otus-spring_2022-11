package ru.otus.spring.dao;

import org.springframework.stereotype.Component;
import ru.otus.spring.model.User;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserRegistryImpl implements UserRegistry{
    private final List<User> userList;

    public UserRegistryImpl() {
        userList = new ArrayList<>();
    }

    @Override
    public void saveUser(User user) {
        userList.add(user);
    }

    @Override
    public User getUser(Long id) {
        return userList.get(0);
    }
}
