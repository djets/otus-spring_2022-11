package ru.otus.spring.hw24.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.otus.spring.hw24.dto.AuthorDto;
import ru.otus.spring.hw24.dto.BookDto;
import ru.otus.spring.hw24.services.BookService;

import java.util.List;

@Controller
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@RequestMapping("/books")
public class BooksController {

    private BookService bookService;

    @GetMapping(value = "")
    public String showAll(Model model) {
        model.addAttribute("booksDto", bookService.findAll());
        return "books";
    }

    @GetMapping(value = "/create")
    public String addBookPage(Model model) {
        BookDto bookDto = new BookDto();
        bookDto.getAuthorDtoList().add(new AuthorDto());
        model.addAttribute("bookDto", bookDto);
        return "createBook";
    }

    @GetMapping(value = "/edit{id}")
    public String editBookPage(@PathVariable("id") String id, Model model) {
        BookDto bookDto = bookService.findById(id);
        model.addAttribute("bookDto", bookDto);
        return "editBook";
    }

    @PostMapping(value = "/save")
    public String saveBooks(@ModelAttribute BookDto bookDto, Model model) {
        if (bookDto.getId() != null) {
            BookDto foundBook = bookService.findById(bookDto.getId());
            if (foundBook.getAuthorDtoList() != null) {
                List<AuthorDto> newAuthors = bookDto.getAuthorDtoList()
                        .stream()
                        .filter(authorDto -> authorDto.getId() == null)
                        .toList();
                foundBook.getAuthorDtoList().addAll(newAuthors);
            } else {
                foundBook.setAuthorDtoList(bookDto.getAuthorDtoList());
            }
            if (bookDto.getCommentDtoList().isEmpty() && !foundBook.getCommentDtoList().isEmpty()) {
                bookDto.setCommentDtoList(foundBook.getCommentDtoList());
            }
        }
        bookService.save(bookDto);
        model.addAttribute("books", bookService.findAll());
        return "redirect:/books";
    }
}
