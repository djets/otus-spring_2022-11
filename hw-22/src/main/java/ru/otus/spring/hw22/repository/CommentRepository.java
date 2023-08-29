package ru.otus.spring.hw22.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.spring.hw22.model.Comment;

import javax.validation.constraints.NotNull;

public interface CommentRepository extends ReactiveMongoRepository<Comment, String> {
    @NotNull Mono<Comment> findBy_id(@NotNull String comment_id);

//    Mono<Comment> save(Mono<Comment> commentMono);

    Flux<Comment> findAllByBookId(String book_id);

    Flux<Comment> findTop10ByBookId(String book_id);
}

