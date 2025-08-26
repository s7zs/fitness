package fitness_tracker.fitness.service;

import fitness_tracker.fitness.Repository.ProgressRepo;
import fitness_tracker.fitness.model.Note;
import fitness_tracker.fitness.model.progress;
import fitness_tracker.fitness.model.users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class progressService {
    private final ProgressRepo progressRepo;
    private  final userservice userService;

    @Autowired
    public progressService(@Lazy ProgressRepo progressRepo,@Lazy userservice userService) {
        this.progressRepo = progressRepo;
        this.userService = userService;
    }

    public progress addProgress(float weightGoal, float fatPercent, Date startDate, Date endDate) {
        users currentUser = userService.getCurrentUserProfile();
        progress prog = new progress();
        prog.setWeightgoal(weightGoal);
        prog.setFatpercent(fatPercent);
        prog.setStartdate(startDate);
        prog.setEnddate(endDate);
        prog.setUser(currentUser);
        return progressRepo.save(prog);
    }

    public List<progress> getMyProgress() {
        users currentUser = userService.getCurrentUserProfile();
        return progressRepo.findByUser(currentUser);
    }
}
