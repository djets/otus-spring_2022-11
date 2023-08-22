package ru.otus.spring.hw19.config.changelog;

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
}
