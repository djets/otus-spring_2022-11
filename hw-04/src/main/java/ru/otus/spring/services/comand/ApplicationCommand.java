package ru.otus.spring.services.comand;

import org.springframework.context.MessageSource;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import ru.otus.spring.config.AppLocaleProperties;
import ru.otus.spring.model.User;
import ru.otus.spring.services.ApplicationRunner;
import ru.otus.spring.services.UserService;

import java.util.HashMap;
import java.util.Map;

@ShellComponent
public class ApplicationCommand {
    private final ApplicationRunner applicationRunner;
    private final UserService userService;
    private final MessageSource messageSource;
    private final AppLocaleProperties properties;
    private Map<String, Long> mapIdFullUserName;
    private Long userId;

    public ApplicationCommand(ApplicationRunner applicationRunner, UserService userService, MessageSource messageSource, AppLocaleProperties properties) {
        this.applicationRunner = applicationRunner;
        this.userService = userService;
        this.messageSource = messageSource;
        this.properties = properties;
        this.mapIdFullUserName = new HashMap<>();
    }

    @ShellMethod(value = "Create user.", key = {"cu", "create-user"})
    public String createUser() {
        userId = userService.createUser();
        User user = userService.getUser(userId);
        mapIdFullUserName.put(user.getFirstName() + " " + user.getLastName(), userId);
        var messageWelcome = messageSource.getMessage("message.create-user",
                new Object[]{userService.getUser(userId)}, properties.getLocale());
        return messageWelcome;
    }

    @ShellMethod(value = "Run game command", key = {"r", "run"})
    @ShellMethodAvailability(value = "isTheUserCreated")
    public String appRun() {
        applicationRunner.run(userId);
        return "====================";
    }

    @ShellMethod(value = "Get result User. Example: shell:>get-result-user FirstNameUser LastNameUser", key = {"gru", "get-result-user"})
    @ShellMethodAvailability(value = "isTheUserCreated")
    public String getResultByUser(String firstName, String lastName) {
        String fullUserName = firstName + " " + lastName;
        return applicationRunner.getResultByUser(mapIdFullUserName.get(fullUserName));
    }

    private Availability isTheUserCreated() {
        var messageNoCreateUser = messageSource.getMessage("message.no-create-user", null, properties.getLocale());
        return userId == null ? Availability.unavailable(messageNoCreateUser) : Availability.available();
    }
}
