package fitness_tracker.fitness.Repository;

import fitness_tracker.fitness.model.meal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MealRepo extends JpaRepository<meal,Long> {
}
