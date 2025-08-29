package fitness_tracker.fitness.service;

import fitness_tracker.fitness.Repository.WorkoutplanRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service

public class Workoutservice {
    @Autowired
    private WorkoutplanRepo workoutPlanRepository;



    //public List<workoutplan> getCurrentWorkoutPlan() throws  Exception{
       // Long userId = securityUtils.getCurrentUserId();
//return workoutPlanRepository.finfbyuserid(userId);
   // }



}
