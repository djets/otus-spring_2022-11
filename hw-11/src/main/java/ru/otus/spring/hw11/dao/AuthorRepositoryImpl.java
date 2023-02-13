package ru.otus.spring.hw11.dao;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Repository;
import ru.otus.spring.hw11.model.Author;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;


@Repository
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class AuthorRepositoryImpl implements AuthorRepository {
    @PersistenceContext
    EntityManager em;

    @Override
    public Author save(Author author) {
        if (author.getId() == 0) {
            em.persist(author);
            return author;
        } else {
            return em.merge(author);
        }
    }

    @Override
    public Optional<Author> findById(Long id) {
        return Optional.ofNullable(em.find(Author.class, id));
    }

    @Override
    public List<Author> findAll() {
        TypedQuery<Author> query = em.createQuery(
                "SELECT a FROM Author a",
                Author.class
        );
        return query.getResultList();
    }

    @Override
    public Optional<Author> findByNameAndSurname(String name, String surname) {
        TypedQuery<Author> query = em.createQuery(
                "SELECT a FROM Author a WHERE a.name = :name AND a.surname = :surname",
                Author.class
        );
        query.setParameter("name", name);
        query.setParameter("surname", surname);
        List<Author> resultList = query.getResultList();
        return resultList.isEmpty() ? Optional.empty() : Optional.of(resultList.get(0));
    }

    @Override
    public void updateNameById(Long id, String updatedName) {
        Query query = em.createQuery(
                "UPDATE Author SET name = :name WHERE id = :id");
        query.setParameter("name", updatedName);
        query.setParameter("id", id);
        query.executeUpdate();
    }

    @Override
    public void updateSurnameById(Long id, String updatedSurname) {
        Query query = em.createQuery(
                "UPDATE Author SET surname = :surname WHERE id = :id");
        query.setParameter("surname", updatedSurname);
        query.setParameter("id", id);
        query.executeUpdate();
    }

    @Override
    public void update(Author author) {
        em.merge(author);
    }

    @Override
    public void deleteById(Long id) {
        Query query = em.createQuery(
                "DELETE FROM Author a WHERE a.id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
    }

    @Override
    public void delete(Author author) {
        em.remove(em.contains(author) ? author : em.merge(author));
    }
}
