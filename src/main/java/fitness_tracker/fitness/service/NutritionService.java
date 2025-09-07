package fitness_tracker.fitness.service;

import fitness_tracker.fitness.Repository.*;
import fitness_tracker.fitness.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
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
    @Autowired
    private AssignedNutritionPlanRepo assignedNutritionPlanRepo;
    @Autowired
    private CoachRepo coachRepo;
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

        plan.setName(targetUser.getName());

        return nutritionPlanRepository.save(plan);
    }
    @Transactional
    public nutritionplan updateNutritionTemplate(Long planId, nutritionplan updatedTemplate) {
        nutritionplan existingTemplate = nutritionPlanRepository.findById(planId)
                .orElseThrow(() -> new RuntimeException("Template not found with ID: " + planId));

        // تحديث الاسم والوصف فقط — لا نعدل التعيينات!
        existingTemplate.setName(updatedTemplate.getName());
        existingTemplate.setDescription(updatedTemplate.getDescription());

        // تحديث الوجبات — حذف القديم وإضافة الجديد (أو يمكنك عمل merge حسب الحاجة)
        existingTemplate.getMeals().clear(); // أو يمكنك عمل merge بدلاً من clear

        for (meal m : updatedTemplate.getMeals()) {
            meal savedMeal = mealRepo.findById(m.getMeal_id())
                    .orElseGet(() -> mealRepo.save(m));
            existingTemplate.addMeal(savedMeal); // استخدم الدالة اللي أنشأناها للحفاظ على العلاقة الثنائية
        }

        return nutritionPlanRepository.save(existingTemplate);
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

    @Transactional
    public AssignedNutritionPlan assignNutritionPlanToUser(
            Long coachId,
            String userEmail,
            Long nutritionPlanId,
            LocalDateTime startDate,
            LocalDateTime endDate,
            String coachNotes
    ) {
        Coach coach = coachRepo.findById(coachId)
                .orElseThrow(() -> new RuntimeException("Coach not found"));

        users user = userRepo.findByEmail(userEmail).orElseThrow(() -> new RuntimeException("User not found"));

        nutritionplan plan = nutritionPlanRepository.findById(nutritionPlanId)
                .orElseThrow(() -> new RuntimeException("Nutrition plan not found"));

        AssignedNutritionPlan assignment = new AssignedNutritionPlan(
                coach, user, plan, startDate, endDate, coachNotes
        );

        return assignedNutritionPlanRepo.save(assignment);
    }
    // داخل NutritionService.java

    public AssignedNutritionPlan getCurrentUserAssignedPlan() {
        users currentUser = userservice.getCurrentUserProfile();
        return assignedNutritionPlanRepo.findTopByUserOrderByAssignedAtDesc(currentUser)
                .orElseThrow(() -> new RuntimeException("No assigned nutrition plan found for you."));
    }
    // داخل NutritionService.java

    public List<nutritionplan> getAllAvailableTemplates() {
        return nutritionPlanRepository.findAll(); // أو تضيف فلتر إذا تبي خطط معينة فقط
    }
    // داخل NutritionService.java

    @Transactional
    public AssignedNutritionPlan updateAssignedNutritionPlanForUser(
            String userEmail,
            LocalDateTime newStartDate,
            LocalDateTime newEndDate,
            String newCoachNotes
    ) {
        // 1. البحث عن المستخدم باستخدام الإيميل
        users targetUser = userRepo.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + userEmail));

        // 2. البحث عن آخر تعيين نشط له
        AssignedNutritionPlan assignment = assignedNutritionPlanRepo
                .findTopByUserOrderByAssignedAtDesc(targetUser)
                .orElseThrow(() -> new RuntimeException("No active nutrition assignment found for user."));

        // 3. تعديل التواريخ والملاحظات فقط — لا نعدل القالب نفسه
        if (newStartDate != null) {
            assignment.setStartDate(newStartDate);
        }
        if (newEndDate != null) {
            assignment.setEndDate(newEndDate);
        }
        if (newCoachNotes != null) {
            assignment.setCoachNotes(newCoachNotes);
        }

        // 4. حفظ التعديلات
        return assignedNutritionPlanRepo.save(assignment);
    }
    // داخل NutritionService.java

    public AssignedNutritionPlan getAssignmentForUser(String userEmail) {
        // 1. البحث عن المستخدم باستخدام الإيميل
        users user = userRepo.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + userEmail));

        // 2. البحث عن آخر تعيين نشط له
        return assignedNutritionPlanRepo.findTopByUserOrderByAssignedAtDesc(user)
                .orElseThrow(() -> new RuntimeException("No nutrition assignment found for this user."));
    }
}