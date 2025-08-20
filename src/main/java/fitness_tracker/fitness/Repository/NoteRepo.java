package fitness_tracker.fitness.Repository;

import fitness_tracker.fitness.model.Note;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NoteRepo extends JpaRepository<Note,Integer> {
List<Note>findbyuserid (Long userid);
List<Note>findbycoachid(Long coachid);
}
