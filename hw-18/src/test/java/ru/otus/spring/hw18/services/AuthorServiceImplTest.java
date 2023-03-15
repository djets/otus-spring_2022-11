package ru.otus.spring.hw18.services;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;
import ru.otus.spring.hw18.model.Author;

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

    @ParameterizedTest
    @CsvSource({
            "John,Doe",
            "Jane,Doe",
            "Bob,Smith"
    })
    @DisplayName("ищет авторов по имени и фамилии")
    void shouldFindAuthorsByNameAndSurname(String name, String surname) {
        String idSave = authorService.save(name, surname);

        List<String> foundAuthors = authorService.findByNameAndSurname(name, surname);
        assertThat(foundAuthors).isNotNull().hasSize(1);
        assertThat(foundAuthors.get(0)).isEqualTo(idSave);
        assertThat(authorService.findById(idSave).getName()).isEqualTo(name);
        assertThat(authorService.findById(idSave).getSurname()).isEqualTo(surname);
    }

    @ParameterizedTest
    @CsvSource({
            "John, Johnson",
            "Jane, Smith",
            "Bob, Williams"
    })
    @DisplayName("Корректное изменение фамилии автора")
    void shouldUpdateAuthorNname(String firstName, String changedSurname) {
        String id = authorService.save(firstName, "Doe");

        authorService.updateSurnameAuthor(id, changedSurname);

        Author updatedAuthor = authorService.findById(id);
        assertThat(updatedAuthor.getName()).isEqualTo(firstName);
    }



    @ParameterizedTest
    @CsvSource({
            "Doe,Smith",
            "Doe,Johnson",
            "Smith,Williams"
    })
    @DisplayName("Корректное изменение фамилии автора")
    void shouldUpdateAuthorSurname(String surname, String changedSurname) {
        String id = authorService.save("John", surname);

        authorService.updateSurnameAuthor(id, changedSurname);

        Author updatedAuthor = authorService.findById(id);
        assertThat(updatedAuthor.getSurname()).isEqualTo(changedSurname);
    }


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


