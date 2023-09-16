package ru.otus.spring.hw24.services;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import ru.otus.spring.hw24.dto.CommentDto;
import ru.otus.spring.hw24.dto.mapper.CommentDtoMapper;
import ru.otus.spring.hw24.repository.CommentRepository;

@Service
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class CommentsServiceImpl implements CommentsService {
    CommentRepository repository;
    CommentDtoMapper commentDtoMapper;

    @Override
    public void save(CommentDto commentDto) {
        repository.save(commentDtoMapper.fromDto(commentDto));
    }

    @Override
    public void delete(String _id) {
        repository.findById(_id).ifPresent(repository::delete);
    }
}
