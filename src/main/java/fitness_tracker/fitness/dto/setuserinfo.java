package fitness_tracker.fitness.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class setuserinfo {
    String phonnumber;
    char gender;
    int age;
    float weight;
    float height;
    String past_conditions;
    String goal;

}
