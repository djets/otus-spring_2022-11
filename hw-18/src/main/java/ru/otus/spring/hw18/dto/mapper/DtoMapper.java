package ru.otus.spring.hw18.dto.mapper;

public interface DtoMapper<S, C> {
    public C toDto(S s);

    S fromDto(C c);
}
