package com.stevekatra.springbootrestws;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    private User user;

    @BeforeEach
    void setUp() {
        user = userRepository.save(new User(1L, "john.doe@gmail.com", "John Doe"));
    }

    @Test
    void createUser() {
        assertNotNull(user);
    }

    @Test
    void getUsers() {
        List<User> users = (List<User>)userRepository.findAll();
        assertTrue(!users.isEmpty());
    }

    @Test
    void getUserById() {
        User data = userRepository.findById(user.getId()).get();
        assertNotNull(data);
    }

    @Test
    void updateUserById() {
        user.setEmail("john.doe@live.com");
        user = userRepository.save(user);
        assertEquals(user.getId(), 1L);
        assertEquals(user.getEmail(), "john.doe@live.com");
    }

    @Test
    void deleteUserById() {
        userRepository.deleteById(user.getId());
        assertFalse(userRepository.findById(user.getId()).isPresent());
    }
}
