package fitness_tracker.fitness.Repository;

import fitness_tracker.fitness.model.users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<users, Long> {
    public Optional<users> findByEmail(String email);
    boolean existsByEmail(String email);


    @Query(
            nativeQuery = true,
            value = "INSERT INTO users (email, password) VALUES (:email, :password)"
    )
    void registerNewUser(@Param("email") String email, @Param("password") String password);
}
