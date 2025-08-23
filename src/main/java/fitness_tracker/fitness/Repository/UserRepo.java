package fitness_tracker.fitness.Repository;

import fitness_tracker.fitness.model.users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<users, Long> {
    Optional<users> findByEmail(String email);
    boolean existsByEmail(String email);
}