package ru.otus.spring.hw22.api.v2;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.otus.spring.hw22.exception.ValidateException;

@RestControllerAdvice(basePackageClasses = AppExceptionHandler.class)
public class AppExceptionHandler {

    @ResponseBody
    @ExceptionHandler(value = ValidateException.class)
    public ResponseEntity<?> handleException(ValidateException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMsg());
    }
}
