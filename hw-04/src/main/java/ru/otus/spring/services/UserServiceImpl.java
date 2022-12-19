package ru.otus.spring.services;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.otus.spring.config.AppLocaleProperties;
import ru.otus.spring.controllers.IOService;
import ru.otus.spring.dao.UserRegistry;
import ru.otus.spring.model.User;

@Service
public class UserServiceImpl implements UserService {
    private final UserRegistry userRegistry;
    private final IOService ioService;
    private final MessageSource messageSource;
    private final AppLocaleProperties properties;

    public UserServiceImpl(UserRegistry userRegistry, @Qualifier("ioServiceConsole") IOService ioService,
                           MessageSource messageSource, AppLocaleProperties properties) {
        this.userRegistry = userRegistry;
        this.ioService = ioService;
        this.messageSource = messageSource;
        this.properties = properties;
    }

    @Override
    public Long createUser() {
        var messageFirstName = messageSource.getMessage("message.first-name", null, properties.getLocale());
        var messageLastName = messageSource.getMessage("message.last-name", null, properties.getLocale());
        User newUser = new User(ioService.inputWithPrompt(messageFirstName),
                ioService.inputWithPrompt(messageLastName));
        userRegistry.saveUser(newUser);
        return newUser.getId();
    }

    @Override
    public User getUser(Long id) {
        return userRegistry.getUser(id);
    }


}
