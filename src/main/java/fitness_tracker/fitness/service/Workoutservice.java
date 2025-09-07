package fitness_tracker.fitness.service;

import fitness_tracker.fitness.Repository.ExerciseRepo;
import fitness_tracker.fitness.Repository.UserRepo;
import fitness_tracker.fitness.Repository.WorkoutplanRepo;
import fitness_tracker.fitness.model.exercise;
import fitness_tracker.fitness.model.users;
import fitness_tracker.fitness.model.workoutplan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service

public class Workoutservice {
    @Autowired
    private WorkoutplanRepo workoutPlanRepo;
    @Autowired
    private userservice userservice;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private ExerciseRepo exerciseRepo;


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
// ✅ معالجة التمارين بشكل صحيح
        if (plan.getExercises() != null && !plan.getExercises().isEmpty()) {
            Set<exercise> savedExercises = new HashSet<>();
            for (exercise ex : plan.getExercises()) {
                exercise saved;
                if (ex.getExerciseid() != 0) {
                    saved = exerciseRepo.findById(ex.getExerciseid())
                            .orElseGet(() -> exerciseRepo.save(ex));
                } else {
                    saved = exerciseRepo.save(ex);
                }
                savedExercises.add(saved);
            }
            plan.setExercises(savedExercises);
        }

        return workoutPlanRepo.save(plan);
    }


    public workoutplan updateWorkoutPlanForUser(Long userId, workoutplan updatedPlan) {
        users targetUser = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));

        workoutplan existingPlan = workoutPlanRepo.findByUser(targetUser)
                .orElseThrow(() -> new RuntimeException("No workout plan found for user ID: " + userId));

        // Update the existing plan
        existingPlan.setTime(updatedPlan.getTime());
// ✅ معالجة صحيحة للتمارين
        if (updatedPlan.getExercises() != null) {
            Set<exercise> savedExercises = new HashSet<>();
            for (exercise ex : updatedPlan.getExercises()) {
                exercise saved;
                if (ex.getExerciseid() != 0) {
                    saved = exerciseRepo.findById(ex.getExerciseid())
                            .orElseGet(() -> exerciseRepo.save(ex));
                } else {
                    saved = exerciseRepo.save(ex);
                }
                savedExercises.add(saved);
            }
            existingPlan.setExercises(savedExercises); // ✅ استخدام المحفوظ الجديد
        }

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