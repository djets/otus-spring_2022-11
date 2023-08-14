package ru.otus.spring.hw19.services;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import ru.otus.spring.hw19.dto.CommentDto;
import ru.otus.spring.hw19.dto.CommentsBookDto;
import ru.otus.spring.hw19.dto.mapper.CommentDtoMapper;
import ru.otus.spring.hw19.model.Comment;
import ru.otus.spring.hw19.repository.CommentRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    @Override
    public CommentsBookDto findCommentsByBookId(String bookId) {
        List<Comment> commentList = repository.findAllByBook__id(bookId);
        if(commentList.isEmpty()){
            return new CommentsBookDto(new ArrayList<>());
        }
        return new CommentsBookDto(commentList.stream()
                .map(commentDtoMapper::toDto)
                .collect(Collectors.toList()));
    }
}
