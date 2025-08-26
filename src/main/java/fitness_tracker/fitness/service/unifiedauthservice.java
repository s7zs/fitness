package fitness_tracker.fitness.service;

import fitness_tracker.fitness.Repository.CoachRepo;
import fitness_tracker.fitness.Repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class unifiedauthservice implements UserDetailsService {
    private final UserRepo userRepo;
    private final CoachRepo coachRepo;

    @Autowired
    public unifiedauthservice(@Lazy UserRepo userRepo, @Lazy CoachRepo coachRepo) {
        this.userRepo = userRepo;
        this.coachRepo = coachRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepo.findByEmail(email)
                .map(u -> User.builder()
                        .username(u.getEmail())
                        .password(u.getPassword())
                        .authorities("ROLE_" + u.getUserrole().name().toUpperCase())
                        .build())
                .orElseGet(() -> coachRepo.findByEmail(email)
                        .map(c -> User.builder()
                                .username(c.getEmail())
                                .password(c.getPassword())
                                .authorities("ROLE_" + c.getUserrole().name().toUpperCase())
                                .build())
                        .orElseThrow(() -> new UsernameNotFoundException("User/Coach not found with email: " + email))
                );
    }
}