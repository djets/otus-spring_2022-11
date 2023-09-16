package ru.otus.spring.hw24.mongock.changelog;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.github.cloudyrock.mongock.driver.mongodb.springdata.v3.decorator.impl.MongockTemplate;
import com.mongodb.client.MongoDatabase;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import ru.otus.spring.hw24.model.Author;
import ru.otus.spring.hw24.model.Book;
import ru.otus.spring.hw24.model.Comment;
import ru.otus.spring.hw24.model.Genre;
import ru.otus.spring.hw24.model.secure.Role;
import ru.otus.spring.hw24.model.secure.User;
import ru.otus.spring.hw24.model.secure.UserRole;

import java.util.*;

@ChangeLog
@Slf4j
public class DatabaseChangelog {

    @ChangeSet(order = "001", id = "dropDb", author = "djet", runAlways = true)
    public void dropDb(MongoDatabase db) {
        db.drop();
    }

    @ChangeSet(order = "002", id = "dataLoad", author = "djet")
    public void dataLoad2(MongockTemplate mongockTemplate) {
        Book bookOne = new Book();
        bookOne.setTitle("Treasure Island");
        bookOne.setGenre(new Genre(null, "Action and Adventure", new ArrayList<>()));
        bookOne.setAuthors(List.of(
                new Author(null, "Robert Lewis", "Carroll", new ArrayList<>())
        ));
        bookOne.setComments(List.of(
                new Comment(null, "A splendid book!", new Date(), null),
                new Comment(null, "Excellent book!", new Date(), null)
        ));

        mongockTemplate.save(bookOne);

        Book bookTwo = new Book();
        bookTwo.setTitle("The Land of Crimson Clouds");
        bookTwo.setGenre(new Genre(null, "Novella", new ArrayList<>()));
        bookTwo.setAuthors(List.of(
                new Author(null, "Arkady", "Strugatsky", new ArrayList<>()),
                new Author(null, "Boris", "Strugatsky", new ArrayList<>())
        ));

        mongockTemplate.save(bookTwo);
    }

    @ChangeSet(order = "003", id= "userLoad", author = "djet")
    public void userLoad(MongockTemplate mongockTemplate) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        final String USER_NAME = "user";
        final String ADMIN_NAME = "admin";
        final String PASSWORD = "pass";

        Role roleUser = new Role();
        roleUser.setName("ROLE_USER");
        mongockTemplate.save(roleUser);

        User user = new User();
        user.setUsername(USER_NAME);
        user.setPassword(bCryptPasswordEncoder.encode(PASSWORD));

        UserRole userRole = new UserRole();
        userRole.setRole(roleUser);
        user.setUserRoles(new HashSet<>(Collections.singletonList(userRole)));
        mongockTemplate.save(user);

        Role roleAdmin = new Role();
        roleAdmin.setName("ROLE_ADMIN");
        mongockTemplate.save(roleAdmin);

        User admin = new User();
        admin.setUsername(ADMIN_NAME);
        admin.setPassword(bCryptPasswordEncoder.encode(PASSWORD));

        UserRole adminRole = new UserRole();
        adminRole.setRole(roleAdmin);
        admin.setUserRoles(new HashSet<>(Collections.singletonList(adminRole)));
        mongockTemplate.save(admin);
    }
}
