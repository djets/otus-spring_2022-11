package ru.otus.spring.hw19.api.v1;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;
import ru.otus.spring.hw19.dto.BookDto;
import ru.otus.spring.hw19.services.BookService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequestMapping("/api/v1/books")
public class RestBookController {
    BookService bookService;

    @GetMapping("")
    public List<BookDto> getBooks() {
        return bookService.findAll();
    }

    @PostMapping("/save")
    public BookDto addBook(@RequestBody BookDto bookDto) {
        return bookService.findById(bookService.save(bookDto));
    }
}

