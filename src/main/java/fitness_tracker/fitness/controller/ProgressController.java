package fitness_tracker.fitness.controller;

import fitness_tracker.fitness.model.progress;
import fitness_tracker.fitness.service.ProgressService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/progress")
@RequiredArgsConstructor
public class ProgressController {
    private ProgressService progressService;
    @PostMapping("/{userId}")
    public ResponseEntity<progress> createProgress(
            @PathVariable Long userId,
            @RequestBody progress prog) {
        return ResponseEntity.ok(progressService.createProgress(userId, prog));
    }

    @PutMapping("/{progressId}")
    public ResponseEntity<progress> updateProgress(
            @PathVariable Long progressId,
            @RequestBody progress updated) {
        return ResponseEntity.ok(progressService.updateProgress(progressId, updated));
    }

    @DeleteMapping("/{progressId}")
    public ResponseEntity<Void> deleteProgress(@PathVariable Long progressId) {
        progressService.deleteProgress(progressId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{progressId}")
    public ResponseEntity<progress> getProgressById(@PathVariable Long progressId) {
        return ResponseEntity.ok(progressService.getProgressById(progressId));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<progress>> getProgressByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(progressService.getProgressByUser(userId));
    }




}
