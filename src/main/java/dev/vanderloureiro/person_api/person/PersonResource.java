package dev.vanderloureiro.person_api.person;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/person")
public class PersonResource {

    private final GetByIdService getByIdService;

    public PersonResource(GetByIdService getByIdService) {
        this.getByIdService = getByIdService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Person> getById(@PathVariable Long id) {
        return ResponseEntity.ok(getByIdService.execute(id));
    }
}
