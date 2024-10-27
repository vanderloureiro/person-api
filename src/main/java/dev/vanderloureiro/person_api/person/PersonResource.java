package dev.vanderloureiro.person_api.person;

import dev.vanderloureiro.person_api.person.domain.Person;
import dev.vanderloureiro.person_api.person.exception.IdAlreadyExistsException;
import dev.vanderloureiro.person_api.person.exception.PersonNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/person")
public class PersonResource {

    private final PersonRepository repository;

    public PersonResource(PersonRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public ResponseEntity<List<Person>> get() {
        List<Person> list = this.repository
                .findAll().stream()
                .sorted(Comparator.comparing(Person::getName))
                .toList();
        return ResponseEntity.ok(list);
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody Person person) {
        if (Objects.nonNull(person.getId()) && repository.exists(person.getId())) {
            throw new IdAlreadyExistsException();
        }
        Person saved = repository.save(person);
        return ResponseEntity.created(URI.create("/person/" + saved.getId())).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Person> getById(@PathVariable Long id) {
        if (!repository.exists(id)) {
            throw new PersonNotFoundException();
        }
        return ResponseEntity.ok(repository.findById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!repository.exists(id)) {
            throw new PersonNotFoundException();
        }
        repository.delete(id);
        return ResponseEntity.noContent().build();
    }
}
