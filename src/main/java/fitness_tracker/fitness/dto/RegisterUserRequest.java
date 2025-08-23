package fitness_tracker.fitness.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Positive;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class register {

        @NotBlank(message = "Email is required")
        @Email(message = "Email should be valid")
        private String email;

        @NotBlank(message = "Password is required")
        @Size(min = 8, message = "Password must be at least 8 characters long")
        @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!?*()\\-_\\[\\]{}|;:,.<>]).{8,}$",
                message = "Password must contain at least one digit, one lowercase, one uppercase letter and one special character")
        private String password;

        @NotBlank(message = "Phone number is required")
        @Pattern(regexp = "^[+]?[(]?[0-9]{3}[)]?[-\\s.]?[0-9]{3}[-\\s.]?[0-9]{4,6}$",
                message = "Invalid phone number format")
        private String phonenumber;

        @NotNull(message = "Gender is required")
        @Pattern(regexp = "^[MF]$", message = "Gender must be 'M' or 'F'")
        private String gender;

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

        private Date startdate = new Date();
}
