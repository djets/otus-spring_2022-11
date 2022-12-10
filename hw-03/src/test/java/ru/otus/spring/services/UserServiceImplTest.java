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
import ru.otus.spring.controllers.IOService;
import ru.otus.spring.dao.UserRegistry;
import ru.otus.spring.model.User;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    Logger logger = LoggerFactory.getLogger(UserServiceImplTest.class);
    @Mock
    private UserRegistry userRegistry;
    @Mock
    private IOService ioService;
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        userService = new UserServiceImpl(userRegistry, ioService);
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