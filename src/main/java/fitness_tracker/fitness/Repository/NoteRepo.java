package fitness_tracker.fitness.Repository;

import fitness_tracker.fitness.model.Coach;
import fitness_tracker.fitness.model.Note;
import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface NoteRepo extends JpaRepository<Note,Long> {

}
