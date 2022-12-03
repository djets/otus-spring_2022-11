package ru.otus.spring;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.AbstractApplicationContext;
import ru.otus.spring.services.ApplicationRunner;

@ComponentScan
@Configuration
public class Main {
    public static void main(String[] args) {
//        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("/spring-context.xml");
//        context.getBean(QuestionsDaoCSV.class);
//        context.getBean(IOServiceConsole.class);
//        var questionService = context.getBean(QuestionServiceImpl.class);
//        questionService.printAllQuestions();
//        context.close();
//        IOServiceConsole ioServiceConsole = new IOServiceConsole(System.out, System.in);
//        CsvReadProcessor csvReadProcessor = new CsvReadProcessorImpl();
//        QuestionRegistry questionRegistry = new QuestionRegistryImpl(csvReadProcessor);
//        QuestionsServiceImpl questionService = new QuestionsServiceImpl(questionRegistry);
//        QuestionsProcessorImpl questionsProcessor = new QuestionsProcessorImpl(questionService, ioServiceConsole);
//        UserRegistryImpl userRegistry = new UserRegistryImpl();
//        UserServiceImpl userService = new UserServiceImpl(userRegistry, ioServiceConsole);
//        ApplicationRunner applicationRunner = new ApplicationRunner(ioServiceConsole, userService, questionsProcessor);
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Main.class);
        ApplicationRunner runner = context.getBean(ApplicationRunner.class);
        runner.run();
    }
}
