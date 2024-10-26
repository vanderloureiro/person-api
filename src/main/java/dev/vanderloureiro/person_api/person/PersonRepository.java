package dev.vanderloureiro.person_api.person;

import dev.vanderloureiro.person_api.person.exception.PersonNotFoundException;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.HashMap;

/*
* Repository com visibildiade package para ser acessado apenas dentro do modulo de person,
* Demais operações na base devem ser pelos serviços
* */
@Repository
class PersonRepository {

    private final HashMap<Long, Person> database = new HashMap<Long, Person>();

    /*
    * Construtor privado para deixar a inicialização sob responsabilidade da pŕopria classe e do Spring
    * */
    private PersonRepository() {
        this.fillBase();
    }

    private void fillBase() {
        database.put(1L, new Person(1L, "Vanderlei", LocalDate.of(1997, 3, 29),
                LocalDate.of(2024, 10, 1)));
        database.put(2L, new Person(2L, "Wilker", LocalDate.of(1990, 5, 20),
                LocalDate.of(2021, 1, 15)));
        database.put(3L, new Person(3L, "Lucas", LocalDate.of(1999, 10, 10),
                LocalDate.of(2022, 8, 12)));
    }

    // uso de fail first
    public Person findById(Long id) {
        if (!database.containsKey(id)) {
            throw new PersonNotFoundException();
        }
        return database.get(id);
    }
}
