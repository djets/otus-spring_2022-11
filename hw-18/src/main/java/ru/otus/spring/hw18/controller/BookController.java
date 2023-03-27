package ru.otus.spring.hw18.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.spring.hw18.dto.BookDto;
import ru.otus.spring.hw18.dto.mapper.BookDtoMapper;
import ru.otus.spring.hw18.model.Book;
import ru.otus.spring.hw18.services.BookService;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Slf4j
public class BookController {

    BookDtoMapper bookDtoMapper;

    BookService service;

    @GetMapping("/")
    public String listPage(Model model) {
        List<BookDto> bookDtoList = service.findAll()
                .stream()
                .map(bookDtoMapper::toDto)
                .collect(Collectors.toList());
        model.addAttribute("bookDtoList", bookDtoList);
        return "books";
    }

    @GetMapping("/book-edit")
    public String editPage(@RequestParam("id") String id, Model model) {
        BookDto bookDto = bookDtoMapper.toDto(service.findById(id));
        model.addAttribute("bookDto", bookDto);
        return "book-edit";
    }

    @PostMapping("/book-edit")
    public String saveBook(
            @Valid @ModelAttribute("bookDto") BookDto bookDto,
//            @RequestParam String _id,
//            @RequestParam String genreName,
////            @RequestParam String authorName,
            BindingResult bindingResult,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            return "book-edit";
        }
//        book.set_id(_id);
//        log.info(book.get_id());
//        log.info(book.getGenre().getName());
        service.save(bookDtoMapper.fromDto(bookDto));
//        Optional.ofNullable(genreName).ifPresent(s -> service.addGenre(_id, s));
////        Arrays.stream(authorName).forEach(p -> service.addAuthor(_id, p.split(" ")[0], p.split(" ")[1]));
        return "redirect:/";
    }


}
