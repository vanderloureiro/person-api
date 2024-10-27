package dev.vanderloureiro.person_api.person;

import dev.vanderloureiro.person_api.person.domain.Person;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

/*
* Repository com visibildiade package para ser acessado apenas dentro do modulo de person,
* Demais operações na base devem ser pelos serviços
* */
@Repository
class PersonRepository {

    private final HashMap<Long, Person> database = new HashMap<Long, Person>();
    private Long highestId;

    /*
    * Construtor privado para deixar a inicialização sob responsabilidade da pŕopria classe e do Spring
    * */
    private PersonRepository() {
        this.fillBase();
        this.highestId = 3L;
    }

    private void fillBase() {
        database.put(1L, new Person(1L, "Vanderlei", LocalDate.of(1997, 3, 29),
                LocalDate.of(2024, 10, 1)));
        database.put(2L, new Person(2L, "Wilker", LocalDate.of(1990, 5, 20),
                LocalDate.of(2021, 1, 15)));
        database.put(3L, new Person(3L, "Lucas", LocalDate.of(1999, 10, 10),
                LocalDate.of(2022, 8, 12)));
    }

    public List<Person> findAll() {
        return this.database.values().stream().toList();
    }

    // uso de fail first
    public Person findById(Long id) {
        return database.get(id);
    }

    public boolean exists(Long id) {
        return this.database.containsKey(id);
    }

    /*
    * O método Save tem o comportamento parecido com o Save do JPA, onde salva e atualiza no mesmo método
    * */
    public Person save(Person person) {
        if (Objects.nonNull(person.getId()) && database.containsKey(person.getId())) {
            this.database.replace(person.getId(), person);
        }
        if (Objects.isNull(person.getId())) {
            highestId++;
            person.setId(highestId);
        }
        if (Objects.nonNull(person.getId()) && person.getId() > highestId) {
            highestId = person.getId();
        }
        this.database.put(person.getId(), person);
        return person;
    }

    public void delete(Long id) {
        this.database.remove(id);
    }
}
