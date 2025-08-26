package fitness_tracker.fitness.service;

import fitness_tracker.fitness.Repository.CoachRepo;
import fitness_tracker.fitness.Repository.UserRepo;
import fitness_tracker.fitness.model.users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class userservice implements UserDetailsService {
    private final UserRepo userRepo;
    private final CoachRepo coachRepo;

    @Autowired
    public userservice(@Lazy  UserRepo userRepo, @Lazy CoachRepo coachRepo) {
        this.userRepo = userRepo;
        this.coachRepo = coachRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<users> userInfo = userRepo.findByEmail(email );

        if (userInfo.isEmpty()) {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }

        users user = userInfo.get();
        return User.builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .authorities("ROLE_" + user.getUserrole().name().toUpperCase()) // assuming roles is already a collection or string[]
                .build();
    }
    public users getCurrentUserProfile() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        return userRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));
    }
    public List<users> viewUsers() {
        return userRepo.findAll();
    }

}