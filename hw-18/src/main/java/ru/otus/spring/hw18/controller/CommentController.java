package ru.otus.spring.hw18.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.spring.hw18.dto.CommentDto;
import ru.otus.spring.hw18.services.BookService;
import ru.otus.spring.hw18.services.CommentsService;

import java.util.List;

@Controller
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE) public class CommentController {
    CommentsService commentsService;
    BookService bookService;

    @GetMapping("/books/{id}/comments")
    public String getCommentsByBookId(@PathVariable String id, Model model) {
        List<CommentDto> commentDtoList = bookService.findCommentsByBookId(id);
        model.addAttribute("commentDtoList", commentDtoList);
        return "comments";
    }
}
