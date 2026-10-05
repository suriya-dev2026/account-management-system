package com.accountmanagement.repository;

import java.time.LocalDate;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import com.accountmanagement.model.Bulletin;

public interface BulletinRepository extends JpaRepository<Bulletin, UUID> {

    boolean existsByOrganizationIdAndTitleIgnoreCaseAndBulletinDate(UUID organizationId, String title,
            LocalDate bulletinDate);

}
