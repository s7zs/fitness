package fitness_tracker.fitness.Repository;

import fitness_tracker.fitness.model.Coach;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoachRepo extends JpaRepository<Coach,Integer> {
}
