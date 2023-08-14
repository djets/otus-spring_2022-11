package ru.otus.spring.hw19.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CommentDto {
    String id;

    @NotBlank(message = "{text-field-should-not-be-blank}")
    @Size(min = 2, max = 3000, message = "{text-field-should-has-expected-size}")
    String text;

    Date createData = new Date();
    String bookId;
}
