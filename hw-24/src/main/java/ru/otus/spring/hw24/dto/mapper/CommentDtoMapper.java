package ru.otus.spring.hw24.dto.mapper;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import ru.otus.spring.hw24.dto.CommentDto;
import ru.otus.spring.hw24.model.Comment;

@Component
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class CommentDtoMapper implements DtoMapper<Comment, CommentDto> {

    @Override
    public CommentDto toDto(Comment comment) {
        return new CommentDto(
                comment.get_id(),
                comment.getTextComment(),
                comment.getCreateData()
        );
    }

    @Override
    public Comment fromDto(CommentDto commentDto) {
        return new Comment(
                commentDto.getId(),
                commentDto.getText(),
                commentDto.getCreateData(),
                null
        );
    }
}
