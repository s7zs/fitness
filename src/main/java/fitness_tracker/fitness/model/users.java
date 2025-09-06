package fitness_tracker.fitness.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.*;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Table(name = "users")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(StringCleaningListener.class)
public class users implements UserDetails {


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "progress_seq")
    @SequenceGenerator(name = "progress_seq", sequenceName = "progress_sequence", allocationSize = 600)
    private long userid;

    private String name;

    @Column(unique = true)
    @Email(message = "email should be valid")
    private String email;


    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!?*()\\-_\\[\\]{}|;:,.<>]).{8,}$",
            message = "Password must contain at least one digit, one lowercase, one uppercase letter and one special character")
    private String password;

    // Optional fields - no validation constraints
    private String phonenumber;
    private String gender;
    private Integer age;
    private Float weight;
    private Float height;
    private String past_health_conditions;


    @Enumerated(EnumType.STRING)
    @JsonIgnore
    private ROLE userrole=ROLE.user;



    @PastOrPresent(message = "Start date must be in the past or present")
    private Date startdate;



    private String goal;

    //@NotNull(message = "Suspended status must be specified")
    private boolean issuspended;


    @OneToOne
    @JsonIgnore
    private progress progress;


    @OneToOne
    @JsonIgnore
    private workoutplan workout;
    @OneToOne
    @JsonIgnore
    private nutritionplan nutrition;
    @ManyToMany
    @JoinTable(
            name = "user_follow_coach",
            joinColumns = @JoinColumn(name = "userid"),
            inverseJoinColumns = @JoinColumn(name = "coachid")
    )
    @JsonIgnore
    private Set<Coach> followedCoaches = new HashSet<>();

    @OneToMany(mappedBy = "users")
    private List<LoginRegister> loginRegister;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Note> note= new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        if (userrole != null) {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + userrole.name().toUpperCase()));
        }
        return authorities;
    }



    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !this.issuspended;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    public String getDisplayUsername() {
        return this.name;
    }
    @Override
    public boolean isEnabled() {
        return !this.issuspended;
    }
}