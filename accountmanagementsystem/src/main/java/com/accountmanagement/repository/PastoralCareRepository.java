package com.accountmanagement.repository;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import com.accountmanagement.model.PastoralCare;

public interface PastoralCareRepository extends JpaRepository<PastoralCare, UUID> {

    boolean existsByVisitorNameAndVisitorContactNumber(String visitorName, String visitorContactNumber);

}
