package ru.otus.spring.hw19.dto.mapper;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import ru.otus.spring.hw19.dto.BookDto;
import ru.otus.spring.hw19.dto.CommentDto;
import ru.otus.spring.hw19.model.Book;
import ru.otus.spring.hw19.model.Comment;
import ru.otus.spring.hw19.services.BookService;

@Component
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class CommentDtoMapper implements DtoMapper<Comment, CommentDto> {
    BookService bookService;
    DtoMapper<Book, BookDto> bookDtoMapper;

    @Override
    public CommentDto toDto(Comment comment) {
        return new CommentDto(
                comment.get_id(),
                comment.getTextComment(),
                comment.getCreateData(),
                comment.get_id()
        );
    }

    @Override
    public Comment fromDto(CommentDto commentDto) {
        return new Comment(
                commentDto.getId(),
                commentDto.getText(),
                commentDto.getCreateData(),
                bookDtoMapper.fromDto(bookService.findById(commentDto.getBookId()))
        );
    }
}
