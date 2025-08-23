package fitness_tracker.fitness.Repository;

import fitness_tracker.fitness.model.exercise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ExerciseRepo extends JpaRepository<exercise,Long> {
    @Query(nativeQuery = true , value = "SELECT * FROM USERS")
    List<exercise> findbyworkplanid (Long workplanid);
}