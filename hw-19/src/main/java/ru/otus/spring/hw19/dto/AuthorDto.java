package ru.otus.spring.hw19.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthorDto {
    String id;

    @NotBlank(message = "{name-field-should-not-be-blank}")
    @Size(min = 2, max = 100)
    String name;

    @NotBlank(message = "{name-field-should-not-be-blank}")
    @Size(min = 2, max = 100)
    String surname;
}
