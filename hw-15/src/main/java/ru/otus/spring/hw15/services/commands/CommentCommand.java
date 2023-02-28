package ru.otus.spring.hw15.services.commands;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.spring.hw15.model.Comment;
import ru.otus.spring.hw15.services.CommentsService;

import java.util.List;
import java.util.stream.Collectors;

@ShellComponent
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class CommentCommand {
    CommentsService commentsService;

    @ShellMethod(value = "find all by book id", key = "-c")
    public String findAllByBookId(
            @ShellOption(help = "Input id book") Long id
    ) {
        List<Comment> comments = commentsService.findAllByBookId(id);
        return "\nComments:\n" + comments.stream()
                .map(comment -> comment.getCreateData().toString().substring(0,19) +
                        " " + comment.getTextComment())
                .collect(Collectors.joining("\n-------\n"));
    }

    @ShellMethod(value = "Delete comment by id", key = "-dc")
    public void deleteById(
            @ShellOption(help = "Input id comment") Long id
    ) {
        commentsService.delete(id);
    }
}
