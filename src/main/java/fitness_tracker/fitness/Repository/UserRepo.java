package fitness_tracker.fitness.Repository;

import fitness_tracker.fitness.model.users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepo extends JpaRepository<users,Long> {
    Optional<users>FindByUsername(String username);
    Optional<users>FindByEmail(String email);
    boolean ExistByUsername( String username);
    boolean ExistByEmail(String email);
}
