package fitness_tracker.fitness.Repository;

import fitness_tracker.fitness.model.nutritionplan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface NutritionplanRepo extends JpaRepository<nutritionplan,Long > {
    @Query(nativeQuery = true , value = "SELECT * FROM USERS")
    List<nutritionplan> findbuuserid (Long userid);
    List<nutritionplan> findByUser_Userid(Long userId);
}