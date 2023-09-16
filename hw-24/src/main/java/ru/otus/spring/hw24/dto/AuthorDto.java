package ru.otus.spring.hw24.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class AuthorDto {
    String id;

    @NotBlank(message = "{name-field-should-not-be-blank}")
    @Size(min = 2, max = 100)
    String name;

    @NotBlank(message = "{name-field-should-not-be-blank}")
    @Size(min = 2, max = 100)
    String surname;
}
