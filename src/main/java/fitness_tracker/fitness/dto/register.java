package fitness_tracker.fitness.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class register {

  
        private String email;

        
        private String password;

      
        private String phonenumber;

       
        private char gender;

        private int age;

        private float weight;

       
        private float height;

       
        private String past_health_conditions;
private Date startdate = new Date();
private String goal;


}
