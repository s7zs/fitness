package fitness_tracker.fitness.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateAssignmentRequest {
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String coachNotes;
}
