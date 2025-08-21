package fitness_tracker.fitness.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class meal {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "progress_seq")
    @SequenceGenerator(name = "progress_seq", sequenceName = "progress_sequence", allocationSize = 600)
    private long meal_id;

    @Size(min = 5, max = 100)
    private String meal_name;

    private Date time;

    @DecimalMin("0.1")
    @DecimalMax("50.9")
    private double gramofcarb;

    @DecimalMin("0.1")
    @DecimalMax("50.9")
    private double gramofprotien;

    @DecimalMin("0.1")
    @DecimalMax("50.9")
    private double gramoffat;

    @ManyToMany(mappedBy = "meals")
    private Set<nutritionplan> nutritionplans = new HashSet<>();

}
