package fitness_tracker.fitness.model;

import jakarta.persistence.*;
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
private Date date;
private String content;
@ManyToOne
@JoinColumn(name = " user_id")
private users users;
@ManyToOne
@JoinColumn(name = " coach_id")
private Coach coach;

}
