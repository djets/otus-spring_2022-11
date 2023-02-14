package ru.otus.spring.hw11.services;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.hw11.dao.CommentRepository;
import ru.otus.spring.hw11.model.Comment;

import java.util.List;

@Service
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class CommentsServiceShellImpl implements CommentsServiceShell {
    CommentRepository repository;

    @Override
    @Transactional(readOnly = true)
    public List<Comment> findAllByBook_BookId(Long id) {
        List<Comment> comments = repository.findAllByBookId(id);
        if (comments.isEmpty()) {
            return null;
        }
        return comments;
    }

    @Override
    @Transactional
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
