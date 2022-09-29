package data;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import miniorm.Column;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Optional;
import java.util.OptionalInt;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString // think StringBuilder if a lot of attributes
public class Person extends Mammal {

    @Column
    @NotNull
    private String firstname;

    @Column
    @NotNull
    private String lastname;


    private LocalDate birthdate;

    /**
     * age at least the 31st december of current year
     * @return age
     */
    public OptionalInt getAge() {
        var today = LocalDate.now();
        if (Objects.isNull(birthdate)) {
            return OptionalInt.empty();
        }
        return OptionalInt.of(today.getYear() - birthdate.getYear());
    }
}
