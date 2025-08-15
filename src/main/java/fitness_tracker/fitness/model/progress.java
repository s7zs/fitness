package fitness_tracker.fitness.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class progress {
    @Id
    private int progressid;

    private float wightgoal;
    private float fatpercent;
    private Date startdate;
    private Date enddate;
@OneToOne
    private users user;

}
