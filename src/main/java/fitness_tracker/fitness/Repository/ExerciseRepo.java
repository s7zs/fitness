package fitness_tracker.fitness.Repository;

import fitness_tracker.fitness.model.exercise;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExerciseRepo extends JpaRepository<exercise,Long> {
}
