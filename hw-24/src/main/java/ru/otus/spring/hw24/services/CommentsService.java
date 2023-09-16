package ru.otus.spring.hw24.services;

import ru.otus.spring.hw24.dto.CommentDto;

public interface CommentsService {

    void save(CommentDto commentDto);

    void delete(String _id);

}
