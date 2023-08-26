package ru.otus.spring.hw19.api.v1;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.spring.hw19.config.MongoConfigTest;
import ru.otus.spring.hw19.dto.CommentDto;
import ru.otus.spring.hw19.dto.CommentsBookDto;
import ru.otus.spring.hw19.services.BookService;
import ru.otus.spring.hw19.services.CommentsService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@TestPropertySource(locations = "classpath:application-test.yml")
@WebMvcTest(RestCommentController.class)
@Import(MongoConfigTest.class)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RestCommentControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    CommentsService commentsService;

    @MockBean
    BookService bookService;

    @Test
    public void testGetCommentsByBook() throws Exception {
        String bookId = "1";
        List<CommentDto> commentDtoList = new ArrayList<>();
        commentDtoList.add(new CommentDto("1", "Comment 1", new Date(), bookId));
        commentDtoList.add(new CommentDto("2", "Comment 2", new Date(), bookId));
        CommentsBookDto commentsBookDto = new CommentsBookDto(commentDtoList);
        when(commentsService.findCommentsByBookId(bookId)).thenReturn(commentsBookDto);

        mockMvc.perform(get("/api/v1/books/{id}/comments", bookId))
                .andExpect(status().isOk())
                .andExpect(result -> content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(result -> content().json(
                        "{\"commentDtoList\":[{" +
                                "\"id\":\"1\"," +
                                "\"text\":\"Comment 1\"," +
                                "\"createDate\":\"" + commentsBookDto.getCommentDtoList().get(0).getCreateData() + "\", " +
                                "\"bookId\": \"1\"}," +
                                "{\"id\":\"2\"," +
                                "\"text\":\"Comment 2\"," +
                                "\"createDate\":\"" + commentsBookDto.getCommentDtoList().get(1).getCreateData() + "\", " +
                                "\"bookId\": \"1\"}]}"));
    }

    @Test
    public void testAddComment() throws Exception {
        String bookId = "3";
        CommentDto commentDto = new CommentDto("3", "Comment 3", new Date(), bookId);
        doNothing().when(commentsService).save(commentDto);

        mockMvc.perform(post("/api/v1/books/{id}/comments", bookId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":\"1\"," +
                                "\"text\":\"Comment 3\"," +
                                "\"createDate\":\"" + commentDto.getCreateData() + "\"," +
                                "\"bookId\": \"3\"}"))
                .andExpect(status().isOk());

        verify(commentsService, times(1)).save(Mockito.notNull());
        ArgumentCaptor<CommentDto> argumentCaptor = ArgumentCaptor.forClass(CommentDto.class);
        verify(commentsService, times(1)).save(argumentCaptor.capture());
    }
}
