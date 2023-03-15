//package ru.otus.spring.hw18.services.commands;
//
//import lombok.AccessLevel;
//import lombok.RequiredArgsConstructor;
//import lombok.experimental.FieldDefaults;
//import org.springframework.shell.standard.ShellComponent;
//import org.springframework.shell.standard.ShellMethod;
//import org.springframework.shell.standard.ShellOption;
//import ru.otus.spring.hw18.model.Comment;
//import ru.otus.spring.hw18.services.CommentsService;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//@ShellComponent
//@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
//@RequiredArgsConstructor
//public class CommentCommand {
//    CommentsService commentsService;
//
//    @ShellMethod(value = "find all by book id", key = "-c")
//    public String findAllByBookId(
//            @ShellOption(help = "Input id book") String _id
//    ) {
//        List<Comment> comments = commentsService.findAllByBookId(_id);
//        return "\nComments:\n" + comments.stream()
//                .map(comment -> comment.getCreateData().toString().substring(0,19) +
//                        " " + comment.getTextComment())
//                .collect(Collectors.joining("\n-------\n"));
//    }
//
//    @ShellMethod(value = "Delete comment by id", key = "-dc")
//    public void deleteById(
//            @ShellOption(help = "Input id comment") String _id
//    ) {
//        commentsService.delete(_id);
//    }
//}
