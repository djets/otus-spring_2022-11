package ru.otus.spring.hw22.api.v2;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.spring.hw22.dto.BookDto;
import ru.otus.spring.hw22.dto.mapper.DtoMapper;
import ru.otus.spring.hw22.model.Book;
import ru.otus.spring.hw22.repository.BookRepository;

@RestController
@RequestMapping("/api/v2")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class ReactBookController {

    BookRepository repository;

    DtoMapper<Book, BookDto> bookDtoMapper;

    @GetMapping("/{id}")
    public Mono<ResponseEntity<BookDto>> get(@PathVariable String id) {
        return repository.findBy_id(id)
                .map(bookDtoMapper::toDto)
                .map(ResponseEntity::ok)
                .switchIfEmpty(Mono.fromCallable(() -> ResponseEntity.notFound().build()));
    }

    @GetMapping("/books")
    public Flux<BookDto> get() {
        return repository.findAll()
                .map(bookDtoMapper::toDto);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<Void>> save (@RequestBody BookDto bookDto) {
        return repository.save(bookDtoMapper.fromDto(bookDto))
                .map(newComment ->
                        new ResponseEntity<Void>(HttpStatusCode.valueOf(HttpStatus.CREATED.value())));
    }
}
