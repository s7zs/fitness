package fitness_tracker.fitness.service;

import fitness_tracker.fitness.Repository.NoteRepo;
import fitness_tracker.fitness.model.Coach;
import fitness_tracker.fitness.model.Note;
import fitness_tracker.fitness.model.users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class Noteservice {
    private final  NoteRepo noteRepo;
    private  final userservice userService;
    private final coachservice coachService;


    @Autowired
    public Noteservice(@Lazy NoteRepo noteRepo, @Lazy userservice userService,@Lazy coachservice coachService) {
        this.noteRepo = noteRepo;
        this.userService = userService;
        this.coachService = coachService;
    }

    public Note addNoteuser(String content, Date date) {
        users currentUser = userService.getCurrentUserProfile();
        Note note = new Note();
        note.setContent(content);
        note.setDate(date);
        note.setUser(currentUser);
        return noteRepo.save(note);
    }

    public List<Note> getMyNotesuser() {
        users currentUser = userService.getCurrentUserProfile();
        return noteRepo.findByUser(currentUser);
    }

    public Note addNotecoach(String content, Date date) {
        Coach currentUser = coachService.getCurrentUserProfile();
        Note note = new Note();
        note.setContent(content);
        note.setDate(date);
        note.setCoach(currentUser);
        return noteRepo.save(note);
    }

    public List<Note> getMyNotecoach() {
        Coach currentUser = coachService.getCurrentUserProfile();
        return noteRepo.findByCoach(currentUser);
    }


}
