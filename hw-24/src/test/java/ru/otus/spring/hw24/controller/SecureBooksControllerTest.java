package ru.otus.spring.hw24.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.spring.hw24.config.MongoConfigTest;
import ru.otus.spring.hw24.services.BookService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@TestPropertySource(locations = "classpath:application-test.yml")
@WebMvcTest(SecureBooksControllerTest.class)
@Import(MongoConfigTest.class)
class SecureBooksControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    BookService bookService;

    @WithMockUser(
            username = "admin",
            authorities = {"ROLE_USER"}
    )

    @Test
    void showAll() {
    }

    @Test
    void testAuthenticatedOnAdminAddBookPage() throws Exception{
        mockMvc.perform(get("/books/create"))
                .andExpect(status().isOk());
    }

    @Test
    void editBookPage() {
    }

    @Test
    void saveBooks() {
    }
}