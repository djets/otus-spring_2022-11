package ru.otus.spring.hw18.services;

import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.hw18.dto.CommentDto;
import ru.otus.spring.hw18.model.Comment;

import java.util.List;

public interface CommentsService {

    void save(CommentDto commentDto);

    void delete(String _id);

}
