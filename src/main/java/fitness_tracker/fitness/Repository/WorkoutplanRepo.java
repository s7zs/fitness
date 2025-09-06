package fitness_tracker.fitness.Repository;

import fitness_tracker.fitness.model.users;
import fitness_tracker.fitness.model.workoutplan;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WorkoutplanRepo extends JpaRepository<workoutplan,Long > {
    Optional<workoutplan> findByUser(users user);
    Optional<workoutplan> findByUserUserid(Long userId);
}