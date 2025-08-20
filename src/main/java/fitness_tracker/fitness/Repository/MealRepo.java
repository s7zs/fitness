package fitness_tracker.fitness.Repository;

import fitness_tracker.fitness.model.meal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MealRepo extends JpaRepository<meal,Long> {
List<meal>findbynutritionplanid (Long nutritionplanid);
}
