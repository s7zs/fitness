package fitness_tracker.fitness.model;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class users {

        @Id
        @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "progress_seq")
        @SequenceGenerator(name = "progress_seq", sequenceName = "progress_sequence", allocationSize = 600)
        private long id;

        @NotBlank(message = "Username is required")
        @Size(min = 3, max = 20, message = "Username must be between 3 and 20 characters")
        private String username;

        @NotBlank(message = "Password is required")
        @Size(min = 8, message = "Password must be at least 8 characters long")
        @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$",
                message = "Password must contain at least one digit, one lowercase, one uppercase letter and one special character")
        private String password;

        @NotBlank(message = "Phone number is required")
        @Pattern(regexp = "^[+]?[(]?[0-9]{3}[)]?[-\\s.]?[0-9]{3}[-\\s.]?[0-9]{4,6}$",
                message = "Invalid phone number format")
        private String phonenumber;

        @NotNull(message = "Gender is required")
        @Pattern(regexp = "[MF]", message = "Gender must be 'M' or 'F'")
        private char gender;

        @Min(value = 12, message = "Age must be at least 12")
        @Max(value = 120, message = "Age must be less than 120")
        private int age;

        @Positive(message = "Weight must be positive")
        @Max(value = 500, message = "Weight must be less than 500 kg")
        private float weight;  // Fixed typo in field name (was 'wight')

        @Positive(message = "Height must be positive")
        @Max(value = 250, message = "Height must be less than 250 cm")
        private float height;  // Fixed typo in field name (was 'hight')

        @Size(max = 500, message = "Past health conditions cannot exceed 500 characters")
        private String past_health_conditions;

        @NotNull(message = "User role is required")
        @Pattern(regexp = "[AT]", message = "User role must be 'A' (admin) or 'T' (trainer)")
        private char userrole;

        @NotNull(message = "Membership status must be specified")
        private boolean ismember;

        @PastOrPresent(message = "Start date must be in the past or present")
        private Date startdate;

        @FutureOrPresent(message = "End date must be in the future or present")
        private Date enddate;

        @NotBlank(message = "Goal is required")
        @Size(max = 200, message = "Goal cannot exceed 200 characters")
        private String goal;

        @NotNull(message = "Suspended status must be specified")
        private boolean issuspended;  // Fixed typo in field name (was 'issusbended')

        @Valid
        @OneToOne
        private progress progress;
    }



