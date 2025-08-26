package fitness_tracker.fitness.controller;

import fitness_tracker.fitness.model.Note;
import fitness_tracker.fitness.service.Noteservice;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth/user/notes")
public class Notecontroller {
    private  Noteservice noteService;

    @Autowired
    public Notecontroller(@Lazy Noteservice noteService) {
        this.noteService = noteService;
    }

    @PostMapping
    public ResponseEntity<Note> addNote(@RequestBody @Valid Note noteRequest) {
        Note note = noteService.addNote(noteRequest.getContent(), noteRequest.getDate());
        return ResponseEntity.ok(note);
    }

    @GetMapping
    public ResponseEntity<List<Note>> getMyNotes() {
        return ResponseEntity.ok(noteService.getMyNotes());
    }
}
