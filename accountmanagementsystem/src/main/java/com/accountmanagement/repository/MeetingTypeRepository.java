package com.accountmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.accountmanagement.model.MeetingType;

public interface MeetingTypeRepository extends JpaRepository<MeetingType, Integer> {

    boolean existsByCategory(String category);

}
