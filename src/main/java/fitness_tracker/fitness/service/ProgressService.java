package fitness_tracker.fitness.service;

import fitness_tracker.fitness.Repository.ProgressRepo;
import fitness_tracker.fitness.Repository.UserRepo;
import fitness_tracker.fitness.model.progress;
import fitness_tracker.fitness.model.users;
import fitness_tracker.fitness.secuirty.SecurityUtils;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProgressService {
    @Autowired
    private ProgressRepo progressRepository;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private SecurityUtils securityUtils;

    public progress createProgress(Long userId, progress prog) {
        users trainee = userRepo.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id " + userId));

        prog.setUser(trainee);

        return progressRepository.save(prog);
    }
    public progress updateProgress(Long progressId, progress updated) {
        progress existing = progressRepository.findById(progressId)
                .orElseThrow(() -> new EntityNotFoundException("Progress not found with id " + progressId));
        existing.setWeightgoal(updated.getWeightgoal());
        existing.setFatpercent(updated.getFatpercent());
        existing.setStartdate(updated.getStartdate());
        existing.setEnddate(updated.getEnddate());
        return progressRepository.save(existing);
    }
    public void deleteProgress(Long progressId) {
        if (!progressRepository.existsById(progressId)) {
            throw new EntityNotFoundException("Progress not found with id " + progressId);
        }
        progressRepository.deleteById(progressId);
    }


    public progress getProgressById(Long progressId) {
        return progressRepository.findById(progressId)
                .orElseThrow(() -> new EntityNotFoundException("Progress not found with id " + progressId));
    }

         public List<progress> getProgressByUser(Long userId) {
        return progressRepository.findByUser_Userid(userId);
    }
}
