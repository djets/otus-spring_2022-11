package ru.otus.spring.hw11.services.commands;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.spring.hw11.model.Comment;
import ru.otus.spring.hw11.services.CommentsServiceShell;

import java.util.List;
import java.util.stream.Collectors;

@ShellComponent
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class CommentCommand {
    CommentsServiceShell commentsService;

    @ShellMethod(value = "Find all comment by book id", key = "-fcab")
    public String findAllCommentsByBookId(Long id) {
        List<Comment> comments = commentsService.findAllByBook_BookId(id);
        if (comments != null) {
            return "Comment found for the book id: " + id + "\n" +
                    comments.stream()
                            .map(comment -> comment.getCreateData() +
                                    ", " + comment.getTextComment())
                            .collect(Collectors.joining("\n"));
        }
        return "Comments not found";
    }

    @ShellMethod(value = "Delete comment by id", key = "-dc")
    public void deleteById(
            @ShellOption(help = "Input id genre") Long id
    ) {
        commentsService.delete(id);
    }
}
