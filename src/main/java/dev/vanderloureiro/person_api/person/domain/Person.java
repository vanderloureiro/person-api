package dev.vanderloureiro.person_api.person.domain;

import java.time.LocalDate;
import java.util.Objects;

/*
* Pela descrição falar apenas de Java e Spring. Ignorei o uso de Lombok ou demais dependências
* */
public class Person {

    private Long id;
    private String name;
    private LocalDate birthDate;
    private LocalDate admissionDate;

    public Person(Long id, String name, LocalDate birthDate, LocalDate admissionDate) {
        this.id = id;
        this.name = name;
        this.birthDate = birthDate;
        this.admissionDate = admissionDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public LocalDate getAdmissionDate() {
        return admissionDate;
    }

    public void setAdmissionDate(LocalDate admissionDate) {
        this.admissionDate = admissionDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Objects.equals(id, person.id)
                && Objects.equals(name, person.name)
                && Objects.equals(birthDate, person.birthDate)
                && Objects.equals(admissionDate, person.admissionDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, birthDate, admissionDate);
    }
}
