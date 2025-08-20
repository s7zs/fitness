package fitness_tracker.fitness.Repository;

import fitness_tracker.fitness.model.workoutplan;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WorkoutplanRepo extends JpaRepository<workoutplan,Long > {
List<workoutplan>finfbyuserid(Long userid);
}
