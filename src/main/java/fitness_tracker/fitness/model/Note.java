package fitness_tracker.fitness.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Calendar;
import java.util.Date;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Note {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long noteid;

    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date date;

    @Size(min = 10, max = 500, message = "Note content must be between 10 and 500 characters")
    private String content;


    @ManyToOne
    @JoinColumn(name = " user_id")
    @JsonBackReference("user-notes")
    private users user;
    @ManyToOne
    @JoinColumn(name = " coach_id")
    @JsonBackReference("coach-notes")
    private Coach coach;
    @PrePersist
    protected void onCreate() {
        this.date = new Date(); // today
    }
}