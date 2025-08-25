package fitness_tracker.fitness.controller;

import fitness_tracker.fitness.model.workoutplan;
import fitness_tracker.fitness.service.Workoutservice;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/workouts")
@RequiredArgsConstructor
public class WorkoutController {
    private Workoutservice workoutService;
    @PostMapping("/{userId}")
    public ResponseEntity<workoutplan> createWorkoutPlan(
            @PathVariable Long userId,
            @RequestBody workoutplan plan) {
        return ResponseEntity.ok(workoutService.createworkoutplan(userId, plan));
    }

    @PutMapping("/{planId}")
    public ResponseEntity<workoutplan> updateWorkoutPlan(
            @PathVariable Long planId,
            @RequestBody workoutplan updatedPlan) {
        return ResponseEntity.ok(workoutService.updateworkoutplan(planId, updatedPlan));
    }

    @DeleteMapping("/{planId}")
    public ResponseEntity<Void> deleteWorkoutPlan(@PathVariable Long planId) {
        workoutService.deleteWorkoutPlan(planId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<workoutplan>> getWorkoutPlansForUser(@PathVariable Long userId) {
        return ResponseEntity.ok(workoutService.getWorkoutPlansForUser(userId));
    }






}
