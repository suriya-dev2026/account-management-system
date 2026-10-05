package com.accountmanagement.repository;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import com.accountmanagement.model.Equipment;

public interface EquipmentRepository extends JpaRepository<Equipment, UUID> {

}
