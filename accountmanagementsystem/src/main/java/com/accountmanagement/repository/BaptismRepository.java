package com.accountmanagement.repository;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import com.accountmanagement.model.Baptism;

public interface BaptismRepository extends JpaRepository<Baptism, UUID> {

}
