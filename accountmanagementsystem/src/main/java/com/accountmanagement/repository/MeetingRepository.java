package com.accountmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.accountmanagement.model.Meeting;

public interface MeetingRepository extends JpaRepository<Meeting, Integer> {

}
