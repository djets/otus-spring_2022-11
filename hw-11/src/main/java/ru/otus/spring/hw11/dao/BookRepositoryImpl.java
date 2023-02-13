package ru.otus.spring.hw11.dao;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.otus.spring.hw11.model.Book;

import javax.persistence.*;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class BookRepositoryImpl implements BookRepository {

    @PersistenceContext
    EntityManager em;

    @Override
    public Book save(Book book) {
        if (book.getId() == 0) {
            em.persist(book);
            return book;
        } else {
            return em.merge(book);
        }
    }

    @Override
    public Optional<Book> findById(Long id) {
        EntityGraph<?> graph = em.getEntityGraph("book-only-entity-graph");
        Map<String, Object> properties = Map.of("javax.persistence.fetchgraph", graph);
        Book book = em.find(Book.class, id, properties);
        return Optional.ofNullable(book);
    }

    @Override
    public Optional<Book> findByIdWithGenreAndAuthors(Long id) {
        EntityGraph<?> graph = em.getEntityGraph("book-genre-authors-entity-graph");
        Map<String, Object> properties = Map.of("javax.persistence.fetchgraph", graph);
        Book book = em.find(Book.class, id, properties);
        return Optional.ofNullable(book);
    }

    @Override
    public List<Book> findAll() {
        TypedQuery<Book> query = em.createQuery(
                "SELECT b FROM Book b",
                Book.class
        );
        return query.getResultList();
    }

    @Override
    //TODO добавить жанр и авторов
    public Optional<Book> findByName(String name) {
        TypedQuery<Book> query = em.createQuery(
                "SELECT b FROM Book b WHERE b.name = :name",
                Book.class
        );
        query.setParameter("name", name);
        List<Book> resultList = query.getResultList();
        return resultList.isEmpty() ? Optional.empty() : Optional.of(resultList.get(0));
    }

    @Override
    public void updateNameById(Long id, String updatedName) {
        Query query = em.createQuery(
                "UPDATE Book SET name = :name WHERE id = :id");
        query.setParameter("name", updatedName);
        query.setParameter("id", id);
        query.executeUpdate();
    }

    @Override
    public void update(Book book) {
        em.merge(book);
    }

    @Override
    public void deleteById(Long id) {
        Query query = em.createQuery(
                "DELETE FROM Book b WHERE b.id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
    }

    @Override
    public void delete(Book book) {
        em.remove(em.contains(book) ? book : em.merge(book));
    }
}
