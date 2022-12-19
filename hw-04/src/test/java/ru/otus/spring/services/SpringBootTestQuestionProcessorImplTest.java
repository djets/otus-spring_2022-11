package ru.otus.spring.services;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import ru.otus.spring.config.IOServiceTestConfiguration;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource("classpath:application-test.yml")
@Import(value = IOServiceTestConfiguration.class)
class SpringBootTestQuestionProcessorImplTest {
    Logger logger = LoggerFactory.getLogger(SpringBootTestQuestionProcessorImplTest.class);
    @Autowired
    private QuestionProcessor questionProcessor;

    @Test
    void whenTestContextLoadedAsksQuestionAnswerShouldBeCorrectTest() {
        questionProcessor.askQuestions(1L);
        logger.info("ResultMap size: {}", questionProcessor.getResultMapByUserId(1L).size());
        assertThat(questionProcessor.getResultMapByUserId(1L).size()).isEqualTo(1);
        questionProcessor.getResultMapByUserId(1L)
                .forEach((q, b) -> {
                    logger.info("Question id:{}; " + "\u001b[32m" + "Answer should be correct: {}", q, b);
                    assertThat(b).isEqualTo(true);
                });
    }
}

