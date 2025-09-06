package fitness_tracker.fitness.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class setuserinfo {
    String phonenumber;
    String gender;
    int age;
    float weight;
    float height;
    String past_conditions;
    String goal;

}