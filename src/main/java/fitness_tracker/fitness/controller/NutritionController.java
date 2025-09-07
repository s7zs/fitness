// fitness_tracker.fitness.controller.NutritionController.java

package fitness_tracker.fitness.controller;

import fitness_tracker.fitness.dto.AssignNutritionRequest;
import fitness_tracker.fitness.dto.UpdateAssignmentRequest;
import fitness_tracker.fitness.model.AssignedNutritionPlan;
import fitness_tracker.fitness.model.nutritionplan;
import fitness_tracker.fitness.service.NutritionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/nutrition")
public class NutritionController {

    @Autowired
    private NutritionService nutritionService;

    // 👉 1. [للجميع] عرض جميع الخطط المسبقة (Templates)
    @GetMapping("/templates")
    public ResponseEntity<List<nutritionplan>> getAllTemplates() {
        List<nutritionplan> templates = nutritionService.getAllAvailableTemplates();
        return ResponseEntity.ok(templates);
    }

    // 👉 2. [للمدرب فقط] تعيين خطة لمستخدم
    @PreAuthorize("hasRole('COACH')")
    @PostMapping("/assign")
    public ResponseEntity<AssignedNutritionPlan> assignNutritionPlan(@RequestBody AssignNutritionRequest request) {
        // هنستخدم getCurrentUserProfile من CoachService عشان نعرف مين المدرب الحالي
        // لكن بما أن nutritionService لا يملكه، نحتاج إما نمرره من الـ Controller أو نحقنه
        // هنا افترض أننا أضفنا getCurrentCoachId() في NutritionService — أو نستخدم SecurityContextHolder

        // ✅ بديل سريع: نستخدم getCurrentUserProfile من CoachService
        // لكن عشان ما نعدل كثير، هنستخدم طريقة مباشرة مؤقتًا — ويمكنك تحسينها لاحقًا

        Long coachId = 1L; // ⚠️ مؤقت — لازم تستبدلها بـ ID المدرب الحالي من التوكن

        AssignedNutritionPlan assignedPlan = nutritionService.assignNutritionPlanToUser(
                coachId,
                request.getUserEmail(),
                request.getNutritionPlanId(),
                request.getStartDate(),
                request.getEndDate(),
                request.getCoachNotes()
        );

        return ResponseEntity.ok(assignedPlan);
    }

    // 👉 3. [للمدرب أو المستخدم] تعديل آخر تعيين للمستخدم
    @PreAuthorize("hasAnyRole('COACH', 'USER')")
    @PutMapping("/assignment/user/{userId}")
    public ResponseEntity<AssignedNutritionPlan> updateAssignmentForUser(
            @PathVariable String userEmail,
            @RequestBody UpdateAssignmentRequest request) {

        AssignedNutritionPlan updated = nutritionService.updateAssignedNutritionPlanForUser(
                userEmail,
                request.getStartDate(),
                request.getEndDate(),
                request.getCoachNotes()
        );

        return ResponseEntity.ok(updated);
    }

    // 👉 4. [للمستخدم] عرض آخر خطة مُعيّنة له
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/my-assignment")
    public ResponseEntity<AssignedNutritionPlan> getCurrentUserAssignment() {
        AssignedNutritionPlan assignment = nutritionService.getCurrentUserAssignedPlan();
        return ResponseEntity.ok(assignment);
    }

    // 👉 5. [للمدرب] عرض آخر خطة مُعيّنة لمستخدم معين
    @PreAuthorize("hasRole('COACH')")
    @GetMapping("/assignment/user/{userId}")
    public ResponseEntity<AssignedNutritionPlan> getAssignmentForUser(@PathVariable String userEmail) {
        AssignedNutritionPlan assignment = nutritionService.getAssignmentForUser(userEmail);
        return ResponseEntity.ok(assignment);
    }
}