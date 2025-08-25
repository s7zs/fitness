package fitness_tracker.fitness.service;

import fitness_tracker.fitness.Repository.CoachRepo;
import fitness_tracker.fitness.Repository.NoteRepo;
import fitness_tracker.fitness.Repository.UserRepo;
import fitness_tracker.fitness.model.Coach;
import fitness_tracker.fitness.model.Note;
import fitness_tracker.fitness.model.users;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiceNote {
@Autowired
    private  UserRepo userRepo;
@Autowired
private  CoachRepo coachRepo;
@Autowired
    private NoteRepo noteRepo;

    public Note createNote(Long userId, Long coachId, Note note) {
        users trainee = userRepo.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id " + userId));

        Coach coach = coachRepo.findById(coachId)
                .orElseThrow(() -> new EntityNotFoundException("Coach not found with id " + coachId));

        note.setUsers(trainee);
        note.setCoach(coach);

        return noteRepo.save(note);
    }
    public Note updateNote(Long noteId, Note updatedNote) {
        Note existing = noteRepo.findById(noteId)
                .orElseThrow(() -> new EntityNotFoundException("Note not found with id " + noteId));

        existing.setDate(updatedNote.getDate());
        existing.setContent(updatedNote.getContent());

        return noteRepo.save(existing);
    }
    public void deleteNote(Long noteId) {
        if (!noteRepo.existsById(noteId)) {
            throw new EntityNotFoundException("Note not found with id " + noteId);
        }
        noteRepo.deleteById(noteId);
    }
    public List<Note> getNotesByUser(Long userId) {
        return noteRepo.findByUsers_Userid(userId);
    }
    public List<Note> getNotesByCoach(Long coachId) {
        return noteRepo.findByCoach_Coachid(coachId);
    }


    public Note getNoteById(Long noteId) {
        return noteRepo.findById(noteId)
                .orElseThrow(() -> new EntityNotFoundException("Note not found with id " + noteId));
    }

}
