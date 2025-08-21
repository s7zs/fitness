package fitness_tracker.fitness.Repository;

import fitness_tracker.fitness.model.LoginRegister;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoginRepo extends JpaRepository<LoginRegister,Long> {
}
