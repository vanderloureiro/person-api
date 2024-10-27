package dev.vanderloureiro.person_api.person.domain;

import dev.vanderloureiro.person_api.person.exception.BadFormatException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class Salary {

    public static BigDecimal of(Person person, String format) {
        if (!List.of("min", "full").contains(format.toLowerCase())) {
            throw new BadFormatException();
        }

        long yearsDifference = ChronoUnit.YEARS.between(person.getAdmissionDate(), LocalDate.now());
        var currentSalary = BigDecimal.valueOf(1558.00).setScale(2, RoundingMode.HALF_UP);
        for (int i = 0; i < yearsDifference; i++) {
            var additional = currentSalary.multiply(BigDecimal.valueOf(18))
                    .divide(BigDecimal.valueOf(100), RoundingMode.HALF_UP)
                    .add(BigDecimal.valueOf(500));
            currentSalary = currentSalary.add(additional);
        }
        if (format.equalsIgnoreCase("full")) {
            return currentSalary;
        }

        var currentMinSalary = BigDecimal.valueOf(1302.00).setScale(2, RoundingMode.HALF_UP);
        return currentSalary.divide(currentMinSalary, RoundingMode.HALF_UP);
    }

}
