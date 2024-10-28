package dev.vanderloureiro.person_api.config;

import dev.vanderloureiro.person_api.person.exception.BadFormatException;
import dev.vanderloureiro.person_api.person.exception.IdAlreadyExistsException;
import dev.vanderloureiro.person_api.person.exception.PersonNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ApplicationExceptionHandler {

    @ExceptionHandler(PersonNotFoundException.class)
    public ProblemDetail handlePersonNotFoundException(RuntimeException e) {
        var response = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
        response.setTitle("Recurso não encontrado");
        return response;
    }

    @ExceptionHandler(IdAlreadyExistsException.class)
    public ProblemDetail handleIdAlreadyExistsException(RuntimeException e) {
        var response = ProblemDetail.forStatus(HttpStatus.CONFLICT);
        response.setTitle("ID já existente");
        return response;
    }

    @ExceptionHandler(BadFormatException.class)
    public ProblemDetail handleBadRequestException(RuntimeException e) {
        var response = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        response.setTitle("Requisição mal formatada");
        return response;
    }
}
