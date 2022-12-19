package ru.otus.spring.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ResourceBundleMessageSource;
import ru.otus.spring.config.AppLocaleProperties;
import ru.otus.spring.controllers.IOService;
import ru.otus.spring.dao.UserRegistry;
import ru.otus.spring.model.User;

import java.util.Locale;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    @Mock
    private UserRegistry userRegistry;
    @Mock
    private IOService ioService;
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("slumdog");
        messageSource.setDefaultEncoding("UTF-8");
        AppLocaleProperties properties = new AppLocaleProperties();
        properties.setLocale(Locale.ENGLISH);
        userService = new UserServiceImpl(userRegistry, ioService, messageSource, properties);
    }

    @ParameterizedTest
    @CsvSource(value = {
            "Boris, The Bullet Dodger",
            "Tony, Bullet Tooth"
    }, ignoreLeadingAndTrailingWhitespace = true)
    void createUserTest(String firstName, String lastName) {
        User testUser = new User(firstName, lastName);
        when(ioService.inputWithPrompt(any())).thenReturn(firstName, lastName);
        ArgumentCaptor<User> argument = ArgumentCaptor.forClass(User.class);
        userService.createUser();
        verify(userRegistry).saveUser(argument.capture());
        assertThat(argument.getValue().getFirstName()).isEqualTo(firstName);
        assertThat(argument.getValue().getLastName()).isEqualTo(lastName);
    }

    @Test
    void getUserTest() {
        User testUser = new User("Alex", "Denovitz");
        when(userRegistry.getUser(any())).thenReturn(testUser);
        assertThat(userService.getUser(any())).isEqualTo(testUser);
    }
}