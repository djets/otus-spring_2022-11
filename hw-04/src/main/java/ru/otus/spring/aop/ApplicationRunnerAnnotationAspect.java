package ru.otus.spring.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ru.otus.spring.controllers.IOService;

@Component
@Aspect
public class ApplicationRunnerAnnotationAspect {
    private final IOService ioService;

    public ApplicationRunnerAnnotationAspect(@Qualifier("ioServiceConsole") IOService ioService) {
        this.ioService = ioService;
    }

    @Before("@annotation(ru.otus.spring.aop.Additional)")
    public void addingTextAdvice(){
        ioService.outString("\u001B[31m" + "\nline added AOP\n------------------------------------------------------" + "\u001B[0m");
    }
}
