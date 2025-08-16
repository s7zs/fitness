package fitness_tracker.fitness.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LoginRegister {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private int sessiionid;
private Date startat;
private Date endat;

@ManyToOne
@JoinColumn(name = "coach_id")
private Coach coach;
@ManyToOne
@JoinColumn(name = "user_id")
private users users;



}
