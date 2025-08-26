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
public class Notecontroller {
    private  Noteservice noteService;

    @Autowired
    public Notecontroller(@Lazy Noteservice noteService) {
        this.noteService = noteService;
    }

    @PostMapping("/auth/user/notes")
    public ResponseEntity<Note> addNoteu(@RequestBody @Valid Note noteRequest) {
        Note note = noteService.addNoteuser(noteRequest.getContent(), noteRequest.getDate());
        return ResponseEntity.ok(note);
    }

    @GetMapping("/auth/user/notes")
    public ResponseEntity<List<Note>> getMyNotesu() {
        return ResponseEntity.ok(noteService.getMyNotesuser());
    }

    @PostMapping("/auth/coach/notes")
    public ResponseEntity<Note> addNotec(@RequestBody @Valid Note noteRequest) {
        Note note = noteService.addNotecoach(noteRequest.getContent(), noteRequest.getDate());
        return ResponseEntity.ok(note);
    }

    @GetMapping("/auth/coach/notes")
    public ResponseEntity<List<Note>> getMyNotesc() {
        return ResponseEntity.ok(noteService.getMyNotecoach());
    }



}
