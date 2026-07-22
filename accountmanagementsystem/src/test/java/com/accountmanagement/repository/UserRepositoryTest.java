package com.accountmanagement.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import static org.junit.jupiter.api.Assertions.assertFalse;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void shouldNotExistsByEmail() {
        boolean exists = userRepository.existsByEmail("unknown@gmail.com");
        assertFalse(exists);
    }

    @Test
    public void shouldNotExistsByUserName() {
        boolean exists = userRepository.existsByUserName("ajay123");
        assertFalse(exists);
    }
}
