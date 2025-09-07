package fitness_tracker.fitness.controller;


import fitness_tracker.fitness.dto.setuserinfo;
import fitness_tracker.fitness.model.Coach;
import fitness_tracker.fitness.model.nutritionplan;
import fitness_tracker.fitness.model.users;
import fitness_tracker.fitness.model.workoutplan;
import fitness_tracker.fitness.service.NutritionService;
import fitness_tracker.fitness.service.Workoutservice;
import fitness_tracker.fitness.service.userservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/auth/user")
public class usercontroller {


    @Autowired
    private userservice userservice;

    @Autowired
    private Workoutservice workoutservice;

    @Autowired
    private NutritionService nutritionService;

    private final AuthenticationManager authenticationManager;

    @Autowired
    public usercontroller( @Lazy AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @GetMapping("/AllUsers")
    public List<users> view()
    {
        return userservice.viewUsers();
    }

    @GetMapping("/profile")
    public ResponseEntity<users> getProfile() {
        return ResponseEntity.ok(userservice.getCurrentUserProfile());
    }

    @PutMapping("/setprofile")
    public ResponseEntity<?> updateProfile(@RequestBody setuserinfo userInfo, Principal Principal) {
        String email = Principal.getName();
        users updateUser = userservice.updateUserProfile(userInfo);
        return ResponseEntity.ok(updateUser);
    }

    @GetMapping("/coach")
    public ResponseEntity<List<Coach>> getCoaches() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(401).body(null);
        }
        return ResponseEntity.ok(userservice.getAllCoaches());
    }
    @PostMapping ("/coach/search/{username}")
    public ResponseEntity<Optional<Coach>> getCoachesByUsername(@PathVariable String username) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(401).body(null);
        }
        return ResponseEntity.ok(userservice.searchCoachesByUsername(username));
    }
    @PostMapping("/coach/follow/{username}")
    public ResponseEntity<?> followCoach(@PathVariable String username) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(401).body(null);
        }
        return ResponseEntity.ok(userservice.followCoach(username));
    }
    @PostMapping("/coach/unfollow/{username}")
    public ResponseEntity<?> unfollowCoach(@PathVariable String username) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(401).body(null);
        }
        return ResponseEntity.ok(userservice.unfollowCoach(username));
    }
    @GetMapping("/coach/followed")
    public ResponseEntity<List<Coach>> getFollowedCoaches() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(401).body(null);
        }
        return ResponseEntity.ok(userservice.getFollowedCoaches());
    }


   /* @PostMapping("/createplan/{userId}")
    public ResponseEntity<?> createPlan(@PathVariable Long userId, @RequestBody nutritionplan plan) {
        try {
            nutritionplan saved = nutritionService.createNutritionPlanForUser(userId, plan);
            return ResponseEntity.ok(saved);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // ✅ Update plan for a specific user

    @PutMapping("/{userId}")
    public ResponseEntity<?> updatePlan(@PathVariable Long userId, @RequestBody nutritionplan plan) {
        try {
            nutritionplan updated = nutritionService.updateNutritionPlanForUser(userId, plan);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping("/nutrition")
    public ResponseEntity<?> getMyNutritionPlan() {
        try {
            // Check if user has nutrition plan
            if (!nutritionService.hasNutritionPlan()) {
                return ResponseEntity.badRequest().body("No nutrition plan assigned yet. Please contact your coach.");
            }

            nutritionplan plan = nutritionService.getCurrentUserNutritionPlan();
            return ResponseEntity.ok(plan);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error retrieving nutrition plan: " + e.getMessage());
        }
    }

    @GetMapping("/nutrition/meals")
    public ResponseEntity<?> getMyMeals() {
        try {
            if (!nutritionService.hasNutritionPlan()) {
                return ResponseEntity.badRequest().body("No nutrition plan assigned yet. Please contact your coach.");
            }

            nutritionplan plan = nutritionService.getCurrentUserNutritionPlan();
            return ResponseEntity.ok(plan.getMeals());

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error retrieving meals: " + e.getMessage());
        }
    }
*/
    @GetMapping("/workout")
    public ResponseEntity<?> getMyWorkoutPlan() {
        try {
            // Check if user has workout plan
            if (!workoutservice.hasWorkoutPlan()) {
                return ResponseEntity.badRequest().body("No workout plan assigned yet. Please contact your coach.");
            }

            workoutplan plan = workoutservice.getCurrentUserWorkoutPlan();
            return ResponseEntity.ok(plan);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error retrieving workout plan: " + e.getMessage());
        }

    }

    @GetMapping("/workout/exercises")
    public ResponseEntity<?> getMyExercises() {
        try {
            if (!workoutservice.hasWorkoutPlan()) {
                return ResponseEntity.badRequest().body("No workout plan assigned yet. Please contact your coach.");
            }

            workoutplan plan = workoutservice.getCurrentUserWorkoutPlan();
            return ResponseEntity.ok(plan.getExercises());

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error retrieving exercises: " + e.getMessage());
        }
    }

}
