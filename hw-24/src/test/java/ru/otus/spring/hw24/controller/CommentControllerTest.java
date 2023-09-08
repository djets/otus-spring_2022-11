package ru.otus.spring.hw24.controller;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.spring.hw24.config.MongoConfigTest;
import ru.otus.spring.hw24.dto.BookDto;
import ru.otus.spring.hw24.dto.CommentDto;
import ru.otus.spring.hw24.services.BookService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CommentController.class)
@ActiveProfiles("test")
@TestPropertySource(locations = "classpath:application-test.yml")
@Import(MongoConfigTest.class)
@FieldDefaults(level = AccessLevel.PRIVATE)
class CommentControllerTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    BookService bookService;

    @WithMockUser(username = "user", authorities = {"ROLE_USER"})
    @Test
    void getCommentsByBookId() throws Exception {
        BookDto bookDto = new BookDto();
        bookDto.setId("643112ec9c7af15a9d628797");
        bookDto.setCommentDtoList(List.of(new CommentDto()));
        given(bookService.findById("643112ec9c7af15a9d628797")).willReturn(bookDto);
        given(bookService.findCommentsByBookId("643112ec9c7af15a9d628797")).willReturn(List.of(new CommentDto()));
        this.mvc.perform(get("/books/{id}/comments", bookDto.getId()))
                .andExpect(status().isOk())
                .andExpect(model().attribute("commentDtoList", hasSize(1)))
                .andExpect(model().attributeExists("commentDtoList"));
    }

    @WithMockUser(username = "user", authorities = {"ROLE_USER"})
    @Test
    void saveComments() throws Exception {
        BookDto bookDto = new BookDto();
        bookDto.setId("643112ec9c7af15a9d628797");
        CommentDto commentDto = new CommentDto();
        commentDto.setText("This a test comment");
        bookDto.setCommentDtoList(new ArrayList<>(Arrays.asList(commentDto)));

        given(bookService.findById(bookDto.getId())).willReturn(bookDto);
        given(bookService.save(bookDto)).willReturn(bookDto.getId());
        this.mvc.perform(post("/books/{id}/comments/save", bookDto.getId())
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/books"));
    }
}