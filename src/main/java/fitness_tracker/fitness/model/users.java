package fitness_tracker.fitness.model;

import com.fasterxml.jackson.databind.ser.std.StdKeySerializers;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import fitness_tracker.fitness.model.ROLE;
import java.util.Date;
import java.util.List;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class users implements UserDetails{

        @Id
        @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "progress_seq")
        @SequenceGenerator(name = "progress_seq", sequenceName = "progress_sequence", allocationSize = 600)
        private long userid;

        @Email(message = "email should be valid")
        private String email;

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
        private float weight;

        @Positive(message = "Height must be positive")
        @Max(value = 250, message = "Height must be less than 250 cm")
        private float height;

        @Size(max = 500, message = "Past health conditions cannot exceed 500 characters")
        private String past_health_conditions;

        @NotNull(message = "User role is required")
        @Enumerated(EnumType.STRING)
        private ROLE userrole;

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

        @Valid
        @OneToOne
        private  workoutplan workout;

        @Valid
        @OneToOne
        private nutritionplan nutrition;

        @ManyToOne
        @JoinColumn(name = "coach_id")
        private Coach coach;
        @OneToMany(mappedBy = "users_login")
        private List< LoginRegister> loginRegister;
        @OneToMany(mappedBy = "users_note")
        private List <Note >note;
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Return the authorities for the user
        return null; // Replace with actual implementation
    }

    
    @Override
    public String getPassword() {
        return this.password; // Replace with actual password field
    }

    @Override
    public String getUsername() {
        return this.email; // Replace with actual username field
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // Adjust logic as needed
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // Adjust logic as needed
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Adjust logic as needed
    }

    @Override
    public boolean isEnabled() {
        return !this.isIssuspended(); // Adjust logic as needed
    }
}



