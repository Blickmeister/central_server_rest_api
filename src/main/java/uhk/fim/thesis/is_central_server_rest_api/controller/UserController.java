package uhk.fim.thesis.is_central_server_rest_api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import uhk.fim.thesis.is_central_server_rest_api.model.User;
import uhk.fim.thesis.is_central_server_rest_api.repository.UserRepository;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @RequestMapping(value = "/all" , method = RequestMethod.GET)
    public List<User> getUsers() {
        return userRepository.findAll();
    }

}
