package fitness_tracker.fitness.service;

import fitness_tracker.fitness.dto.*;
import fitness_tracker.fitness.model.Coach;
import fitness_tracker.fitness.model.ROLE;
import fitness_tracker.fitness.model.users;
import fitness_tracker.fitness.Repository.UserRepo;
import fitness_tracker.fitness.Repository.CoachRepo;
import fitness_tracker.fitness.secuirty.jwt.jwtservice;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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
    
    public authresponse register(register request) {
        if (userRepo.ExistByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        var user = new users();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setPhonenumber(request.getPhonenumber());
        user.setGender(request.getGender());
        user.setAge(request.getAge());
        user.setWeight(request.getWeight());
        user.setHeight(request.getHeight());
        user.setPast_health_conditions(request.getPast_health_conditions());
        user.setUserrole(ROLE.user);
        user.setIsmember(true);
        user.setStartdate(new Date());
        
        user.setIssuspended(false);

        userRepo.save(user);
        
        String token = jwtService.generateToken(user);
        return authresponse.builder()
                .token(token)
                .role(ROLE.user.name())
                .build();
    }

 public authresponse login(authrequest request) {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                request.getEmail(),
                request.getPassword()
            )
        );

        var user = userRepo.FindByEmail(request.getEmail())
                .orElseGet(() -> {
                    var coach = coachRepo.FindByEmail(request.getEmail())
                            .stream()
                            .findFirst()
                            .orElseThrow(() -> new RuntimeException("User not found"));
                    return null;
                });

        String role;
        String token;
        
        if (user != null) {
            if (user.isIssuspended()) {
                throw new RuntimeException("Account is suspended");
            }
            token = jwtService.generateToken(user);
            role = user.getUserrole().name();
        } else {
            var coach = coachRepo.FindByEmail(request.getEmail()).get(0);
            if (coach.isIssusbended()) {
                throw new RuntimeException("Account is suspended");
            }
            token = jwtService.generateToken(coach);
            role = "COACH";
        }

        return authresponse.builder()
                .token(token)
                .role(role)
                .build();
    }

}
