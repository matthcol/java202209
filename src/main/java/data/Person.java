package data;

import lombok.*;

import java.time.LocalDate;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString // think StringBuilder if a lot of attributes
public class Person {

    private String firstname;
    private String lastname;
    private LocalDate birthdate;
}
