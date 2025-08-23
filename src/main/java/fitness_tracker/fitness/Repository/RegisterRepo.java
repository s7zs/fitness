package fitness_tracker.fitness.Repository;

import fitness_tracker.fitness.model.LoginRegister;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegisterRepo extends JpaRepository<LoginRegister,Long> {
}
