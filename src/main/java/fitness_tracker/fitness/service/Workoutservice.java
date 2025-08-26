package fitness_tracker.fitness.service;

import fitness_tracker.fitness.Repository.UserRepo;
import fitness_tracker.fitness.Repository.WorkoutplanRepo;
import fitness_tracker.fitness.model.users;
import fitness_tracker.fitness.model.workoutplan;
import fitness_tracker.fitness.secuirty.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class Workoutservice {
    @Autowired
    private WorkoutplanRepo workoutPlanRepository;

    @Autowired
    private SecurityUtils securityUtils;
    @Autowired
    private UserRepo userRepo;

    public List<workoutplan> getCurrentWorkoutPlan() throws  Exception{
        Long userId = securityUtils.getCurrentUserId();
        return workoutPlanRepository.finfbyuserid(userId);
    }

    public workoutplan createworkoutplan(Long userid ,workoutplan plan){
    Long coachid = securityUtils.getCurrentUserId();
    users trainee = userRepo.findById(userid)
            .orElseThrow(()-> new RuntimeException("user not found"));
    plan.setUser(trainee);
    plan.setTime(plan.getTime());
    plan.setExercises(plan.getExercises());
    return workoutPlanRepository.save(plan);
    }
    public workoutplan updateworkoutplan(Long planid,workoutplan updateplan){
        workoutplan existing = workoutPlanRepository.findById(planid)
                .orElseThrow(() -> new RuntimeException("Plan not found"));

        existing.setTime(updateplan.getTime());
        existing.setUser(updateplan.getUser());
        existing.setExercises(updateplan.getExercises());
        return workoutPlanRepository.save(existing);
    }

    public void deleteWorkoutPlan(Long planId) {
        workoutPlanRepository.deleteById(planId);
    }


    public List<workoutplan> getWorkoutPlansForUser(Long userId) {
        users trainee = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return workoutPlanRepository.findByUser(trainee);
    }


    public List<workoutplan> getWorkoutPlansForCurrentCoach() {
        Long coachId = securityUtils.getCurrentUserId();

        throw new UnsupportedOperationException( );
    }






}
