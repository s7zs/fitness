package fitness_tracker.fitness.Repository;

import fitness_tracker.fitness.model.meal;
import fitness_tracker.fitness.model.nutritionplan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface MealRepo extends JpaRepository<meal,Long> {
    //@Query(nativeQuery = true , value = "SELECT * FROM USERS")
    List<meal> findByNutritionPlans(nutritionplan nutritionPlan);
//    @Query("SELECT m FROM meal m WHERE m.nutritionplan.id = :planId")
  //  List<meal> findByNutritionplanId(Long planId);

    //List<meal>findbynutritionplanid (Long nutritionplanid);
    List<meal> findByNutritionplans_Id(Long nutritionPlanId);
}