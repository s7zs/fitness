package fitness_tracker.fitness.Repository;

import fitness_tracker.fitness.model.nutritionplan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NutritionplanRepo extends JpaRepository<nutritionplan,Long > {

List<nutritionplan> findbuuserid (Long userid);
}
