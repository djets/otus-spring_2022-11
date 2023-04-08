package ru.otus.spring.hw18.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.spring.hw18.dto.BookDto;
import ru.otus.spring.hw18.repository.BookRepository;
import ru.otus.spring.hw18.services.BookService;

import java.util.List;

import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BooksController.class)
class BooksControllerTest {
    @Autowired
    MockMvc mvc;

    @MockBean
    BookService bookService;

    @BeforeEach
    void setUp() {
    }

    @Test
    void showAll() throws Exception {
        given(bookService.findAll()).willReturn(List.of(new BookDto()));
        this.mvc.perform(get("/books"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("booksDto"));
    }

    @Test
    void addBookPage() throws Exception {
        this.mvc.perform(get("/books/create"))
                .andExpect(status().isOk());
    }

    @Test
    void editBookPage() throws Exception {
        given(bookService.findById("643112ec9c7af15a9d628797"))
                .willReturn(new BookDto());
        this.mvc.perform(get("/books/edit643112ec9c7af15a9d628797"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("bookDto", notNullValue()));
    }

    @Test
    void saveBooks() throws Exception {
        BookDto bookDto = new BookDto();
        bookDto.setId("1");
        given(bookService.findById(bookDto.getId())).willReturn(bookDto);
        given(bookService.save(bookDto)).willReturn("1");
        this.mvc.perform(post("/books/save")
                        .param("id", "1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/books"));
    }
}