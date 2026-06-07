package com.accountmanagement.repository;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import com.accountmanagement.model.User;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void shouldFindUserByEmail() {
        User user = new User();
        user.setFirstName("Ajay");
        user.setUserName("ajay123");
        user.setEmail("test@gmail.com");
        user.setPassword("Password@123");
        user.setPhone("75412589631");
        userRepository.save(user);
        Optional<User> result = userRepository.findByEmail("test@gmail.com");
        assertTrue(result.isPresent());
    }

    @Test
    public void shouldFindByLoginUserUsingEmail() {
        User user = new User();
        user.setFirstName("Ajay");
        user.setUserName("ajay123");
        user.setEmail("test@gmail.com");
        user.setPassword("Password@123");
        user.setPhone("75412589631");
        userRepository.save(user);
        Optional<User> result = userRepository.findByLoginUser("test@gmail.com");
        assertTrue(result.isPresent());
        assertEquals("ajay123", result.get().getUserName());
    }

    @Test
    public void shouldExistsByEmail() {
        User user = new User();
        user.setFirstName("Ajay");
        user.setUserName("ajay123");
        user.setEmail("test@gmail.com");
        user.setPhone("7584123698");
        user.setPassword("Password@123");
        userRepository.save(user);
        boolean exists = userRepository.existsByEmail("test@gmail.com");
        assertTrue(exists);
    }

    @Test
    public void shouldNotExistsByEmail() {
        boolean exists = userRepository.existsByEmail("unknown@gmail.com");
        assertFalse(exists);
    }

    @Test
    public void shouldExistsByUserName() {
        User user = new User();
        user.setFirstName("Ajay");
        user.setUserName("ajay123");
        user.setEmail("test@gmail.com");
        user.setPhone("7584123698");
        user.setPassword("Password@123");
        userRepository.save(user);
        boolean exists = userRepository.existsByUserName("ajay123");
        assertTrue(exists);
    }

    @Test
    public void shouldNotExistsByUserName() {
        boolean exists = userRepository.existsByUserName("ajay123");
        assertFalse(exists);
    }
}
