package fitness_tracker.fitness.service;

import fitness_tracker.fitness.Repository.CoachRepo;
import fitness_tracker.fitness.Repository.UserRepo;
import fitness_tracker.fitness.dto.setuserinfo;
import fitness_tracker.fitness.model.Coach;
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

import java.util.ArrayList;
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

    public users updateUserProfile( setuserinfo dto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String emaill = authentication.getName();
        users user = userRepo.findByEmail(emaill)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        user.setPhonenumber(dto.getPhonnumber());
        user.setGender(dto.getGender());
        user.setAge(dto.getAge());
        user.setWeight(dto.getWeight());
        user.setHeight(dto.getHeight());
        user.setPast_health_conditions(dto.getPast_conditions());
        user.setGoal(dto.getGoal());

        return userRepo.save(user);
    }


    public List<users> viewUsers() {
        return userRepo.findAll();
    }
    public List<Coach> getAllCoaches() {
        return coachRepo.findAll();
    }

    public Optional<Coach> searchCoachesByUsername(String username) {
        return coachRepo.findByName(username);
    }

    public String followCoach(String name) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        users user = userRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));

        Coach coach = coachRepo.findByName(name)
                .orElseThrow(() -> new RuntimeException("Coach not found with ID: " + name));


        user.getFollowedCoaches().add(coach);
        userRepo.save(user);


        coach.getFollowers().add(user);
        coachRepo.save(coach);
        return "coach followed";
    }

    public String unfollowCoach(String name ) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        users user = userRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));

        Coach coach = coachRepo.findByName(name)
                .orElseThrow(() -> new RuntimeException("Coach not found with ID: " + name));


        user.getFollowedCoaches().remove(coach);
        userRepo.save(user);


        coach.getFollowers().remove(user);
        coachRepo.save(coach);
        return "coach unfollowed";
    }

    public List<Coach> getFollowedCoaches() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        users user = userRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));

        return new ArrayList<>(user.getFollowedCoaches());
    }

}