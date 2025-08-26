package fitness_tracker.fitness.service;

import fitness_tracker.fitness.Repository.MealRepo;
import fitness_tracker.fitness.Repository.NutritionplanRepo;
import fitness_tracker.fitness.Repository.UserRepo;
import fitness_tracker.fitness.model.meal;
import fitness_tracker.fitness.model.nutritionplan;
import fitness_tracker.fitness.model.users;
import fitness_tracker.fitness.secuirty.SecurityUtils;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.jta.UserTransactionAdapter;

import java.util.List;
import java.util.Set;

@Service
public class NutritionPlanService {
    @Autowired
    private SecurityUtils securityUtils;
    @Autowired
    private NutritionplanRepo nutritionplanRepo;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private MealRepo mealRepo;

    public nutritionplan createnutritionplan(Long userid, nutritionplan plan, Set<Long> mealIds) {
        Long coachid = securityUtils.getCurrentUserId();
        users trainee = userRepo.findById(userid)
                .orElseThrow(() -> new RuntimeException("user not found"));
        plan.setUser(trainee);
        if (mealIds != null && !mealIds.isEmpty()) {

            Set<meal> meals = Set.copyOf(mealRepo.findAllById(mealIds));
            plan.setMeals(meals);
        }
        return nutritionplanRepo.save(plan);

    }
    public nutritionplan updatenutritionplan(Long planId, nutritionplan updatedPlan, Set<Long> mealIds){
        nutritionplan existing = nutritionplanRepo.findById(planId)
                .orElseThrow(()->new RuntimeException("user not found"));
          existing.setStartdate(updatedPlan.getStartdate());
          existing.setEnddate(updatedPlan.getEnddate());

        if (mealIds != null && !mealIds.isEmpty()) {
            Set<meal> meals = Set.copyOf(mealRepo.findAllById(mealIds));
            existing.setMeals(meals);

        }
        return nutritionplanRepo.save(existing);
    }
    public void deleteNutritionPlan(Long planId) {
        if (!nutritionplanRepo.existsById(planId)) {
            throw new EntityNotFoundException("Nutrition Plan not found with id " + planId);
        }
        nutritionplanRepo.deleteById(planId);
    }


    public List<nutritionplan> getNutritionPlansByUser(Long userId) {
        return nutritionplanRepo.findByUser_Userid(userId);
    }


    public nutritionplan getNutritionPlanById(Long planId) {
        return nutritionplanRepo.findById(planId)
                .orElseThrow(() -> new EntityNotFoundException("Nutrition Plan not found with id " + planId));
    }




}