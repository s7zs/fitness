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

public class progresscontroller {

    private progressService progressService;

    @Autowired
    public progresscontroller(@Lazy progressService progressService) {
        this.progressService = progressService;
    }

    @PostMapping("/auth/user/progress")
    public ResponseEntity<progress> addProgress(@RequestBody @Valid progress progressRequest) {
        progress prog = progressService.addProgressU(
                progressRequest.getWeightgoal(),
                progressRequest.getFatpercent(),
                progressRequest.getStartdate(),
                progressRequest.getEnddate()
        );
        return ResponseEntity.ok(prog);
    }

    @GetMapping("/auth/user/progress")
    public ResponseEntity<List<progress>> getMyProgress() {
        return ResponseEntity.ok(progressService.getMyProgressU());
    }

    @PostMapping("/auth/coach/progress")
    public ResponseEntity<progress> addcoachprogress(@RequestBody @Valid progress progressRequest) {
        progress prog = progressService.addProgressC(
                progressRequest.getWeightgoal(),
                progressRequest.getFatpercent(),
                progressRequest.getStartdate(),
                progressRequest.getEnddate()
        );
        return ResponseEntity.ok(prog);
    }

    @GetMapping("/auth/coach/progress")
    public ResponseEntity<List<progress>> getcoachProgress() {
        return ResponseEntity.ok(progressService.getMyProgressC());
    }


}
