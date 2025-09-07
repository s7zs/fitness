package fitness_tracker.fitness.model;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.time.Duration;
import java.util.List;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter

public class workoutplan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    private long workoutid;

    @NonNull
    @Positive
    private Duration  time;

    @Valid
    @OneToOne
    @JoinColumn(name = "userid")
    private users user;

    @ManyToMany()
    @JoinTable(
            name = "workout_plan_exercises",
            joinColumns = @JoinColumn(name = "workout_plan_id"),
            inverseJoinColumns = @JoinColumn(name = "exercise_id")
    )
    private Set<exercise> exercises;


}