package fitness_tracker.fitness.secuirty;

import fitness_tracker.fitness.Repository.UserRepo;
import fitness_tracker.fitness.model.users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class SecurityUtils {

    @Autowired
    private UserRepo userRepository;

    public users getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            String username = ((UserDetails) principal).getUsername();
            return userRepository.findByEmail(username)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + username));
        } else {
            throw new IllegalStateException("Principal is not an instance of UserDetails");
        }
    }

    public Long getCurrentUserId() {
        return getCurrentUser().getUserid();
    }
}