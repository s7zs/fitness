package fitness_tracker.fitness.service;

import fitness_tracker.fitness.Repository.ProgressRepo;
import fitness_tracker.fitness.model.Coach;
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
    private final coachservice coachService;

    @Autowired
    public progressService(@Lazy ProgressRepo progressRepo, @Lazy userservice userService,@Lazy coachservice coachService) {
        this.progressRepo = progressRepo;
        this.userService = userService;
        this.coachService = coachService;
    }

    public progress addProgressU(float weightGoal, float fatPercent, Date startDate, Date endDate) {
        users currentUser = userService.getCurrentUserProfile();
        progress prog = new progress();
        prog.setWeightgoal(weightGoal);
        prog.setFatpercent(fatPercent);
        prog.setStartdate(startDate);
        prog.setEnddate(endDate);
        prog.setUser(currentUser);
        return progressRepo.save(prog);
    }

    public List<progress> getMyProgressU() {
        users currentUser = userService.getCurrentUserProfile();
        return progressRepo.findByUser(currentUser);
    }
    public progress addProgressC(float weightGoal, float fatPercent, Date startDate, Date endDate) {
        Coach currentUser = coachService.getCurrentUserProfile();
        progress prog = new progress();
        prog.setWeightgoal(weightGoal);
        prog.setFatpercent(fatPercent);
        prog.setStartdate(startDate);
        prog.setEnddate(endDate);
        prog.setCoach(currentUser);
        return progressRepo.save(prog);
    }

    public List<progress> getMyProgressC() {
        Coach currentUser = coachService.getCurrentUserProfile();
        return progressRepo.findBycoach(currentUser);
    }
}