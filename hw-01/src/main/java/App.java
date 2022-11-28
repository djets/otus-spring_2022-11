import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import service.AnswerService;
import service.QuestionService;

public class App {
    static Logger log = LoggerFactory.getLogger(App.class);
    public static void main(String[] args) {

        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("/spring-context.xml");
        QuestionService questionService = context.getBean(QuestionService.class);
        AnswerService answerService  = context.getBean(AnswerService.class);
//        questionService.getListObject().forEach(question -> System.out.println(question.getText()));
        answerService.getListObject().forEach(answer -> {
            questionService.getListObject().forEach(question -> {
                if (question == answer.getQuestion()) {
                    answer.setQuestion(question);
                }
            });
            System.out.println(answer.getQuestion().getText() + ": " + answer.getText() + ": " + answer.isRightAnswer());

        });
    }


}
