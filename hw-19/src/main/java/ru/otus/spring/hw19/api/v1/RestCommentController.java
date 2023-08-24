package ru.otus.spring.hw19.api.v1;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.otus.spring.hw19.dto.CommentDto;
import ru.otus.spring.hw19.dto.CommentsBookDto;
import ru.otus.spring.hw19.services.BookService;
import ru.otus.spring.hw19.services.CommentsService;

import java.util.Date;

@RestController
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequestMapping("/api/v1/books")
public class RestCommentController {
    CommentsService commentsService;
    BookService bookService;

    @GetMapping("/{id}/comments")
    public CommentsBookDto getCommentsByBook(@PathVariable String id) {
        return commentsService.findCommentsByBookId(id);
    }

    @PostMapping("/{id}/comments")
    public void addComment(@RequestBody CommentDto commentDto) {
        commentDto.setCreateData(new Date());
        commentsService.save(commentDto);
    }
}
