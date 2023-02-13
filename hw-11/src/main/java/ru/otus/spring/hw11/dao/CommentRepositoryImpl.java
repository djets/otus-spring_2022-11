package ru.otus.spring.hw11.dao;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Repository;
import ru.otus.spring.hw11.model.Comment;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class CommentRepositoryImpl implements CommentRepository {
    @PersistenceContext
    EntityManager em;

    @Override
    public Comment save(Comment comment) {
        if (comment.getId() == 0) {
            em.persist(comment);
            return comment;
        } else {
            return em.merge(comment);
        }
    }

    @Override
    public Optional<Comment> findById(Long id) {
        return Optional.ofNullable(em.find(Comment.class, id));
    }

    @Override
    public List<Comment> findAllByBookId(Long bookId) {
        TypedQuery<Comment> comments = em.createQuery(
                "SELECT c FROM Comment c WHERE c.book.id = :bookId",
                Comment.class
        );
        comments.setParameter("bookId", bookId);
        return comments.getResultList();
    }

    @Override
    public void update(Comment comment) {
        em.merge(comment);
    }

    @Override
    public void deleteById(Long id) {
        Query query = em.createQuery(
                "DELETE FROM Comment c WHERE c.id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
    }

    @Override
    public void delete(Comment comment) {
        em.remove(em.contains(comment) ? comment : em.merge(comment));
    }
}
