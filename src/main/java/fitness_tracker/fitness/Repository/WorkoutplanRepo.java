package fitness_tracker.fitness.Repository;

import fitness_tracker.fitness.model.workoutplan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkoutplanRepo extends JpaRepository<workoutplan,Long > {
}
