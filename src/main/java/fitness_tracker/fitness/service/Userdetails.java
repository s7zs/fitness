package fitness_tracker.fitness.service;

import fitness_tracker.fitness.model.users;
import fitness_tracker.fitness.model.Coach;
import fitness_tracker.fitness.Repository.UserRepo;
import fitness_tracker.fitness.Repository.CoachRepo;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class Userdetails implements UserDetailsService {

    private final UserRepo userRepo;
    private final CoachRepo coachRepo;

    @Autowired
    public Userdetails(UserRepo userRepo, CoachRepo coachRepo) {
        this.userRepo = userRepo;
        this.coachRepo = coachRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // Try to find user
        List<Coach> coaches = new ArrayList<>();
        users user = userRepo.FindByEmail(email)
            .orElseGet(() -> {
                // If not found as user, try to find as coach
                coaches.addAll(coachRepo.FindByEmail(email));
                if (coaches.isEmpty()) {
                    throw new UsernameNotFoundException("User not found: " + email);
                }
                return null;
            });

        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        
        if (user != null) {
            // Add user role
            authorities.add(new SimpleGrantedAuthority("ROLE_" + user.getUserrole().name().toUpperCase()));
        } else {
            // Add coach role
            authorities.add(new SimpleGrantedAuthority("ROLE_COACH"));
        }

        return new User(email, 
            user != null ? user.getPassword() : (coaches.isEmpty() ? null : coaches.get(0).getPassword()) ,
            true, true, true, user != null && !user.isIssuspended(),
            authorities);
    }
}
