package dev.vanderloureiro.person_api.person;

import dev.vanderloureiro.person_api.person.domain.Person;
import dev.vanderloureiro.person_api.person.exception.BadRequestException;
import dev.vanderloureiro.person_api.person.exception.IdAlreadyExistsException;
import dev.vanderloureiro.person_api.person.exception.PersonNotFoundException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
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

    @PutMapping("/{id}")
    public ResponseEntity<Person> put(@PathVariable Long id, @RequestBody Person person) {
        if (!repository.exists(id)) {
            throw new PersonNotFoundException();
        }
        if (Objects.isNull(person.getId())) {
            person.setId(id);
        }
        return ResponseEntity.ok(repository.save(person));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Person> update(@PathVariable Long id, @RequestBody Person request) {
        if (!repository.exists(id)) {
            throw new PersonNotFoundException();
        }
        Person updatable = repository.findById(id);
        if (Objects.nonNull(request.getName()) && StringUtils.isNotEmpty(request.getName())) {
            updatable.setName(request.getName());
        }
        if (Objects.nonNull(request.getBirthDate())) {
            updatable.setBirthDate(request.getBirthDate());
        }
        if (Objects.nonNull(request.getAdmissionDate())) {
            updatable.setAdmissionDate(request.getAdmissionDate());
        }
        repository.save(updatable);
        return ResponseEntity.noContent().build();
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

    @GetMapping("/{id}/age")
    public ResponseEntity<Long> age(
            @PathVariable Long id,
            @RequestParam String output) {
        if (!repository.exists(id)) {
            throw new PersonNotFoundException();
        }
        if (!List.of("days", "months", "years").contains(output.toLowerCase())) {
            throw new BadRequestException();
        }
        Person person = repository.findById(id);
        if (output.equalsIgnoreCase("days")) {
            long difference = ChronoUnit.DAYS.between(person.getBirthDate(), LocalDate.now());
            return ResponseEntity.ok(difference);
        }
        if (output.equalsIgnoreCase("months")) {
            long difference = ChronoUnit.MONTHS.between(person.getBirthDate(), LocalDate.now());
            return ResponseEntity.ok(difference);
        }
        long difference = ChronoUnit.YEARS.between(person.getBirthDate(), LocalDate.now());
        return ResponseEntity.ok(difference);
    }
}
