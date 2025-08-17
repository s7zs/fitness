package fitness_tracker.fitness.Repository;

import fitness_tracker.fitness.model.users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<users,Long> {
}
