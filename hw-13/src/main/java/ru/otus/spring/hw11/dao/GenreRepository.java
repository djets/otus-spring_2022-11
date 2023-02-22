package ru.otus.spring.hw11.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.otus.spring.hw11.model.Genre;

import java.util.List;

@Repository
public interface GenreRepository extends JpaRepository<Genre, Long>{

    List<Genre> findByName(String genreName);
}
