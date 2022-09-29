package data;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;
import miniorm.Column;
import miniorm.Table;

@Table
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Pilot extends Person {

    @Column
    @PositiveOrZero
    private double agility;
}
