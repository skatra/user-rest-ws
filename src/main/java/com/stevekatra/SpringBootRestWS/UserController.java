package com.stevekatra.springbootrestws;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public User createUser(@RequestBody User user) {
        return userRepository.save(user);
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<User> getUsers() {
        return (List<User>) userRepository.findAll();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public User getUserById(@PathVariable Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        if(userOptional.isPresent())
            return userOptional.get();
        throw new NotFoundException();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public User updateUserById(@PathVariable Long id, @RequestBody User user) {
        Optional<User> userOptional = userRepository.findById(id);
        if(userOptional.isPresent() && userOptional.get().getId() == user.getId())
            return userRepository.save(user);
        throw new NotFoundException();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteUserById(@PathVariable Long id) {
        userRepository.deleteById(id);
    }
}
