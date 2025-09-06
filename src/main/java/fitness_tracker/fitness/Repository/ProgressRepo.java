package fitness_tracker.fitness.Repository;

import fitness_tracker.fitness.model.Coach;
import fitness_tracker.fitness.model.Note;
import fitness_tracker.fitness.model.progress;
import fitness_tracker.fitness.model.users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProgressRepo extends JpaRepository<progress,Long > {
    List<progress> findByUser (users user);


    List<progress> findBycoach (Coach coach);
}