package ru.otus.spring.hw19.services;

import ru.otus.spring.hw19.dto.CommentDto;
import ru.otus.spring.hw19.dto.CommentsBookDto;

import java.util.List;

public interface CommentsService {

    void save(CommentDto commentDto);

    void delete(String _id);

    CommentsBookDto findCommentsByBookId(String bookId);

}
