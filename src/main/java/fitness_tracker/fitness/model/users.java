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
public class users {
    @Id
    private int id;

    private String username;
    private String password;
    private  String phonenumber;

    private char gender;
    private int age;
    private float wight;
    private float hight;
    private String past_health_conditions;
    private char userrole;
    private boolean ismember;
    private Date startdate;
    private Date enddate;
    private String goal;
    private boolean issusbended;
    @OneToOne
    private progress progress;



}
