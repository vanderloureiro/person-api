package dev.vanderloureiro.person_api;

import dev.vanderloureiro.person_api.person.domain.Person;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PersonResourceIntegrationTest {

    @Autowired
    private TestRestTemplate testRestTemplate;


    @Test
    public void whenGetAll_returnsList() {
        ResponseEntity<List<Person>> response = testRestTemplate
                .exchange("/person", HttpMethod.GET, null, new ParameterizedTypeReference<List<Person>>() {});

        Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);
        Assertions.assertEquals(response.getBody().size(), 4);
    }

    @Test
    public void whenSearchValidId_returnsValid() {
        ResponseEntity<Person> response = testRestTemplate.getForEntity("/person/1", Person.class);

        Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void whenSearchInvalidId_returns404() {
        ResponseEntity<Person> response = testRestTemplate.getForEntity("/person/-5", Person.class);

        Assertions.assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
    }

    @Test
    public void create_successfully() {
        Person request = new Person(null, "João da Silva", LocalDate.of(1990, 10, 10),
                LocalDate.of(2021, 8, 8));
        ResponseEntity<Void> response = testRestTemplate.postForEntity("/person", request, Void.class);

        Assertions.assertEquals(response.getStatusCode(), HttpStatus.CREATED);
    }

    @Test
    public void whenSendAlreadyId_returnsBadRequest() {
        Person request = new Person(1L, "João da Silva", LocalDate.of(1990, 10, 10),
                LocalDate.of(2021, 8, 8));
        ResponseEntity<Void> response = testRestTemplate.postForEntity("/person", request, Void.class);

        Assertions.assertEquals(response.getStatusCode(), HttpStatus.CONFLICT);
    }

    @Test
    public void whenSendValidId_updateSuccessfully() {
        Person request = new Person(1L, "João da Silva", LocalDate.of(1990, 10, 10),
                LocalDate.of(2021, 8, 8));
        ResponseEntity<Void> response = testRestTemplate
                .exchange("/person/" + 1L, HttpMethod.PUT, new HttpEntity<>(request), Void.class);

        Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void whenSendInvalidId_returnsPutError() {
        Person request = new Person(500L, "João da Silva", LocalDate.of(1990, 10, 10),
                LocalDate.of(2021, 8, 8));
        ResponseEntity<Void> response = testRestTemplate
                .exchange("/person/" + 500L, HttpMethod.PUT, new HttpEntity<>(request), Void.class);

        Assertions.assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
    }

    @Test
    public void whenSendValidId_patchSuccessfully() {
        Person request = new Person(null, "João da Silva", LocalDate.of(1990, 10, 10),
                null);
        ResponseEntity<Person> response = testRestTemplate
                .exchange("/person/" + 2L, HttpMethod.PATCH, new HttpEntity<>(request), Person.class);

        Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);
        Assertions.assertEquals(response.getBody().getName(), "João da Silva");
        Assertions.assertEquals(response.getBody().getAdmissionDate(), LocalDate.of(2021, 1, 15));
    }

    @Test
    public void whenSendInvalidId_returnsDeleteNotFoundError() {
        ResponseEntity<Void> response = testRestTemplate
                .exchange("/person/" + 500L, HttpMethod.DELETE, null, Void.class);

        Assertions.assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
    }

    @Test
    public void whenSendInvalidId_returnsDeleteSuccess() {
        ResponseEntity<Void> response = testRestTemplate
                .exchange("/person/" + 3L, HttpMethod.DELETE, null, Void.class);

        Assertions.assertEquals(response.getStatusCode(), HttpStatus.NO_CONTENT);
    }

}
