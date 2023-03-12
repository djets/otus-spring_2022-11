CREATE SEQUENCE genre_seq START WITH 1000 INCREMENT BY 10;

DROP TABLE IF EXISTS genres;

CREATE TABLE genres
(
    id   BIGINT NOT NULL,
--     id BIGINT NOT NULL DEFAULT nextval(genre_seq),
    name VARCHAR(255),
    CONSTRAINT pk_genres PRIMARY KEY (id)
);


CREATE SEQUENCE author_seq START WITH 1000 INCREMENT BY 10;

DROP TABLE IF EXISTS authors;

CREATE TABLE authors
(
    id      BIGINT NOT NULL,
    name    VARCHAR(255),
    surname VARCHAR(255),
    CONSTRAINT pk_authors PRIMARY KEY (id)
);


CREATE SEQUENCE book_seq START WITH 1000 INCREMENT BY 10;


CREATE TABLE book_authors
(
    author_id BIGINT NOT NULL,
    book_id   BIGINT NOT NULL
);

CREATE TABLE books
(
    id       BIGINT NOT NULL,
    name     VARCHAR(255),
    genre_id BIGINT,
    CONSTRAINT pk_books PRIMARY KEY (id)
);

ALTER TABLE books
    ADD CONSTRAINT fk_genre_books_id FOREIGN KEY (genre_id) REFERENCES genres (id);

ALTER TABLE book_authors
    ADD CONSTRAINT fk_book_author_on_author FOREIGN KEY (author_id) REFERENCES authors (id);

ALTER TABLE book_authors
    ADD CONSTRAINT fk_book_author_on_book FOREIGN KEY (book_id) REFERENCES books (id);


CREATE SEQUENCE comment_seq START WITH 1000 INCREMENT BY 50;

DROP TABLE IF EXISTS comments;

CREATE TABLE comments
(
    id           BIGINT NOT NULL,
    text_comment VARCHAR(3000),
    create_data    TIMESTAMP NOT NULL,
    book_id      BIGINT,
    CONSTRAINT pk_comments PRIMARY KEY (id)
);

ALTER TABLE comments
    ADD CONSTRAINT FK_COMMENTS_ON_BOOK FOREIGN KEY (book_id) REFERENCES books (id);
