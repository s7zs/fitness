package fitness_tracker.fitness.Repository;

import fitness_tracker.fitness.model.progress;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProgressRepo extends JpaRepository<progress,Long > {
}
