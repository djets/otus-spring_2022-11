package ru.otus.spring.hw22.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;

public record CommentDto(
        String id,
        @NotBlank(message = "{text-field-should-not-be-blank}")
        @Size(min = 2, max = 3000, message = "{text-field-should-has-expected-size}")
        String text,
        Date createData,
        String bookId
) {
}
