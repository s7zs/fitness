package fitness_tracker.fitness.service;

import fitness_tracker.fitness.Repository.UserRepo;
import fitness_tracker.fitness.Repository.WorkoutplanRepo;
import fitness_tracker.fitness.model.users;
import fitness_tracker.fitness.model.workoutplan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service

public class Workoutservice {
    @Autowired
    private WorkoutplanRepo workoutPlanRepo;
    @Autowired
    private userservice userservice;
    @Autowired
    private UserRepo userRepo;




    public workoutplan createWorkoutPlanForUser(Long userId, workoutplan plan) {
        // Find the target user
        users targetUser = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));

        // Check if user already has workout plan
        Optional<workoutplan> existingPlan = workoutPlanRepo.findByUser(targetUser);
        if (existingPlan.isPresent()) {
            throw new RuntimeException("User already has a workout plan. Update it instead of creating new one.");
        }

        // Set the user for the plan
        plan.setUser(targetUser);

        // Save and return
        return workoutPlanRepo.save(plan);

    }


    public workoutplan updateWorkoutPlanForUser(Long userId, workoutplan updatedPlan) {
        users targetUser = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));

        workoutplan existingPlan = workoutPlanRepo.findByUser(targetUser)
                .orElseThrow(() -> new RuntimeException("No workout plan found for user ID: " + userId));

        // Update the existing plan
        existingPlan.setTime(updatedPlan.getTime());
        existingPlan.setExercises(updatedPlan.getExercises());

        return workoutPlanRepo.save(existingPlan);
    }

    public workoutplan getCurrentUserWorkoutPlan() {
        users currentUser = userservice.getCurrentUserProfile();
        return workoutPlanRepo.findByUser(currentUser)
                .orElseThrow(() -> new RuntimeException("No workout plan found for user: " + currentUser.getEmail()));
    }
    public workoutplan getWorkoutPlanByUserId(Long userId) {
        return workoutPlanRepo.findByUserUserid(userId)
                .orElseThrow(() -> new RuntimeException("No workout plan found for user ID: " + userId));
    }
    public boolean hasWorkoutPlan() {
        users currentUser = userservice.getCurrentUserProfile();
        return workoutPlanRepo.findByUser(currentUser).isPresent();
    }


}