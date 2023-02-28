package ru.otus.spring.hw15.services;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.hw15.dao.CommentRepository;
import ru.otus.spring.hw15.model.Comment;

import java.util.List;

@Service
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class CommentsServiceImpl implements CommentsService {
    CommentRepository repository;
    BookService bookService;

    @Override
    @Transactional
    public void delete(Long id) {
        repository.findById(id).ifPresent(repository::delete);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Comment> findAllByBookId(Long id) {
        return bookService.findById(id, true).getComments();
    }
}
