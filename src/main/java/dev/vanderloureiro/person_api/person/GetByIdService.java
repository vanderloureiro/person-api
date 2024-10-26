package dev.vanderloureiro.person_api.person;

import org.springframework.stereotype.Service;

@Service
public class GetByIdService {

    private final PersonRepository repository;

    public GetByIdService(PersonRepository repository) {
        this.repository = repository;
    }

    public Person execute(Long id) {
        return repository.findById(id);
    }
}
