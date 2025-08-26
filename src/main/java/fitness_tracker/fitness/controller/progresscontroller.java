package fitness_tracker.fitness.controller;

import fitness_tracker.fitness.model.progress;
import fitness_tracker.fitness.service.progressService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth/user/progress")
public class progresscontroller {

    private progressService progressService;

    @Autowired
    public progresscontroller(@Lazy progressService progressService) {
        this.progressService = progressService;
    }

    @PostMapping
    public ResponseEntity<progress> addProgress(@RequestBody @Valid progress progressRequest) {
        progress prog = progressService.addProgress(
                progressRequest.getWeightgoal(),
                progressRequest.getFatpercent(),
                progressRequest.getStartdate(),
                progressRequest.getEnddate()
        );
        return ResponseEntity.ok(prog);
    }

    @GetMapping
    public ResponseEntity<List<progress>> getMyProgress() {
        return ResponseEntity.ok(progressService.getMyProgress());
    }

}
