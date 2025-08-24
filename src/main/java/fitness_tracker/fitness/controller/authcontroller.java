package fitness_tracker.fitness.controller;


import fitness_tracker.fitness.dto.*;
import fitness_tracker.fitness.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Primary;

import org.springframework.context.annotation.Lazy;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")

public class authcontroller {


    @Autowired
    @Lazy

    private final AuthService authService;

    @Autowired
    public authcontroller(@Lazy AuthService authService) {
        this.authService = authService;
    }

    @GetMapping()
    public String hello (){
        return "hello";
    }

    @PostMapping("/register")
    public ResponseEntity<authresponse> register(@Valid @RequestBody register request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<authresponse> login(@Valid @RequestBody authrequest request) {
        return ResponseEntity.ok(authService.login(request));
    }
}
