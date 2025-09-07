package fitness_tracker.fitness.controller;

import fitness_tracker.fitness.model.exercise;
import fitness_tracker.fitness.model.nutritionplan;
import fitness_tracker.fitness.model.users;
import fitness_tracker.fitness.model.workoutplan;
import fitness_tracker.fitness.service.NutritionService;
import fitness_tracker.fitness.service.Workoutservice;
import fitness_tracker.fitness.service.coachservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/auth/coach")
public class coachcontroller {

    private final coachservice coachservice;
    private final Workoutservice workoutservice;

    private final NutritionService nutritionService;

    @Autowired
    public coachcontroller(coachservice coachservice, @Lazy Workoutservice workoutservice,  @Lazy NutritionService nutritionService) {
        this.coachservice = coachservice;
        this.workoutservice = workoutservice;

        this.nutritionService = nutritionService;
    }


    @GetMapping("/followers")
    public ResponseEntity<List<users>> getFollowers() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(401).body(null);
        }
        return ResponseEntity.ok(coachservice.getFollowers());
    }

    @GetMapping("/followers/count")
    public ResponseEntity<Map<String, Integer>> getFollowerCount() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(401).body(null);
        }
        return ResponseEntity.ok(Map.of("followers", coachservice.getFollowerCount()));
    }


    // ✅ Create/Update Nutrition Plan for a User
    @PostMapping("/nutrition/create/{userId}")
    public ResponseEntity<?> createNutritionPlan(@PathVariable Long userId, @RequestBody nutritionplan plan) {
        try {
            nutritionplan saved = nutritionService.createNutritionPlanForUser(userId, plan);
            return ResponseEntity.ok(saved);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/nutrition/update/{userId}")
    public ResponseEntity<?> updateNutritionPlan(@PathVariable Long userId, @RequestBody nutritionplan plan) {
        try {
            nutritionplan updated = nutritionService.updateNutritionTemplate(userId, plan);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // ✅ Create/Update Workout Plan for a User
    @PostMapping("/workout/create/{userId}")
    public ResponseEntity<?> createWorkoutPlan(@PathVariable Long userId, @RequestBody workoutplan plan) {
        try {
            workoutplan saved = workoutservice.createWorkoutPlanForUser(userId, plan);
            return ResponseEntity.ok(saved);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/workout/update/{userId}")
    public ResponseEntity<?> updateWorkoutPlan(@PathVariable Long userId, @RequestBody workoutplan plan) {
        try {
            workoutplan updated = workoutservice.updateWorkoutPlanForUser(userId, plan);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }



}