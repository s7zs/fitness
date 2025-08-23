package fitness_tracker.fitness.Repository;

import fitness_tracker.fitness.model.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface NoteRepo extends JpaRepository<Note,Long> {
    @Query(nativeQuery = true , value = "SELECT * FROM USERS")
List<Note>findbyuserid (Long userid);
    @Query(nativeQuery = true , value = "SELECT * FROM USERS")
List<Note>findbycoachid(Long coachid);
}
