package ru.otus.spring.hw22.dto.mapper;

public interface DtoMapper<S, C> {
    C toDto(S s);

    S fromDto(C c);
}
