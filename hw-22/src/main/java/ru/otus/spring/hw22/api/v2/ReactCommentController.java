package ru.otus.spring.hw22.api.v2;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.spring.hw22.dto.CommentDto;
import ru.otus.spring.hw22.dto.mapper.DtoMapper;
import ru.otus.spring.hw22.exception.NotFoundException;
import ru.otus.spring.hw22.model.Book;
import ru.otus.spring.hw22.model.Comment;
import ru.otus.spring.hw22.repository.BookRepository;
import ru.otus.spring.hw22.repository.CommentRepository;

import java.util.Date;

@RestController
@RequestMapping("/api/v2")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class ReactCommentController {

    CommentRepository repository;

    DtoMapper<Comment, CommentDto> commentDtoMapper;

    @GetMapping("/{book_id}/comments")
    public Flux<CommentDto> getTop10ByBook__id(@PathVariable String book_id) {
        return repository.findTop10ByBook__id(book_id)
                .map(commentDtoMapper::toDto);
    }

    @GetMapping("/comment/{id}")
    public Mono<ResponseEntity<CommentDto>> get(@PathVariable String id) {
        return repository.findBy_id(id)
                .map(commentDtoMapper::toDto)
                .map(ResponseEntity::ok)
                .switchIfEmpty(Mono.fromCallable(() -> ResponseEntity.notFound().build()));
    }

    @PostMapping(value = "/comment", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<Void>> save(@RequestBody CommentDto commentDto) {
        Comment comment = commentDtoMapper.fromDto(commentDto);
        return repository.save(comment)
                .map(newComment ->
                        new ResponseEntity<Void>(HttpStatusCode.valueOf(HttpStatus.CREATED.value())));
    }

}
