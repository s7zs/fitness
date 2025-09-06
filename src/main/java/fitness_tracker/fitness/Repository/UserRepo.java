package fitness_tracker.fitness.Repository;

import fitness_tracker.fitness.model.Coach;
import fitness_tracker.fitness.model.users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<users, Long> {
    //public
    Optional<users> findByEmail(String email);
    boolean existsByEmail(String email);
    Optional<users> findByName(String username);

   // @Query(
     //      nativeQuery = true,
       //    value = "INSERT INTO users (email, password) VALUES (:email, :password)"
    //)

    //void registerNewUser(@Param("email") String email, @Param("password") String password);
    //List<users> findByUsernameContainingIgnoreCase(String username);


    @Query("SELECT u FROM users u JOIN u.followedCoaches c WHERE c.name = :coachUsername")
    List<users> findFollowersByCoachUsername(@Param("coachUsername") String coachUsername);

    @Query("SELECT u FROM users u WHERE :coach MEMBER OF u.followedCoaches")
    List<users> findUsersFollowingCoach(@Param("coach") Coach coach);
}