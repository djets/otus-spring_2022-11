package ru.otus.spring.hw18.mongock.changelog;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.github.cloudyrock.mongock.driver.mongodb.springdata.v3.decorator.impl.MongockTemplate;
import com.mongodb.client.MongoDatabase;
import lombok.extern.slf4j.Slf4j;
import ru.otus.spring.hw18.model.Author;
import ru.otus.spring.hw18.model.Book;
import ru.otus.spring.hw18.model.Comment;
import ru.otus.spring.hw18.model.Genre;

import java.util.ArrayList;
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
        bookOne.setGenre(new Genre(null, "Action and Adventure", new ArrayList<>()));
        bookOne.setAuthors(List.of(
                new Author(null, "Robert Lewis", "Carroll", new ArrayList<>())
                ));
        bookOne.setComments(List.of(
                new Comment(null, "A splendid book!", new Date(), null),
                new Comment(null, "Excellent book!", new Date(), null)
        ));

        mongockTemplate.save(bookOne);

        Book bookTwo = new Book();
        bookTwo.setTitle("The Land of Crimson Clouds");
        bookTwo.setGenre(new Genre(null, "Novella", new ArrayList<>()));
        bookTwo.setAuthors(List.of(
                new Author(null, "Arkady", "Strugatsky", new ArrayList<>()),
                new Author(null, "Boris", "Strugatsky", new ArrayList<>())
        ));

        mongockTemplate.save(bookTwo);
    }
}
