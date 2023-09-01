package ru.otus.spring.hw24.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.otus.spring.hw24.dto.BookDto;
import ru.otus.spring.hw24.dto.CommentDto;
import ru.otus.spring.hw24.services.BookService;

import javax.annotation.security.RolesAllowed;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RolesAllowed("ROLE_USER")
public class CommentController {

    BookService bookService;

    @GetMapping("/books/{id}/comments")
    public String getCommentsByBookId(@PathVariable String id, Model model) {
        BookDto bookDto = bookService.findById(id);
        List<CommentDto> commentDtoList = bookDto.getCommentDtoList();
        model.addAttribute("commentDtoList", commentDtoList);
        model.addAttribute("bookDto", bookDto);
        return "comments";
    }

    @PostMapping(value = "/books/{id}/comments/save")
    public String saveComments(@ModelAttribute BookDto bookDto, Model model) {
        BookDto foundBook = bookService.findById(bookDto.getId());
        if (foundBook.getCommentDtoList() != null) {
            List<CommentDto> newComment = bookDto.getCommentDtoList()
                    .stream()
                    .filter(commentDto -> commentDto.getId() == null)
                    .collect(Collectors.toList());
            foundBook.getCommentDtoList().addAll(newComment);
        } else {
            foundBook.setCommentDtoList(bookDto.getCommentDtoList());
        }
        bookService.save(foundBook);
        model.addAttribute("books", bookService.findAll());
        return "redirect:/books";
    }
}

