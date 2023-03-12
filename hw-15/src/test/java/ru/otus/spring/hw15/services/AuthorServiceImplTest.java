package ru.otus.spring.hw15.services;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.spring.hw15.model.Author;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest
@TestPropertySource(properties = "mongock.enabled=false")
@Import(AuthorServiceImpl.class)
@DisplayName("Класс AuthorServiceImpl должен")
class AuthorServiceImplTest {

    @Autowired
    private AuthorService authorService;

    @Test
    @DisplayName("добавлять автора")
    void shouldAddAuthor() {
        String firstName = "John";
        String lastName = "Doe";
        String id = authorService.save(firstName, lastName);
        assertThat(id).isNotBlank();

        Author actualAuthor = authorService.findById(id);
        assertThat(actualAuthor).isNotNull();
        assertThat(actualAuthor.getName()).isEqualTo(firstName);
        assertThat(actualAuthor.getSurname()).isEqualTo(lastName);
    }

    @Test
    @DisplayName("ищет автора по id")
    void shouldFindAuthorById() {
        String firstName = "John";
        String lastName = "Doe";
        String id = authorService.save(firstName, lastName);

        Author actualAuthor = authorService.findById(id);

        assertThat(actualAuthor).isNotNull();
        assertThat(actualAuthor.getName()).isEqualTo(firstName);
        assertThat(actualAuthor.getSurname()).isEqualTo(lastName);
    }

    @Test
    @DisplayName("ищет авторов по имени")
    void shouldFindAuthorsByName() {
        String name1 = "John";
        String surname1 = "Doe";
        String name2 = "Jane";
        String surname2 = "Doe";
        String name3 = "Bob";
        String surname3 = "Smith";
        String id1 = authorService.save(name1, surname1);
        String id2 = authorService.save(name2, surname2);
        String id3 = authorService.save(name3, surname3);

        List<String> idFoundAuthors2 = authorService.findByNameAndSurname(name2, surname2);
        assertThat(idFoundAuthors2).isNotNull().hasSize(1);
        assertThat(idFoundAuthors2.get(0)).isEqualTo(id2);
        assertThat(authorService.findById(id2).getName()).isEqualTo(name2);
        assertThat(authorService.findById(id2).getSurname()).isEqualTo(surname2);

        List<String> idFoundAuthors3 = authorService.findByNameAndSurname(name3, surname3);
        assertThat(idFoundAuthors3).isNotNull().hasSize(1);
        assertThat(idFoundAuthors3.get(0)).isEqualTo(id3);
        assertThat(authorService.findById(id3).getName()).isEqualTo(name3);
        assertThat(authorService.findById(id3).getSurname()).isEqualTo(surname3);

    }

//    @Test
//    @DisplayName("обновлять автора")
//    void shouldUpdateAuthor() {
//        String firstName = "John";
//        String lastName = "Doe";
//        String newFirstName = "Jane";
//        String newLastName = "Smith";
//        String id = authorService.save(firstName, lastName);
//
//        Author author = authorService.findById(id);
//        author.setName(newFirstName);
//        author.setSurname(newLastName);
//        authorService.updateNameAuthor(author);
//
//        Author updatedAuthor = authorService.findById(id);
//        assertThat(updatedAuthor.getFirstName()).isEqualTo(newFirstName);
//        assertThat(updatedAuthor.getLastName()).isEqualTo(newLastName);
//    }

    @Test
    @DisplayName("удалять автора")
    void shouldDeleteAuthor() {
        String firstName = "John";
        String lastName = "Doe";
        String id = authorService.save(firstName, lastName);

        authorService.delete(id);

        assertThat(authorService.findById(id)).isNull();
    }

}


