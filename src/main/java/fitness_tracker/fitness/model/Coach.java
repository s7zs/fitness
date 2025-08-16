package fitness_tracker.fitness.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Coach {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private int coachid;
private String  username;
private String  password;
private  String phonenumber;
private String gender;
private int age;
private String experince;
private boolean issusbended;

@OneToMany(mappedBy = "coach")
private List<users> users;
@OneToMany(mappedBy = "coach")
private List<LoginRegister> loginRegister;

@OneToMany(mappedBy = "coach")
private List<Note> note;

}
