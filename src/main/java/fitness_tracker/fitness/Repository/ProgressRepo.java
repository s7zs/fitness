package fitness_tracker.fitness.Repository;

import fitness_tracker.fitness.model.progress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProgressRepo extends JpaRepository<progress,Long > {
    @Query(nativeQuery = true , value = "SELECT * FROM USERS")
    List<progress>findbyuserid (Long userid);

}