package fitness_tracker.fitness.service;

import fitness_tracker.fitness.Repository.CoachRepo;
import fitness_tracker.fitness.Repository.UserRepo;
import fitness_tracker.fitness.model.Coach;
import fitness_tracker.fitness.model.users;
import fitness_tracker.fitness.secuirty.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CoachService {
@Autowired
private CoachRepo coachRepo;
@Autowired
private SecurityUtils securityUtils;
@Autowired
private UserRepo userRepo;


public List<users> getallusers (){
    Long coachid = securityUtils.getCurrentUserId();
    return userRepo.findByCoach_Coachid(coachid);
}

}
