package fitness_tracker.fitness.Repository;

import fitness_tracker.fitness.model.nutritionplan;
import fitness_tracker.fitness.model.users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NutritionplanRepo extends JpaRepository<nutritionplan,Long > {
    @Query(nativeQuery = true , value = "SELECT * FROM USERS")
    List<nutritionplan> findbuuserid (Long userid);
    Optional<nutritionplan> findByUser(users user);
    Optional<nutritionplan> findByUserUserid(Long userId);
}