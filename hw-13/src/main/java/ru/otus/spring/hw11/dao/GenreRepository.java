package ru.otus.spring.hw11.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.spring.hw11.model.Genre;

import java.util.List;

public interface GenreRepository extends JpaRepository<Genre, Long>{
    List<Genre> findByName(String genreName);
}
