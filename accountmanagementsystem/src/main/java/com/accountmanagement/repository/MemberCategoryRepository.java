package com.accountmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.accountmanagement.model.MemberCategory;

public interface MemberCategoryRepository extends JpaRepository<MemberCategory, Integer> {

    boolean existsByCategoryIgnoreCase(String trim);

}
