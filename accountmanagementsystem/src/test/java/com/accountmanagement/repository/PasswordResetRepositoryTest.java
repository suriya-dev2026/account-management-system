package com.accountmanagement.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.time.LocalDateTime;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import com.accountmanagement.model.PasswordReset;
import com.accountmanagement.model.User;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class PasswordResetRepositoryTest {

    @Autowired
    private PasswordResetRepository passwordResetRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void shouldFindTopByUserIdOrderByCreatedAtDesc() throws InterruptedException {
        User user = new User();
        user.setFirstName("Ajay");
        user.setLastName("Kumar");
        user.setUserName("ajay123");
        user.setEmail("ajay@gmail.com");
        user.setPassword("Ajay@123");
        user.setPhone("7859632148");
        User savedUser = userRepository.save(user);
        PasswordReset oldReset = new PasswordReset();
        oldReset.setUserId(savedUser.getId());
        oldReset.setResetToken("oldToken");
        oldReset.setCreatedAt(LocalDateTime.now().minusMinutes(10));
        passwordResetRepository.save(oldReset);
        Thread.sleep(2000);
        PasswordReset newReset = new PasswordReset();
        newReset.setUserId(savedUser.getId());
        newReset.setResetToken("newToken");
        newReset.setCreatedAt(LocalDateTime.now());
        passwordResetRepository.save(newReset);
        Optional<PasswordReset> result = passwordResetRepository.findTopByUserIdOrderByCreatedAtDesc(savedUser.getId());
        assertTrue(result.isPresent());
        assertEquals("newToken", result.get().getResetToken());

    }

    @Test
    public void shouldFindByResetToken() {
        User user = new User();
        user.setFirstName("Ajay");
        user.setLastName("Kumar");
        user.setUserName("ajay123");
        user.setEmail("ajay@gmail.com");
        user.setPassword("Ajay@123");
        user.setPhone("7859632148");
        User savedUser = userRepository.save(user);
        PasswordReset passwordReset = new PasswordReset();
        passwordReset.setUserId(savedUser.getId());
        passwordReset.setResetToken("token123");
        passwordReset.setCreatedAt(LocalDateTime.now());
        passwordResetRepository.save(passwordReset);
        Optional<PasswordReset> result = passwordResetRepository.findByResetToken("token123");
        assertTrue(result.isPresent());
        assertEquals("token123", result.get().getResetToken());
    }
}
