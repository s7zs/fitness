// fitness_tracker.fitness.model.nutritionplan.java

package fitness_tracker.fitness.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "nutrition_plans")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class nutritionplan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    private Long nutritionid;

    @Column(nullable = false)
    private String name; // e.g., "Keto Plan", "Muscle Gain"

    private String description; // Optional description

    @PastOrPresent
    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate createdAt = LocalDate.now(); // تاريخ إنشاء القالب

    // علاقة ManyToMany مع الـ meals — خطة واحدة تحتوي على عدة وجبات
    @ManyToMany
    @JoinTable(
            name = "nutrition_plan_meals",
            joinColumns = @JoinColumn(name = "nutrition_plan_id"),
            inverseJoinColumns = @JoinColumn(name = "meal_id")
    )
    private Set<meal> meals = new HashSet<>();

    // علاقة عكسية مع التعيينات — لتتبع من استخدم هذه الخطة
    @OneToMany(mappedBy = "nutritionPlan", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<AssignedNutritionPlan> assignments = new HashSet<>();

    // إضافة وجبة للخطة
    public void addMeal(meal meal) {
        this.meals.add(meal);
        meal.getNutritionplans().add(this);
    }

    // حذف وجبة من الخطة
    public void removeMeal(meal meal) {
        this.meals.remove(meal);
        meal.getNutritionplans().remove(this);
    }
}