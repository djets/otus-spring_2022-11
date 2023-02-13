INSERT INTO authors (id, name, surname)
VALUES (101, 'Robert Louis', 'Stevenson');
INSERT INTO authors (id, name, surname)
VALUES (102, 'Lewis', 'Carroll');
INSERT INTO authors (id, name, surname)
VALUES (103, 'Daniel', 'Defoe');
INSERT INTO authors (id, name, surname)
VALUES (104, 'Arthur', 'Conan Doyle');

INSERT INTO genres (id, name)
VALUES (101, 'Classic');
INSERT INTO genres (id, name)
VALUES (102, 'Action and Adventure');
INSERT INTO genres (id, name)
VALUES (103, 'Detective');
INSERT INTO genres (id, name)
VALUES (104, 'Fantasy');

INSERT INTO books (id, name, genre_id)
VALUES (101, 'Treasure Island', 102);
INSERT INTO books (id, name, genre_id)
VALUES (102, 'Alice in Wonderland', 104);
INSERT INTO books (id, name, genre_id)
VALUES (103, 'Robinson Crusoe', 102);
INSERT INTO books (id, name, genre_id)
VALUES (104, 'The Hound of the Baskervilles', 103);

INSERT INTO book_authors (author_id, book_id)
VALUES (101, 103);
INSERT INTO book_authors (author_id, book_id)
VALUES (102, 102);
INSERT INTO book_authors (author_id, book_id)
VALUES (103, 103);
INSERT INTO book_authors (author_id, book_id)
VALUES (104, 104);

INSERT INTO comments (id, text_comment, create_data, book_id)
values ( 101, 'A splendid book!', NOW(), 102 );
INSERT INTO comments (id, text_comment, create_data, book_id)
values ( 102, 'Excellent book!', NOW(), 102 );