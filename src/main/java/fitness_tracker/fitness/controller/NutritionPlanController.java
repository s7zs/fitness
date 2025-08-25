package fitness_tracker.fitness.controller;


import fitness_tracker.fitness.model.nutritionplan;
import fitness_tracker.fitness.service.NutritionPlanService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/nutrition-plans")
@RequiredArgsConstructor
public class NutritionPlanController {
    private NutritionPlanService nutritionPlanService;
    @PostMapping("/{userId}")
    public ResponseEntity<nutritionplan> createNutritionPlan(
            @PathVariable Long userId,
            @RequestBody nutritionplan plan,
            @RequestParam(required = false) Set<Long> mealIds) {
        return ResponseEntity.ok(nutritionPlanService.createnutritionplan(userId, plan, mealIds));
    }

    @PutMapping("/{planId}")
    public ResponseEntity<nutritionplan> updateNutritionPlan(
            @PathVariable Long planId,
            @RequestBody nutritionplan updated,
            @RequestParam(required = false) Set<Long> mealIds) {
        return ResponseEntity.ok(nutritionPlanService.updatenutritionplan(planId, updated, mealIds));
    }
    @DeleteMapping("/{planId}")
    public ResponseEntity<Void> deleteNutritionPlan(@PathVariable Long planId) {
        nutritionPlanService.deleteNutritionPlan(planId);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<nutritionplan>> getPlansByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(nutritionPlanService.getNutritionPlansByUser(userId));
    }

    @GetMapping("/{planId}")
    public ResponseEntity<nutritionplan> getPlanById(@PathVariable Long planId) {
        return ResponseEntity.ok(nutritionPlanService.getNutritionPlanById(planId));
    }


}
