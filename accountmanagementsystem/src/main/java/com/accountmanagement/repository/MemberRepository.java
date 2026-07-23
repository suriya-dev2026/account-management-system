package com.accountmanagement.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.accountmanagement.model.Member;

public interface MemberRepository extends JpaRepository<Member, UUID> {

    boolean existsByUserName(String userName);

    boolean existsByEmail(String email);

    boolean existsByContactNumber(String contactNumber);

}
