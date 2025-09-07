package fitness_tracker.fitness.Repository;

import fitness_tracker.fitness.model.exercise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ExerciseRepo extends JpaRepository<exercise,Long> {
    @Query("SELECT e FROM exercise e JOIN e.workoutplans wp WHERE wp.workoutid = :workplanid")
    List<exercise> findByWorkplanId(@Param("workplanid") Long workplanid);
    // البحث بالاسم
    List<exercise> findByExercisenameContainingIgnoreCase(String exercisename);

    // جلب التمارين الحديثة
    List<exercise> findTop10ByOrderByUploadDesc();
}