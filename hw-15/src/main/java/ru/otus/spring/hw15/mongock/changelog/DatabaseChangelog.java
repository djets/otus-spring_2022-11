package ru.otus.spring.hw15.mongock.changelog;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

@ChangeLog
public class DatabaseChangelog {

    @ChangeSet(order = "001", id = "dropDb", author = "djet", runAlways = true)
    public void dropDb(MongoDatabase db) {
        db.drop();
    }

    @ChangeSet(order = "002", id = "dataLoad", author = "dejt")
    public void dataLoad(MongoDatabase db) {
        MongoCollection<Document> myCollection = db.getCollection("genres");
        var doc = new Document().append("name", "GenreTest Load");
        myCollection.insertOne(doc);
    }
}
