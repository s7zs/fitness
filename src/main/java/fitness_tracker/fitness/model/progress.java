package fitness_tracker.fitness.model;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Calendar;
import java.util.Date;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class progress {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "progress_seq")
    @SequenceGenerator(name = "progress_seq", sequenceName = "progress_sequence", allocationSize = 600)
    private long progressid;

    @Positive(message = "Weight goal must be positive")
    @Max(value = 500, message = "Weight goal must be less than 500 kg")
    private float weightgoal;

    @DecimalMin(value = "0.0", message = "Fat percentage cannot be negative")
    @DecimalMax(value = "100.0", message = "Fat percentage cannot exceed 100%")
    private float fatpercent;

    @PastOrPresent(message = "Start date must be in the past or present")
    private Date startdate;

    @FutureOrPresent(message = "End date must be in the future or present")
    private Date enddate;

    @Valid
    @OneToOne
    @JoinColumn(name = "userid")
    private users user;


    @PrePersist
    protected void onCreate() {
        this.startdate = new Date(); // today
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(this.startdate);
        calendar.add(Calendar.DAY_OF_MONTH, 30);
        this.enddate = calendar.getTime();
    }

}
