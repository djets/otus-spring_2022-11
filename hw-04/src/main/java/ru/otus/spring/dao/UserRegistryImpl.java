package ru.otus.spring.dao;

import org.springframework.stereotype.Component;
import ru.otus.spring.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class UserRegistryImpl implements UserRegistry {
    private final Map<Long, User> userList;

    public UserRegistryImpl() {
        userList = new HashMap<>();
    }

    @Override
    public void saveUser(User user) {
        userList.put(user.getId(), user);
    }

    @Override
    public User getUser(Long id) {
        return userList.get(id);
    }
}
