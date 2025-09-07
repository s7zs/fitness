package fitness_tracker.fitness.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.PastOrPresent;
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

public class nutritionplan {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "progress_seq")
    @SequenceGenerator(name = "progress_seq", sequenceName = "progress_sequence", allocationSize = 600)
    private  long nutritionid;

    @PastOrPresent(message = "Start date must be in the past or present")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date startdate;

    @PastOrPresent(message = "End date must be in the past or present")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date enddate;

    @OneToOne
    @JoinColumn( name = "userid")
    private users user;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinTable(
            name = "nutrition_plan_meals",
            joinColumns = @JoinColumn(name = "nutrition_plan_id"),
            inverseJoinColumns = @JoinColumn(name = "meal_id")
    )
    private Set<meal> meals = new HashSet<>();
}
