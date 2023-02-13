package ru.otus.spring.hw11.dao;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Repository;
import ru.otus.spring.hw11.model.Genre;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class GenreRepositoryImpl implements GenreRepository {
    @PersistenceContext
    EntityManager em;

    @Override
    public Genre save(Genre genre) {
        if (genre.getId() == 0) {
            em.persist(genre);
            return genre;
        } else {
            return em.merge(genre);
        }
    }

    @Override
    public Optional<Genre> findById(Long id) {
        return Optional.ofNullable(em.find(Genre.class, id));
    }

    @Override
    public List<Genre> findAll() {
        TypedQuery<Genre> query = em.createQuery(
                "SELECT g FROM Genre g",
                Genre.class
        );
        return query.getResultList();
    }

    @Override
    public Optional<Genre> findByName(String name) {
        TypedQuery<Genre> query = em.createQuery(
                "SELECT g FROM Genre g WHERE g.name = :name",
                Genre.class
        );
        query.setParameter("name", name);
        List<Genre> resultList = query.getResultList();
        return resultList.isEmpty() ? Optional.empty() : Optional.of(resultList.get(0));
    }

    @Override
    public void updateNameById(Long id, String updatedName) {
        Query query = em.createQuery(
                "UPDATE Genre SET name = :name WHERE id = :id");
        query.setParameter("name", updatedName);
        query.setParameter("id", id);
        query.executeUpdate();
    }

    @Override
    public void update(Genre genre) {
        em.merge(genre);
    }

    @Override
    public void deleteById(Long id) {
        Query query = em.createQuery(
                "DELETE FROM Genre g WHERE g.id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
    }

    @Override
    public void delete(Genre genre) {
        em.remove(em.contains(genre) ? genre : em.merge(genre));
    }
}
