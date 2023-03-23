package ru.otus.spring.hw18.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.spring.hw18.model.Book;
import ru.otus.spring.hw18.services.BookService;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class BookController {

    BookService service;

    @GetMapping("/")
    public String listPage(Model model) {
        List<Book> books = service.findAll();
        model.addAttribute("books", books);
        return "books";
    }

    @GetMapping("/book-edit")
    public String editPage(@RequestParam("id") String id, Model model) {
        Book book = service.findById(id);
        model.addAttribute("book", book);
        return "book-edit";
    }

    @PostMapping("/book-edit")
    public String saveBook(@Valid @ModelAttribute("book") Book book,
                             BindingResult bindingResult, Model model) {
//        if (bindingResult.hasErrors()) {
//            return "book-edit";
//        }
        model.addAttribute("book", book);
        service.save(book);
        return "redirect:/";
    }
}
