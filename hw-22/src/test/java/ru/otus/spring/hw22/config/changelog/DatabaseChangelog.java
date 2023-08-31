package ru.otus.spring.hw22.config.changelog;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoDatabase;
import lombok.extern.slf4j.Slf4j;

@ChangeLog
@Slf4j
public class DatabaseChangelog {

    @ChangeSet(order = "001", id = "dropDb", author = "djet", runAlways = true)
    public void dropDb(MongoDatabase db) {
        db.drop();
    }
}
