package fitness_tracker.fitness.service;

import fitness_tracker.fitness.Repository.NoteRepo;
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


    @Autowired
    public Noteservice(@Lazy NoteRepo noteRepo,@Lazy userservice userService) {
        this.noteRepo = noteRepo;
        this.userService = userService;
    }

    public Note addNote(String content, Date date) {
        users currentUser = userService.getCurrentUserProfile();
        Note note = new Note();
        note.setContent(content);
        note.setDate(date);
        note.setUsers(currentUser);
        return noteRepo.save(note);
    }

    public List<Note> getMyNotes() {
        users currentUser = userService.getCurrentUserProfile();
        return noteRepo.findByUsers(currentUser);
    }
}
