package fitness_tracker.fitness.Repository;

import fitness_tracker.fitness.model.workoutplan;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkoutplanRepo extends JpaRepository<workoutplan,Long > {

}
