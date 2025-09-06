package fitness_tracker.fitness.controller;


import fitness_tracker.fitness.dto.*;
import fitness_tracker.fitness.model.Coach;
import fitness_tracker.fitness.model.users;
import fitness_tracker.fitness.service.AuthService;
import fitness_tracker.fitness.service.coachservice;
import fitness_tracker.fitness.service.loginregister;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class authcontroller {

    private final AuthService authService;
    private final loginregister registerService;
    private final AuthenticationManager authenticationManager;
    private final coachservice coachservice;

   @Autowired
    public authcontroller(@Lazy AuthService authService, @Lazy loginregister registerService, @Lazy AuthenticationManager authenticationManager,@Lazy coachservice coachservice) {
        this.authService = authService;
        this.registerService = registerService;
        this.authenticationManager = authenticationManager;

        this.coachservice = coachservice;
    }

    @GetMapping()
    public String hello (){
        return "hello";
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody users user) {
        return ResponseEntity.ok(authService.addUser(user));
    }

    @PostMapping("/login")
    public ResponseEntity<authresponse> login(@Valid @RequestBody authrequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/registercoach")
    public ResponseEntity<String> registercoach(@Valid @RequestBody Coach request) {
        return ResponseEntity.ok(coachservice.registerCoach(request));
    }

    @PostMapping("/logincoach")
    public ResponseEntity<authresponse> logincoach(@Valid @RequestBody authrequest request) {
        return ResponseEntity.ok(coachservice.login(request));
    }



}