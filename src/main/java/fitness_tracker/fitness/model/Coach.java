package fitness_tracker.fitness.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Coach implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long coachid;

    private String name;

    @NotBlank(message = "Username is required")
    @Email
    private String  email;

    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    private String password;

    //@NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^[+]?[(]?[0-9]{3}[)]?[-\\s.]?[0-9]{3}[-\\s.]?[0-9]{4,6}$", message = "Invalid phone number format")
    private  String phonenumber;



    private String gender;

    private int age;


    private List<String> experince;

    @Enumerated(EnumType.STRING)
    @Column( nullable = false)
    private ROLE userrole = ROLE.coach; // default value

    private boolean issusbended;

    @ManyToMany(mappedBy = "followedCoaches")
    @JsonIgnore
    private Set<users> followers = new HashSet<>();


    @OneToMany(mappedBy = "coach")
    private List<LoginRegister> loginRegister;

    @OneToMany(mappedBy = "coach")
    @JsonIgnore
    private List<Note> note;

   @OneToOne
   private progress progress;

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
        return !this.issusbended;
    }

    // Add missing getter for the issuspended field
    public boolean isIssuspended() {
        return this.issusbended;
    }
    public String getDisplayUsername() {
        return this.name;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        if (userrole != null) {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + userrole.name().toUpperCase()));
        }
        return authorities;
    }
}
