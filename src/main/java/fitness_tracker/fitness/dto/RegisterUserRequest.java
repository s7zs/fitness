package fitness_tracker.fitness.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterUserRequest {

        private long userid;
        @NotBlank
        private String email;
        private String phonenumber;
        private int age;
        private String password;
        private char gender;
        private float weight;
        private float height;
        private String past_health_conditions;
        private Date startdate = new Date();


}
