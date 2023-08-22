package ru.otus.spring.hw19.api.v1;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.spring.hw19.config.MongoConfigTest;
import ru.otus.spring.hw19.dto.AuthorDto;
import ru.otus.spring.hw19.dto.BookDto;
import ru.otus.spring.hw19.dto.GenreDto;
import ru.otus.spring.hw19.services.BookService;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ActiveProfiles("test")
@TestPropertySource(locations = "classpath:application-test.yml")
@WebMvcTest(RestBookController.class)
@Import(MongoConfigTest.class)
@FieldDefaults(level = AccessLevel.PRIVATE)
class RestBookControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    BookService bookService;

    @Test
    void getBooks() throws Exception {
        BookDto bookDto = new BookDto("1", "Book 1",
                new GenreDto("1", "Genre 1"),
                new ArrayList<>(List.of(new AuthorDto("1", "John", "Wick"))
                )
        );
        when(bookService.findAll()).thenReturn(List.of(bookDto));

        mockMvc.perform(get("/api/v1/books"))
                .andExpect(status().isOk())
                .andExpect(result -> content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(result -> content().json(
                        "{\"id\":\"1\"," +
                                "\"title\":\"Book 1\"," +
                                "\"genreDto\":{\"id\":\"1\"," +
                                "\"nameGenre\":\"Genre 1\"}," +
                                "\"authorDtoList\":[" +
                                "{\"id\":\"1\"," +
                                "\"name\":\"John\"," +
                                "\"surname\":\"Wick\"}]}"
                ));
    }

    @Test
    void addBook() throws Exception {
        ArgumentCaptor<BookDto> argumentCaptor = ArgumentCaptor.forClass(BookDto.class);
        mockMvc.perform(post("/api/v1/books/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":\"2\"," +
                                "\"title\":\"Book 2\"," +
                                "\"genreDto\":{\"id\":\"2\"," +
                                "\"nameGenre\":\"Genre 2\"}," +
                                "\"authorDtoList\":[" +
                                "{\"id\":\"2\"," +
                                "\"name\":\"Robert Lewis\"," +
                                "\"surname\":\"Carroll\"}]}"
                        ))
                .andExpect(status().isOk())
                .andExpect(result -> content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(result -> content().json(
                                "{\"id\":\"2\"," +
                                        "\"title\":\"Book 2\"," +
                                        "\"genreDto\":{\"id\":\"2\"," +
                                        "\"nameGenre\":\"Genre 2\"}," +
                                        "\"authorDtoList\":[" +
                                        "{\"id\":\"2\"," +
                                        "\"name\":\"Robert Lewis\"," +
                                        "\"surname\":\"Carroll\"}]}"
                        )
                );
        verify(bookService, times(1)).save(Mockito.notNull());
        verify(bookService, times(1)).save(argumentCaptor.capture());
    }
}