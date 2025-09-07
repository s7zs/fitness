package fitness_tracker.fitness.service;

import fitness_tracker.fitness.Repository.MealRepo;
import fitness_tracker.fitness.Repository.NutritionplanRepo;
import fitness_tracker.fitness.Repository.UserRepo;
import fitness_tracker.fitness.dto.CreateNutritionPlanRequest;
import fitness_tracker.fitness.model.meal;
import fitness_tracker.fitness.model.nutritionplan;
import fitness_tracker.fitness.model.users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class NutritionService {
    @Autowired
    private NutritionplanRepo nutritionPlanRepository;
    @Autowired
    private userservice userservice;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private MealRepo mealRepo;

@Transactional
    public nutritionplan createNutritionPlanForUser(Long userId, nutritionplan plan) {
        // Find the target user
        users targetUser = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));
        Optional<nutritionplan> existingPlan = nutritionPlanRepository.findByUser(targetUser);
        if (existingPlan.isPresent()) {
            throw new RuntimeException("User already has a nutrition plan. Update it instead of creating new one.");
        }

        Set<meal> mealss = new HashSet<>();

        for(meal meal : plan.getMeals()){

            meal savedMeal = mealRepo.save(meal);

            mealss.add(savedMeal);

        }


        plan.setMeals(mealss);

        plan.setUser(targetUser);

        return nutritionPlanRepository.save(plan);
    }
    public nutritionplan updateNutritionPlanForUser(Long userId, nutritionplan updatedPlan) {
        users targetUser = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));

        nutritionplan existingPlan = nutritionPlanRepository.findByUser(targetUser)
                .orElseThrow(() -> new RuntimeException("No nutrition plan found for user ID: " + userId));
        existingPlan.setStartdate(updatedPlan.getStartdate());
        existingPlan.setEnddate(updatedPlan.getEnddate());
        existingPlan.setMeals(updatedPlan.getMeals());

        return nutritionPlanRepository.save(existingPlan);
    }
    public nutritionplan addMealToCurrentUserPlan(Long mealId) {
        users currentUser = userservice.getCurrentUserProfile();

        nutritionplan plan = nutritionPlanRepository.findByUser(currentUser)
                .orElseThrow(() -> new RuntimeException("No nutrition plan found."));

        meal mealObj = mealRepo.findById(mealId)
                .orElseThrow(() -> new RuntimeException("Meal not found."));

        plan.getMeals().add(mealObj);
        return nutritionPlanRepository.save(plan);
    }



    public nutritionplan getCurrentUserNutritionPlan() {
        users currentUser = userservice.getCurrentUserProfile();
        return nutritionPlanRepository.findByUser(currentUser)
                .orElseThrow(() -> new RuntimeException("No nutrition plan found for user: " + currentUser.getEmail()));
    }
    public nutritionplan getNutritionPlanByUserId(Long userId) {
        return nutritionPlanRepository.findByUserUserid(userId)
                .orElseThrow(() -> new RuntimeException("No nutrition plan found for user ID: " + userId));
    }

    public boolean hasNutritionPlan() {
        users currentUser = userservice.getCurrentUserProfile();
        return nutritionPlanRepository.findByUser(currentUser).isPresent();
    }

    /**
     * Create nutrition plan for user using DTO with meal IDs
     */
    @Transactional
    public nutritionplan createNutritionPlanForUserWithMealIds(Long userId, CreateNutritionPlanRequest request) {
        // Find the target user
        users targetUser = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));
        
        // Check if user already has a nutrition plan
        Optional<nutritionplan> existingPlan = nutritionPlanRepository.findByUser(targetUser);
        if (existingPlan.isPresent()) {
            throw new RuntimeException("User already has a nutrition plan. Update it instead of creating new one.");
        }

        // Create new nutrition plan
        nutritionplan plan = new nutritionplan();
        plan.setStartdate(request.getStartdate());
        plan.setEnddate(request.getEnddate());
        plan.setUser(targetUser);

        // Add meals by IDs if provided
        if (request.getMealIds() != null && !request.getMealIds().isEmpty()) {
            Set<meal> meals = new HashSet<>();
            for (Long mealId : request.getMealIds()) {
                meal mealObj = mealRepo.findById(mealId)
                        .orElseThrow(() -> new RuntimeException("Meal not found with ID: " + mealId));
                meals.add(mealObj);
            }
            plan.setMeals(meals);
        }

        return nutritionPlanRepository.save(plan);
    }

    /**
     * Add meals to existing nutrition plan by meal IDs
     */
    @Transactional
    public nutritionplan addMealsToUserPlan(Long userId, Set<Long> mealIds) {
        users targetUser = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));

        nutritionplan plan = nutritionPlanRepository.findByUser(targetUser)
                .orElseThrow(() -> new RuntimeException("No nutrition plan found for user ID: " + userId));

        for (Long mealId : mealIds) {
            meal mealObj = mealRepo.findById(mealId)
                    .orElseThrow(() -> new RuntimeException("Meal not found with ID: " + mealId));
            plan.getMeals().add(mealObj);
        }

        return nutritionPlanRepository.save(plan);
    }


}