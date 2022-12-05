package ru.otus.spring.services;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.otus.spring.controllers.IOService;
import ru.otus.spring.dao.UserRegistry;
import ru.otus.spring.model.User;

@Service
public class UserServiceImpl implements UserService {
    private final UserRegistry userRegistry;
    private final IOService ioService;

    public UserServiceImpl(UserRegistry userRegistry, @Qualifier("ioServiceConsole") IOService ioService) {
        this.userRegistry = userRegistry;
        this.ioService = ioService;
    }

    @Override
    public Long createUser() {
        User newUser = new User(ioService.inputWithPrompt("Input Your First name."),
                ioService.inputWithPrompt("Input Your Last name."));
        userRegistry.saveUser(newUser);
        return newUser.getId();
    }

    @Override
    public User getUser(Long id) {
        return userRegistry.getUser(id);
    }


}
