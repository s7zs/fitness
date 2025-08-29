package fitness_tracker.fitness.service;

import fitness_tracker.fitness.dto.*;
import fitness_tracker.fitness.model.users;
import fitness_tracker.fitness.Repository.UserRepo;
import fitness_tracker.fitness.Repository.CoachRepo;
import fitness_tracker.fitness.secuirty.jwt.jwtservice;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepo userRepo;
    private final CoachRepo coachRepo;
    private final AuthenticationManager authenticationManager;
    private final jwtservice jwtService;
    private final PasswordEncoder passwordEncoder;

    public String addUser(users userInfo) {
        userInfo.setPassword(passwordEncoder.encode(userInfo.getPassword()));
        userRepo.save(userInfo);
        return "User added successfully!";
    }
    public authresponse login(authrequest request) {
        // Authenticate the user
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );
        } catch (Exception e) {
            throw new RuntimeException("Invalid email or password");
        }

        // Find the user
        users user = userRepo.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Check if user is suspended
        if (user.isIssuspended()) {
            throw new RuntimeException("Account is suspended");
        }
        // Generate token
        String token = jwtService.generateToken(user);

        return authresponse.builder()
                .token(token)

                .role(user.getUserrole().name())
                .message("Welcome!") // Add welcome message
                .build();
    }
}