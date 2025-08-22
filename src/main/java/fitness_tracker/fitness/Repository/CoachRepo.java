package fitness_tracker.fitness.Repository;

import fitness_tracker.fitness.model.Coach;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CoachRepo extends JpaRepository<Coach,Long> {
    @Query(nativeQuery = true , value = "SELECT * FROM USERS")
    List<Coach> FindByEmail(String email);

}
