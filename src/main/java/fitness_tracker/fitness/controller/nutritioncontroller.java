package fitness_tracker.fitness.controller;

import fitness_tracker.fitness.dto.CreateNutritionPlanRequest;
import fitness_tracker.fitness.dto.MealResponse;
import fitness_tracker.fitness.model.meal;
import fitness_tracker.fitness.model.nutritionplan;
import fitness_tracker.fitness.service.NutritionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth/nutrition")
public class nutritioncontroller {

    @Autowired
    private NutritionService nutritionService;

    /**
     * Create a nutrition plan for a specific user
     * POST /auth/nutrition/plan/{userId}
     */
    @PostMapping("/plan/{userId}")
    public ResponseEntity<?> createNutritionPlanForUser(@PathVariable Long userId, @RequestBody nutritionplan plan) {
        try {
            nutritionplan saved = nutritionService.createNutritionPlanForUser(userId, plan);
            return ResponseEntity.ok(saved);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * Update nutrition plan for a specific user
     * PUT /auth/nutrition/plan/{userId}
     */
    @PutMapping("/updateplan/{userId}")
    public ResponseEntity<?> updateNutritionPlanForUser(@PathVariable Long userId, @RequestBody nutritionplan plan) {
        try {
            nutritionplan updated = nutritionService.updateNutritionPlanForUser(userId, plan);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * Get nutrition plan for a specific user by user ID
     * GET /auth/nutrition/plan/{userId}
     */
    @GetMapping("/getplan/{userId}")
    public ResponseEntity<?> getNutritionPlanByUserId(@PathVariable Long userId) {
        try {
            nutritionplan plan = nutritionService.getNutritionPlanByUserId(userId);
            return ResponseEntity.ok(plan);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * Get current user's nutrition plan
     * GET /auth/nutrition/my-plan
     */
    @GetMapping("/my-plan")
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

    /**
     * Get meals from current user's nutrition plan
     * GET /auth/nutrition/my-meals
     */
    @GetMapping("/my-meals")
    public ResponseEntity<?> getMyMeals() {
        try {
            if (!nutritionService.hasNutritionPlan()) {
                return ResponseEntity.badRequest().body("No nutrition plan assigned yet. Please contact your coach.");
            }

            nutritionplan plan = nutritionService.getCurrentUserNutritionPlan();
            
            // Convert meals to DTOs to avoid circular reference
            Set<MealResponse> mealResponses = plan.getMeals().stream()
                    .map(this::convertToMealResponse)
                    .collect(Collectors.toSet());
            
            return ResponseEntity.ok(mealResponses);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error retrieving meals: " + e.getMessage());
        }
    }
    
    /**
     * Convert meal entity to MealResponse DTO
     */
    private MealResponse convertToMealResponse(meal meal) {
        MealResponse response = new MealResponse();
        response.setMeal_id(meal.getMeal_id());
        response.setMeal_name(meal.getMeal_name());
        response.setTime(meal.getTime());
        response.setGramofcarb(meal.getGramofcarb());
        response.setGramofprotien(meal.getGramofprotien());
        response.setGramoffat(meal.getGramoffat());
        return response;
    }

    /**
     * Add a meal to current user's nutrition plan
     * POST /auth/nutrition/add-meal/{mealId}
     */
    @PostMapping("/add-meal/{mealId}")
    public ResponseEntity<?> addMealToMyPlan(@PathVariable Long mealId) {
        try {
            nutritionplan updated = nutritionService.addMealToCurrentUserPlan(mealId);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * Check if current user has a nutrition plan
     * GET /auth/nutrition/has-plan
     */
    @GetMapping("/has-plan")
    public ResponseEntity<?> hasNutritionPlan() {
        try {
            boolean hasPlan = nutritionService.hasNutritionPlan();
            return ResponseEntity.ok(hasPlan);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error checking nutrition plan status: " + e.getMessage());
        }
    }

    /**
     * Create nutrition plan for user using DTO with meal IDs
     * POST /auth/nutrition/plan-with-meals/{userId}
     */
    @PostMapping("/plan-with-meals/{userId}")
    public ResponseEntity<?> createNutritionPlanWithMeals(@PathVariable Long userId, @RequestBody CreateNutritionPlanRequest request) {
        try {
            nutritionplan saved = nutritionService.createNutritionPlanForUserWithMealIds(userId, request);
            return ResponseEntity.ok(saved);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * Add meals to existing user's nutrition plan
     * POST /auth/nutrition/add-meals/{userId}
     */
    @PostMapping("/add-meals/{userId}")
    public ResponseEntity<?> addMealsToUserPlan(@PathVariable Long userId, @RequestBody Set<Long> mealIds) {
        try {
            nutritionplan updated = nutritionService.addMealsToUserPlan(userId, mealIds);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
