package fitness_tracker.fitness.controller;

import fitness_tracker.fitness.Repository.CoachRepo;
import fitness_tracker.fitness.model.users;
import fitness_tracker.fitness.service.coachservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/auth/coach")
public class coachcontroller {

  private final coachservice coachservice;

    public coachcontroller(coachservice coachservice) {
        this.coachservice = coachservice;
    }

    @GetMapping("/followers")
    public ResponseEntity<List<users>> getFollowers() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(401).body(null);
        }
        return ResponseEntity.ok(coachservice.getFollowers());
    }

    @GetMapping("/followers/count")
    public ResponseEntity<Map<String, Integer>> getFollowerCount() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(401).body(null);
        }
        return ResponseEntity.ok(Map.of("followers", coachservice.getFollowerCount()));
    }
}
