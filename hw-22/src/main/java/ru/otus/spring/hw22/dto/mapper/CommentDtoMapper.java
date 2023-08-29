package ru.otus.spring.hw22.dto.mapper;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import ru.otus.spring.hw22.dto.CommentDto;
import ru.otus.spring.hw22.model.Comment;
import ru.otus.spring.hw22.repository.BookRepository;

import java.util.Date;

@Component
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class CommentDtoMapper implements DtoMapper<Comment, CommentDto> {
    BookRepository bookRepository;

    @Override
    public CommentDto toDto(Comment comment) {
        return new CommentDto(
                comment.get_id(),
                comment.getTextComment(),
                comment.getCreateData(),
                comment.getBook().get_id()
        );
    }

    @Override
    public Comment fromDto(CommentDto commentDto) {
        return new Comment(
                null,
                commentDto.text(),
                new Date(),
                bookRepository.findBy_id(commentDto.bookId()).block()
        );
    }
}
