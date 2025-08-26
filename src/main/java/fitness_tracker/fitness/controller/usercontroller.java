
package fitness_tracker.fitness.controller;


import fitness_tracker.fitness.model.users;
import fitness_tracker.fitness.service.userservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/auth/user")
public class usercontroller {

    @Autowired
    private userservice userservice;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public usercontroller( @Lazy AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @GetMapping("/AllUsers")
    public List<users> view() {
        return userservice.viewUsers();
    }

    @GetMapping("/profile")
    public ResponseEntity<users> getProfile() {
        return ResponseEntity.ok(userservice.getCurrentUserProfile());
    }
}
