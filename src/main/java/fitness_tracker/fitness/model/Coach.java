package fitness_tracker.fitness.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.Type;
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

    @NotBlank(message = "Username is required")
    @Size(min = 3, max = 20, message = "Username must be between 3 and 20 characters")
private String  username;

    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$", message = "Password must contain at least one digit, one lowercase, one uppercase letter and one special character")
private String  password;

    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^[+]?[(]?[0-9]{3}[)]?[-\\s.]?[0-9]{3}[-\\s.]?[0-9]{4,6}$", message = "Invalid phone number format")
private  String phonenumber;

@Size(max = 1)
private char gender;
@Min(value = 20)
private int age;


private List<String> experince;


private boolean issusbended;

@OneToMany(mappedBy = "coach")
private List<users> users;
@OneToMany(mappedBy = "coach")
private List<LoginRegister> loginRegister;

@OneToMany(mappedBy = "coach")
private List<Note> note;

}
