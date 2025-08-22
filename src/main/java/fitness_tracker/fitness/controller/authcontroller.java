package fitness_tracker.fitness.controller;


import fitness_tracker.fitness.dto.*;
import fitness_tracker.fitness.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class authcontroller {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<authresponse> register(@Valid @RequestBody register request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<authresponse> login(@Valid @RequestBody authrequest request) {
        return ResponseEntity.ok(authService.login(request));
    }
}
