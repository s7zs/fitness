package fitness_tracker.fitness.Repository;

import fitness_tracker.fitness.model.meal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface MealRepo extends JpaRepository<meal,Long> {
    @Query(nativeQuery = true , value = "SELECT * FROM USERS")
List<meal>findbynutritionplanid (Long nutritionplanid);
}
