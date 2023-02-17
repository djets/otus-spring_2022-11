package ru.otus.spring.hw11.services.commands;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.spring.hw11.services.CommentsServiceShell;

@ShellComponent
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class CommentCommand {
    CommentsServiceShell commentsService;

    @ShellMethod(value = "Delete comment by id", key = "-dc")
    public void deleteById(
            @ShellOption(help = "Input id genre") Long id
    ) {
        commentsService.delete(id);
    }
}
