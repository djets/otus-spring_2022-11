package ru.otus.spring.hw22.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;


public record BookDto(
        String id,

        @NotBlank(message = "{name-field-should-not-be-blank}")
        @Size(min = 2, max = 250, message = "{name-field-should-has-expected-size}")
        String title,
        String genreName,
        List<String> authorList
) {
}
