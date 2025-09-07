// fitness_tracker.fitness.model.meal.java

package fitness_tracker.fitness.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "meals")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class meal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    private Long meal_id;

    @Size(min = 3, max = 100, message = "Meal name must be between 3 and 100 characters")
    @Column(nullable = false)
    private String meal_name;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    private LocalTime time; // وقت الوجبة — مثال: 08:00

    @DecimalMin(value = "0.0", message = "Carbs cannot be negative")
    private double gramofcarb;

    @DecimalMin(value = "0.0", message = "Protein cannot be negative")
    private double gramofprotien;

    @DecimalMin(value = "0.0", message = "Fat cannot be negative")
    private double gramoffat;

    // علاقة ManyToMany مع الخطط — وجبة واحدة يمكن أن تظهر في أكثر من خطة
    @ManyToMany(mappedBy = "meals")
    private Set<nutritionplan> nutritionplans = new HashSet<>();

    // إضافة خطة لهذه الوجبة
    public void addNutritionPlan(nutritionplan plan) {
        this.nutritionplans.add(plan);
        plan.getMeals().add(this);
    }

    // حذف خطة من هذه الوجبة
    public void removeNutritionPlan(nutritionplan plan) {
        this.nutritionplans.remove(plan);
        plan.getMeals().remove(this);
    }
}