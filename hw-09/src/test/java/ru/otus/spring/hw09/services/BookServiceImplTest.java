package ru.otus.spring.hw09.services;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import ru.otus.spring.hw09.dao.BookDao;
import ru.otus.spring.hw09.model.Book;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@DisplayName("Service для работы с книгами должно")
@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource(locations = "classpath:application-test.yml")
@ExtendWith(MockitoExtension.class)
@Import(BookServiceImpl.class)
@FieldDefaults(level = AccessLevel.PRIVATE)
class BookServiceImplTest {
    @MockBean
    BookDao bookDao;
    @MockBean
    AuthorService authorService;
    @Autowired
    BookService bookService;

    @DisplayName("искать по id")
    @Test
    void getByIdTest() {
        Book expectedBook = new Book(1L, "TestBook", null, null);
        when(bookDao.findById(1L)).thenReturn(expectedBook);
        assertThat(bookDao.findById(1L)).usingRecursiveComparison().isEqualTo(expectedBook);
    }
    @DisplayName("сохранять книгу")
    @Test
    void saveTest() {
        Book expectedBook = new Book(null, "TestBook", null, null);
        when(bookDao.insert(expectedBook)).thenReturn(1L);
        assertThat(bookService.save("TestBook", null, null, null)).isEqualTo(1L);
    }
    @DisplayName("обновлять книгу")
    @Test
    void updateTest() {
        when(authorService.save("Test", "Name")).thenReturn(1L);
        when(bookDao.update(1L, Map.of("author_id", 1L))).thenReturn(1);
        bookService.update(1L, "Test Name", null);
        verify(bookDao).update(1L, Map.of("author_id", 1L));
    }
    @DisplayName("искать по имени")
    @Test
    void findByNameTest() {
        when(bookDao.findByName("TestBook")).thenReturn(List.of(1L));
        assertThat(bookService.findByName("TestBook")).isEqualTo(1L);
    }
    @DisplayName("удалять книгу")
    @Test
    void deleteTest() {
        when(bookDao.delete(any())).thenReturn(1);
        assertThat(bookService.delete(1L)).isTrue();
        verify(bookDao).delete(any());
    }
    @DisplayName("находить все книги")
    @Test
    void getAllTest() {
        Book expectedBook = new Book(1L, "TestBook", null, null);
        when(bookDao.findAll()).thenReturn(List.of(new Book(1L, "TestBook", null, null)));
        assertThat(bookService.getAll().get(0)).usingRecursiveComparison().isEqualTo(expectedBook);
    }
}