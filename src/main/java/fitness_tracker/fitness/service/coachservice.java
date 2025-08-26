package fitness_tracker.fitness.service;

import fitness_tracker.fitness.Repository.CoachRepo;
import fitness_tracker.fitness.Repository.UserRepo;
import fitness_tracker.fitness.dto.authrequest;
import fitness_tracker.fitness.dto.authresponse;
import fitness_tracker.fitness.model.Coach;
import fitness_tracker.fitness.model.users;
import fitness_tracker.fitness.secuirty.jwt.jwtservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class coachservice implements UserDetailsService{
    private final CoachRepo coachRepo;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final jwtservice jwtservice;

    @Autowired
    public coachservice(@Lazy CoachRepo coachRepo, @Lazy PasswordEncoder passwordEncoder,@Lazy AuthenticationManager authenticationManager,@Lazy jwtservice jwtservice) {
        this.coachRepo = coachRepo;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtservice = jwtservice;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Coach> userInfo = coachRepo.findByEmail(email );

        if (userInfo.isEmpty()) {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }

        Coach coach = userInfo.get();
        return User.builder()
                .username(coach.getEmail())
                .password(coach.getPassword())
                .authorities("ROLE_" + coach.getUserrole().name().toUpperCase()) // assuming roles is already a collection or string[]
                .build();
    }

    public String registerCoach(Coach coach ) {
        coach.setPassword(passwordEncoder.encode(coach.getPassword()));
        coachRepo.save(coach);
        return "coach added successfully!";
    }


    public authresponse login(authrequest request) {

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


        Coach coach = coachRepo.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("coach not found"));


        if (coach.isIssuspended()) {
            throw new RuntimeException("Account is suspended");
        }

        String token = jwtservice.generateToken(coach);

        return authresponse.builder()
                .token(token)

                .role(coach.getUserrole().name())
                .message("Welcome!") // Add welcome message
                .build();
    }
}
