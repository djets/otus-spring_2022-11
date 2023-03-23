package ru.otus.spring.hw18.mongock.changelog;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.github.cloudyrock.mongock.driver.mongodb.springdata.v3.decorator.impl.MongockTemplate;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.InsertOneResult;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import ru.otus.spring.hw18.model.Author;
import ru.otus.spring.hw18.model.Book;
import ru.otus.spring.hw18.model.Genre;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@ChangeLog
@Slf4j
public class DatabaseChangelog {

    @ChangeSet(order = "001", id = "dropDb", author = "adm", runAlways = true)
    public void dropDb(MongoDatabase db) {
        db.drop();
    }

    @ChangeSet(order = "002", id = "dataLoad", author = "adm")
    public void dataLoad(MongoDatabase db) {
        MongoCollection<Document> genresCollection = db.getCollection("genres");
        List<Document> genreDocumentList = new ArrayList<>();
        genreDocumentList.add(new Document().append("name", "Classic"));
        genreDocumentList.add(new Document().append("name", "Historical genre"));
        genresCollection.insertMany(genreDocumentList);

        MongoCollection<Document> authorCollection = db.getCollection("authors");
        List<Document> authorDocumentList = new ArrayList<>();
        authorDocumentList.add(new Document().append("name", "Robinson").append("surname", "Test"));
        authorDocumentList.add(new Document().append("name", "Alise").append("surname", "Test"));
        authorCollection.insertMany(authorDocumentList);

        MongoCollection<Document> bookCollection = db.getCollection("books");
        List<Document> bookDocumentList = new ArrayList<>();
        bookDocumentList.add(new Document().append("name", "TestBook"));
        bookDocumentList.add(new Document().append("name", "TestBook"));
        bookCollection.insertMany(bookDocumentList);
    }

    @ChangeSet(order = "003", id = "dataLoad2", author = "adm")
    public void dataLoad2(MongockTemplate mongockTemplate){
        Book book = new Book();
        book.setName("TestBookName");
        book.setGenre(new Genre(null, "TestGenreName", new ArrayList<>()));
        book.setAuthors(List.of(new Author(null, "AuthorName", "Surname", new ArrayList<>())));
        book.setComments(new ArrayList<>());

        mongockTemplate.save(book);
    }
}
