package fitness_tracker.fitness.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

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

    @PastOrPresent
    @NotBlank(message = "date is required")
    private Date startdate;

    @PastOrPresent
    @NotBlank(message = "date is required")
    private Date enddate;

    @OneToOne
    @JoinColumn( name = "userid")
    private users user;
}
