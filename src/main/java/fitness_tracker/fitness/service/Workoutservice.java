package fitness_tracker.fitness.service;

import fitness_tracker.fitness.Repository.WorkoutplanRepo;
import fitness_tracker.fitness.model.workoutplan;
import fitness_tracker.fitness.secuirty.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service

public class Workoutservice {
    @Autowired
    private WorkoutplanRepo workoutPlanRepository;

    @Autowired
    private SecurityUtils securityUtils;

    public workoutplan getCurrentWorkoutPlan() throws  Exception{
        Long userId = securityUtils.getCurrentUserId();
        return workoutPlanRepository.getuserworkoutplan(userId);
    }



}
