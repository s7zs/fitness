package fitness_tracker.fitness.Repository;

import fitness_tracker.fitness.model.Coach;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CoachRepo extends JpaRepository<Coach, Long> {


    @Query("SELECT c FROM Coach c JOIN c.followers u WHERE u.name = :username")
    List<Coach> findCoachesFollowedByUser(@Param("username") String username);

    // Add this method for searching coaches by username (contains, case insensitive)
    //List<Coach> findByUsernameContainingIgnoreCase(String username);

    // Alternative: If you want to search by both username and email
    //List<Coach> findByUsernameContainingIgnoreCaseOrEmailContainingIgnoreCase(String username, String email);

    Optional<Coach> findByName(String username);
    Optional<Coach> findByEmail(String email);
    //boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}