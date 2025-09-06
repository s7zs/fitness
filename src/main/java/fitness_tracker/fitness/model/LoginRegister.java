package fitness_tracker.fitness.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
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
    private long sessionid;

    @PastOrPresent
    @NotNull
    private Date startdate;

    @FutureOrPresent
    @NotNull
    private Date endate;

    @ManyToOne
    @JoinColumn(name = "loginRegister")
    private Coach coach;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private users users;



}