package ru.otus.spring.hw18.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.otus.spring.hw18.dto.BookDto;
import ru.otus.spring.hw18.services.BookService;

@Controller
@RequestMapping("/books")
public class BooksController {

    @Autowired
    private BookService bookService;

    @GetMapping(value = "")
    public String showAll(Model model) {
        model.addAttribute("booksDto", bookService.findAll());
        return "books";
    }

    @GetMapping(value = "/create")
    public String addBookPage(Model model) {
        BookDto bookDto = new BookDto();
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

        bookService.save(bookDto);

        model.addAttribute("books", bookService.findAll());

        return "redirect:/books";
    }

}
