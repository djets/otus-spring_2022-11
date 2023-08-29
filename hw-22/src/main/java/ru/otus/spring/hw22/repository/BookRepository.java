package ru.otus.spring.hw22.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.spring.hw22.model.Book;

import javax.validation.constraints.NotNull;

public interface BookRepository extends ReactiveMongoRepository<Book, String> {
    @NotNull Mono<Book> findBy_id(@NotNull String book_id);

    Flux<Book> findByTitle(String title);

    Flux<Book> findBooksByGenre__id(String genre_id);

    Flux<Book> findBooksByAuthors__id(String authors_id);
}
