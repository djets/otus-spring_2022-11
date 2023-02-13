package ru.otus.spring.hw11.services;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.otus.spring.hw11.dao.GenreRepository;
import ru.otus.spring.hw11.model.Genre;

import java.util.List;
import java.util.Optional;

@Service
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class GenreServiceShellImpl implements GenreServiceShell {
    Logger logger = LoggerFactory.getLogger(GenreServiceShellImpl.class);
    GenreRepository repositories;

    @Override
    public Long save(String name) {
        Long foundId = findByName(name);
        if (foundId == 0) {
            Genre genre = new Genre();
            genre.setName(name);
            Genre receivedGenre = repositories.save(genre);
            logger.info("Genre {} - save. id: {}", genre.getName(), receivedGenre.getId());
        }
        logger.info("Genre {} - is already in base. id: {}", name, foundId);
        return foundId;

    }

    @Override
    public Genre findById(Long id) {
        return repositories.findById(id).orElseThrow(RuntimeException::new);
    }

    @Override
    public Long findByName(String name) {
        Optional<Genre> optionalGenre = repositories.findByName(name);
        return optionalGenre.orElseThrow(RuntimeException::new).getId();
    }

    @Override
    public void updateNameById(Long id, String changedName) {
        repositories.updateNameById(id, changedName);
    }

    @Override
    public void delete(Long id) {
        repositories.deleteById(id);
    }

    @Override
    public List<Genre> getAll() {
        return repositories.findAll();
    }
}
