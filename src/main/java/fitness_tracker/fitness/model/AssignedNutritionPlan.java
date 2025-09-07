// fitness_tracker.fitness.model.AssignedNutritionPlan.java

package fitness_tracker.fitness.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "assigned_nutrition_plans")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AssignedNutritionPlan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "coach_id", nullable = false)
    private Coach coach; // من قام بالتعيين

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private users user; // لمن تم التعيين

    @ManyToOne
    @JoinColumn(name = "nutrition_plan_id", nullable = false)
    private nutritionplan nutritionPlan; // الخطة المسبقة المختارة

    private LocalDateTime assignedAt; // متى تم التعيين
    private LocalDateTime startDate;   // تاريخ بدء تنفيذ الخطة
    private LocalDateTime endDate;     // تاريخ انتهاء الخطة

    private String coachNotes; // ملاحظات اختيارية من المدرب

    // Constructors
    public AssignedNutritionPlan(Coach coach, users user, nutritionplan nutritionPlan, LocalDateTime startDate, LocalDateTime endDate, String coachNotes) {
        this.coach = coach;
        this.user = user;
        this.nutritionPlan = nutritionPlan;
        this.assignedAt = LocalDateTime.now();
        this.startDate = startDate;
        this.endDate = endDate;
        this.coachNotes = coachNotes;
    }
}