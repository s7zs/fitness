package fitness_tracker.fitness.Repository;

import fitness_tracker.fitness.model.AssignedNutritionPlan;
import fitness_tracker.fitness.model.users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AssignedNutritionPlanRepo extends JpaRepository<AssignedNutritionPlan,Long> {
    @Query("SELECT a FROM AssignedNutritionPlan a WHERE a.user = :user ORDER BY a.assignedAt DESC")
    Optional<AssignedNutritionPlan> findTopByUserOrderByAssignedAtDesc(@Param("user") users user);
}
