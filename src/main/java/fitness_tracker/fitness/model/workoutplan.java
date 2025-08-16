package fitness_tracker.fitness.model;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.time.Duration;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter

public class workoutplan {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "progress_seq")
    @SequenceGenerator(name = "progress_seq", sequenceName = "progress_sequence", allocationSize = 600)
    private long workoutid;

    @NonNull
    @Positive
    private Duration  time;

    @Valid
    @OneToOne
    @JoinColumn(name = "userid")
    private users user;


}
