package ru.otus.spring.hw09;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.otus.spring.hw09.dao.BookJdbcModelDao;

@SpringBootApplication
public class Hw09Application {

    public static void main(String[] args) {
        var ctx = SpringApplication.run(Hw09Application.class, args);
        var bjmd = ctx.getBean(BookJdbcModelDao.class);
        System.out.println(bjmd.findById(1L).toString());
    }

}
