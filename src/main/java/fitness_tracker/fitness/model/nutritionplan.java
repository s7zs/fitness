package fitness_tracker.fitness.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter

public class nutritionplan {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "progress_seq")
    @SequenceGenerator(name = "progress_seq", sequenceName = "progress_sequence", allocationSize = 600)
    private  long nutritionid;

    @PastOrPresent
    @NotNull
    //@NotBlank(message = "date is required")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date startdate;

    @FutureOrPresent
    //@PastOrPresent
    @NotNull
    // @NotBlank(message = "date is required")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date enddate;

    @OneToOne
    @JoinColumn( name = "userid")
    private users user;

    @ManyToMany
    @JoinTable(
            name = "nutrition_plan_mealS",
            joinColumns = @JoinColumn(name = "nutrition_plan_id"),
            inverseJoinColumns = @JoinColumn(name = "meal_id")
    )
    private Set<meal> meals = new HashSet<>();
}