package fitness_tracker.fitness.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
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

    private String meal_name;

    private Date time;

    private String gramofcarb;

    private String gramofprotien;

    private String gramoffat;

    @ManyToMany(mappedBy = "meals")
    private Set<nutritionplan> nutritionplans;

}
