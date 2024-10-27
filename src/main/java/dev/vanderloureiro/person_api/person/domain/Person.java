package dev.vanderloureiro.person_api.person.domain;

import dev.vanderloureiro.person_api.person.exception.BadFormatException;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
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

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public LocalDate getAdmissionDate() {
        return admissionDate;
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

    public void patch(Person newPerson) {
        if (Objects.nonNull(newPerson.getName()) && StringUtils.isNotEmpty(newPerson.getName())) {
            this.name = newPerson.name;
        }
        if (Objects.nonNull(newPerson.getBirthDate())) {
            this.birthDate = newPerson.birthDate;
        }
        if (Objects.nonNull(newPerson.getAdmissionDate())) {
            admissionDate = newPerson.admissionDate;
        }
    }

    public long getAge(String format) {
        if (!List.of("days", "months", "years").contains(format.toLowerCase())) {
            throw new BadFormatException();
        }
        if (format.equalsIgnoreCase("days")) {
            return ChronoUnit.DAYS.between(this.birthDate, LocalDate.now());
        }
        if (format.equalsIgnoreCase("months")) {
            return ChronoUnit.MONTHS.between(this.birthDate, LocalDate.now());
        }
        return ChronoUnit.YEARS.between(this.birthDate, LocalDate.now());
    }

}
