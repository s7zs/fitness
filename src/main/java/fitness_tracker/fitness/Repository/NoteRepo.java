package fitness_tracker.fitness.Repository;

import fitness_tracker.fitness.model.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface NoteRepo extends JpaRepository<Note,Long> {
List<Note>findbyuserid (Long userid);
List<Note>findbycoachid(Long coachid);
}
