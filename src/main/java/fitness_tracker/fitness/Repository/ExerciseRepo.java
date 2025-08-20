package fitness_tracker.fitness.Repository;

import fitness_tracker.fitness.model.exercise;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExerciseRepo extends JpaRepository<exercise,Long> {
    List<exercise> findbyworkplanid (Long workplanid);
}
