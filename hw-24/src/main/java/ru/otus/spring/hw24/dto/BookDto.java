package ru.otus.spring.hw24.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
public class BookDto {
    String id;

    @NotBlank(message = "{name-field-should-not-be-blank}")
    @Size(min = 2, max = 250, message = "{name-field-should-has-expected-size}")
    String title;

    GenreDto genreDto;
    List<CommentDto> commentDtoList;
    List<AuthorDto> authorDtoList;

    public BookDto() {
        this.authorDtoList = new ArrayList<>();
        this.commentDtoList = new ArrayList<>();
    }
}
