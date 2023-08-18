package ru.otus.spring.hw19.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.otus.spring.hw19.dto.BookDto;
import ru.otus.spring.hw19.dto.CommentsBookDto;
import ru.otus.spring.hw19.services.BookService;
import ru.otus.spring.hw19.services.CommentsService;

@Controller
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class CommentController {
    CommentsService commentsService;

    BookService bookService;

    @GetMapping("/books/{id}/comments")
    public String getCommentsByBookId(@PathVariable String id, Model model) {
        BookDto bookDto = bookService.findById(id);
        model.addAttribute("commentsBookDto", commentsService.findCommentsByBookId(id));
        model.addAttribute("bookDto", bookDto);
        return "comments";
    }

    @GetMapping("/api/books/{id}/comments")
    public String getCommentsByBookIdRest(@PathVariable String id, Model model) {
        BookDto bookDto = bookService.findById(id);
        model.addAttribute("commentsBookDto", commentsService.findCommentsByBookId(id));
        model.addAttribute("bookDto", bookDto);
        return "comment_rest";
    }

    @PostMapping(value = "/books/{id}/comments/save")
    public String saveComments(@ModelAttribute BookDto bookDto, CommentsBookDto commentsBookDto, Model model) {
        if (!commentsBookDto.getCommentDtoList().isEmpty()) {
            commentsBookDto.getCommentDtoList().stream()
                    .filter(commentDto -> commentDto.getId() == null)
                    .peek(commentDto -> commentDto.setBookId(bookDto.getId()))
                    .forEach(commentsService::save);
        }
        model.addAttribute("books", bookService.findAll());
        return "redirect:/books";
    }
}

