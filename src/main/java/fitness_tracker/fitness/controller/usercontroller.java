package fitness_tracker.fitness.controller;


import fitness_tracker.fitness.model.users;
import fitness_tracker.fitness.service.userservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/auth/user")
public class usercontroller {

    @Autowired
    private userservice userservice;

    @GetMapping("/AllUsers")
    public List<users> view()
    {
        return userservice.viewUsers();
    }

}