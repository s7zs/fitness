package fitness_tracker.fitness.model;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
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
public class Note {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private int noteid;

@FutureOrPresent
private Date date;

@Min( value = 10)
@Max(value = 500)
private String content;


@ManyToOne
@JoinColumn(name = " user_id")
private users users;
@ManyToOne
@JoinColumn(name = " coach_id")
private Coach coach;

}
