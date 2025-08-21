package fitness_tracker.fitness.secuirty;


import java.security.AuthProvider;

import fitness_tracker.fitness.service.userservice;
import org.springframework.security.authentication.AuthenticationProvider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import fitness_tracker.fitness.secuirty.jwt.jwtauth;

@Configuration
@EnableWebSecurity
public class websecuirtyconfig   {
    private final jwtauth jwtauth;
    private final userservice userservice; // Changed type name
    
    @Autowired
    public websecuirtyconfig(jwtauth jwtauth,userservice userservice) { // Changed parameter type
        this.jwtauth = jwtauth;
      this.userservice = userservice;
    }

    @Bean // Add @Bean annotation
    public SecurityFilterChain appsecuirty(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
            .requestMatchers("/api/auth/**").permitAll()
            // User endpoints
            .requestMatchers("/api/user/**").hasRole("user")
            .requestMatchers("/api/workout/view/**").hasAnyRole("user", "coach")
            .requestMatchers("/api/nutrition/view/**").hasAnyRole("user", "coach")
            // Coach endpoints
            .requestMatchers("/api/coach/**").hasRole("coach")
            .requestMatchers("/api/workout/manage/**").hasRole("coach")
            .requestMatchers("/api/nutrition/manage/**").hasRole("coach")
            // Admin endpoints
            .requestMatchers("/api/admin/**").hasRole("admin")
            .anyRequest().authenticated()
            )
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
            .authenticationProvider(authenticationProvider()) // Use corrected field name
            .addFilterBefore(jwtauth, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    private AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userservice);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
