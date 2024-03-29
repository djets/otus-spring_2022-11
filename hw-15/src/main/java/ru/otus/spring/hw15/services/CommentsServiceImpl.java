package ru.otus.spring.hw15.services;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.spring.hw15.repository.CommentRepository;
import ru.otus.spring.hw15.model.Comment;

import java.util.List;

@Service
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class CommentsServiceImpl implements CommentsService {
    CommentRepository repository;
    BookService bookService;

    @Override
    public void delete(String _id) {
        repository.findById(_id).ifPresent(repository::delete);
    }

    @Override
    public List<Comment> findAllByBookId(String _id) {
        return bookService.findById(_id).getComments();
    }
}
