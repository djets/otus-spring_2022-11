package ru.otus.spring.hw19.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookDto {
    String id;

    @NotBlank(message = "{name-field-should-not-be-blank}")
    @Size(min = 2, max = 250, message = "{name-field-should-has-expected-size}")
    String title;

    GenreDto genreDto;

    List<AuthorDto> authorDtoList;

    public BookDto() {
        this.authorDtoList = new ArrayList<>();
    }
}
