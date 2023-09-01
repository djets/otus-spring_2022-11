package ru.otus.spring.hw24.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import ru.otus.spring.hw24.model.secure.User;

public interface UserRepository extends MongoRepository<User, String> {
    @Query("{username: '?0'}")
    User findUserByUsername(String username);
}
