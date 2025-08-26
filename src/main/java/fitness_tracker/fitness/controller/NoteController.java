package fitness_tracker.fitness.controller;

import fitness_tracker.fitness.model.Note;
import fitness_tracker.fitness.service.ServiceNote;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notes")
@RequiredArgsConstructor
public class NoteController {
    private ServiceNote serviceNote;

    @PostMapping("/user/{userId}/coach/{coachId}")
    public ResponseEntity<Note> createNote(
            @PathVariable Long userId,
            @PathVariable Long coachId,
            @RequestBody Note note) {
        return ResponseEntity.ok(serviceNote.createNote(userId, coachId, note));
    }

    @PutMapping("/{noteId}")
    public ResponseEntity<Note> updateNote(
            @PathVariable Long noteId,
            @RequestBody Note updatedNote) {
        return ResponseEntity.ok(serviceNote.updateNote(noteId, updatedNote));
    }

    @DeleteMapping("/{noteId}")
    public ResponseEntity<Void> deleteNote(@PathVariable Long noteId) {
        serviceNote.deleteNote(noteId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Note>> getNotesByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(serviceNote.getNotesByUser(userId));
    }
    @GetMapping("/coach/{coachId}")
    public ResponseEntity<List<Note>> getNotesByCoach(@PathVariable Long coachId) {
        return ResponseEntity.ok(serviceNote.getNotesByCoach(coachId));
    }
    @GetMapping("/{noteId}")
    public ResponseEntity<Note> getNoteById(@PathVariable Long noteId) {
        return ResponseEntity.ok(serviceNote.getNoteById(noteId));
    }
}
