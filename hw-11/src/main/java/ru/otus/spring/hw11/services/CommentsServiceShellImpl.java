package ru.otus.spring.hw11.services;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.hw11.dao.CommentRepository;

@Service
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class CommentsServiceShellImpl implements CommentsServiceShell {
    CommentRepository repository;

    @Override
    @Transactional
    public void delete(Long id) {
        repository.findById(id).ifPresent(repository::delete);
    }
}
