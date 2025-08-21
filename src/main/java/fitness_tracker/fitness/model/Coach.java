package fitness_tracker.fitness.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.Collection;
@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Coach implements UserDetails {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private long coachid;

    @NotBlank(message = "Username is required")
    @Email
private String  email;

    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$", message = "Password must contain at least one digit, one lowercase, one uppercase letter and one special character")
private String  password;

    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^[+]?[(]?[0-9]{3}[)]?[-\\s.]?[0-9]{3}[-\\s.]?[0-9]{4,6}$", message = "Invalid phone number format")
private  String phonenumber;

@Size(max = 1)
private char gender;
@Min(value = 20)
private int age;


private List<String> experince;

    @Enumerated(EnumType.STRING)
    @Column( nullable = false)
    private ROLE userrole = ROLE.coach; // default value

private boolean issusbended;

@OneToMany(mappedBy = "coach")
private List<users> users;
@OneToMany(mappedBy = "coach")
private List<LoginRegister> loginRegister;

@OneToMany(mappedBy = "coach")
private List<Note> note;

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
        return true; // Adjust logic as needed
    }
}
