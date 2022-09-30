package data;

import jakarta.validation.constraints.*;
import lombok.*;
import miniorm.Column;
import miniorm.Table;

@Table
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class Pilot extends Person {

    @Column
    @PositiveOrZero
    private Double agility;

    @Null
    @Min(value = 500, message = "minimun flight hours to be a pilot")
    private Integer flightHours;

    @Override
    public Pilot whoAreYou() {
        return (Pilot) super.whoAreYou();
    }
}
