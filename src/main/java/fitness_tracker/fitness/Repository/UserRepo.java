package fitness_tracker.fitness.Repository;

import fitness_tracker.fitness.model.users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<users,Long> {
    @Query(nativeQuery = true , value = "SELECT * FROM USERS")
    Optional<users>FindByUsername(String username);
    @Query(nativeQuery = true , value = "SELECT * FROM USERS")
    Optional<users>FindByEmail(String email);
    @Query(nativeQuery = true , value = "SELECT * FROM USERS")
    boolean existsByUsername( String username);
    @Query(nativeQuery = true , value = "SELECT * FROM USERS")
     boolean ExistByEmail(String email);
    @Query(nativeQuery = true , value = "SELECT * FROM USERS")
    Optional<users>  ExistsByEmail(String email);



}
