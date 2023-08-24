package ru.otus.spring.hw19.mongock.changelog;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.github.cloudyrock.mongock.driver.mongodb.springdata.v3.decorator.impl.MongockTemplate;
import com.mongodb.client.MongoDatabase;
import lombok.extern.slf4j.Slf4j;
import ru.otus.spring.hw19.model.Author;
import ru.otus.spring.hw19.model.Book;
import ru.otus.spring.hw19.model.Comment;
import ru.otus.spring.hw19.model.Genre;

import java.util.Date;
import java.util.List;

@ChangeLog
@Slf4j
public class DatabaseChangelog {

    @ChangeSet(order = "001", id = "dropDb", author = "djet", runAlways = true)
    public void dropDb(MongoDatabase db) {
        db.drop();
    }

    @ChangeSet(order = "002", id = "dataLoad", author = "djet")
    public void dataLoad2(MongockTemplate mongockTemplate){
        Book bookOne = new Book();
        bookOne.setTitle("Treasure Island");
        bookOne.setGenre(new Genre(null, "Action and Adventure"));
        bookOne.setAuthors(List.of(
                new Author(null, "Robert Lewis", "Carroll")
                ));

        Book savedBookOne = mongockTemplate.save(bookOne);
        List.of(
                new Comment(null, "A splendid book!", new Date(), savedBookOne),
                new Comment(null, "Excellent book!", new Date(), savedBookOne)
        ).forEach(mongockTemplate::save);


        Book bookTwo = new Book();
        bookTwo.setTitle("The Land of Crimson Clouds");
        bookTwo.setGenre(new Genre(null, "Novella"));
        bookTwo.setAuthors(List.of(
                new Author(null, "Arkady", "Strugatsky"),
                new Author(null, "Boris", "Strugatsky")
        ));

        mongockTemplate.save(bookTwo);
    }
}
