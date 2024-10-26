package dev.vanderloureiro.person_api.config;

import dev.vanderloureiro.person_api.person.exception.PersonNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ApplicationExceptionHandler {

    @ExceptionHandler(PersonNotFoundException.class)
    public ProblemDetail handlePersonNotFoundException(PersonNotFoundException e) {
        var response = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
        response.setTitle("Person Not Found");
        return response;
    }
}
