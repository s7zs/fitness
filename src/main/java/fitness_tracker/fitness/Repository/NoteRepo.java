package fitness_tracker.fitness.Repository;

import fitness_tracker.fitness.model.Note;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoteRepo extends JpaRepository<Note,Integer> {
}
