package fitness_tracker.fitness.controller;

import fitness_tracker.fitness.model.users;
import fitness_tracker.fitness.service.CoachService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/coach")
@RequiredArgsConstructor
public class CoachController {
    private final CoachService coachService;

    @GetMapping("/trainees")
    public ResponseEntity<List<users>> getAllTrainees() {
        return ResponseEntity.ok(coachService.getallusers());
    }



}
