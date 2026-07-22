package com.accountmanagement.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.accountmanagement.model.Organization;
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

    @Autowired
    private OrganizationRepository organizationRepository;

    @Test
    public void shouldFindTopByUserIdOrderByCreatedAtDesc() throws InterruptedException {
        Organization organization = addOrganization();
        User user = addUser(organization.getId());
        PasswordReset oldReset = new PasswordReset();
        oldReset.setUserId(user.getId());
        oldReset.setCreatedAt(LocalDateTime.now().minusMinutes(10));
        passwordResetRepository.save(oldReset);
        Thread.sleep(1000);
        PasswordReset newReset = new PasswordReset();
        newReset.setUserId(user.getId());
        newReset.setCreatedAt(LocalDateTime.now());
        passwordResetRepository.save(newReset);
        Optional<PasswordReset> result = passwordResetRepository.findTopByUserIdOrderByCreatedAtDesc(user.getId());
        assertTrue(result.isPresent());
        assertEquals(newReset.getId(), result.get().getId());
    }

    @Test
    public void shouldFindByResetToken() {
        Organization organization = addOrganization();
        User user = addUser(organization.getId());
        User savedUser = userRepository.save(user);
        PasswordReset passwordReset = new PasswordReset();
        passwordReset.setUserId(savedUser.getId());
        passwordReset.setResetToken("token123");
        passwordReset.setCreatedAt(LocalDateTime.now());
        passwordResetRepository.save(passwordReset);
        Optional<PasswordReset> result = passwordResetRepository.findByResetToken("token123");
        assertTrue(result.isPresent());
    }

    private Organization addOrganization() {
        Organization organization = new Organization();
        organization.setCode("CH_" + UUID.randomUUID().toString().substring(0, 8));
        organization.setName("CSI Church");
        organization.setRegistrationNumber("REG123");
        organization.setEmail("csi@gmail.com");
        organization.setContactNumber("9876543210");
        organization.setWebsite("www.csichurch.com");
        organization.setAddress("4646 NGM Colony");
        organization.setCity("Nagercoil");
        organization.setState("Tamil Nadu");
        organization.setCountry("India");
        organization.setPostalcode("629002");
        organization.setPrimaryContactName("Ajay");
        organization.setPrimaryContactEmail("ajay@gmail.com");
        organization.setPrimaryContactPhone("9876543210");
        return organizationRepository.save(organization);
    }

    private User addUser(UUID id) {
        User user = new User();
        user.setOrganizationId(id);
        user.setUserName("ajay123");
        user.setEmail("ajay@gmail.com");
        user.setPassword("Ajay@123");
        user.setContactNumber("7859632148");
        user.setFailedLoginAttempts(0);
        user.setIsAccountLocked(false);
        user.setLockedTime(null);
        return userRepository.save(user);
    }
}
