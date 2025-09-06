package fitness_tracker.fitness.Repository;

import fitness_tracker.fitness.model.exercise;
import fitness_tracker.fitness.model.workoutplan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ExerciseRepo extends JpaRepository<exercise,Long> {
 //   @Query(nativeQuery = true , value = "SELECT * FROM USERS")
   // List<exercise> findbyworkplanid (Long workplanid);
 List<exercise> findByWorkoutplans(workoutplan workoutPlan);
   // @Query("SELECT e FROM exercise e JOIN e.workoutPlans wp WHERE wp.workoutid = :planId")
    //List<exercise> findByWorkoutplansId(@Param("planId") Long planId);
   List<exercise> findByWorkoutplans_Id(Long workoutPlanId);

}