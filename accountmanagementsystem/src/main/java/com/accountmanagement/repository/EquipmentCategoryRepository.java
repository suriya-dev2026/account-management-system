package com.accountmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.accountmanagement.model.EquipmentCategory;

public interface EquipmentCategoryRepository extends JpaRepository<EquipmentCategory, Integer> {

    boolean existsByCategoryNameIgnoreCase(String category);

}
