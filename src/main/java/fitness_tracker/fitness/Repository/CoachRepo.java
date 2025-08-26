package fitness_tracker.fitness.Repository;

import fitness_tracker.fitness.model.Coach;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CoachRepo extends JpaRepository<Coach, Long> {
    List<Coach> findByEmail(String email);

}