package ru.otus.spring.hw22.dto.mapper;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import ru.otus.spring.hw22.dto.CommentDto;
import ru.otus.spring.hw22.exception.NotFoundException;
import ru.otus.spring.hw22.model.Book;
import ru.otus.spring.hw22.model.Comment;
import ru.otus.spring.hw22.repository.BookRepository;

import java.util.Date;

@Component
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class CommentDtoMapper implements DtoMapper<Comment, CommentDto> {

    @Override
    public CommentDto toDto(Comment comment) {
        return new CommentDto(
                comment.get_id(),
                comment.getTextComment(),
                comment.getCreateData(),
                comment.getBookId()
        );
    }

    @Override
    public Comment fromDto(CommentDto commentDto) {
        return new Comment(
                null,
                commentDto.text(),
                new Date(),
                commentDto.bookId()
        );
    }
}
