package fitness_tracker.fitness.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Duration;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class exercise {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "progress_seq")
    @SequenceGenerator(name = "progress_seq", sequenceName = "progress_sequence", allocationSize = 600)
    private long exerciseid;

    @NotNull
    private String exercisename;

    @Positive
    private Duration duration;

    @Lob
    private byte[] video;

    private LocalDate upload;

    @ManyToMany(mappedBy = "exercises")

    private Set<workoutplan> workoutplans = new HashSet<workoutplan>();
}