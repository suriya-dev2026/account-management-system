package com.accountmanagement.repository;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import com.accountmanagement.model.Event;

public interface EventRepository extends JpaRepository<Event, UUID> {

    boolean existsByEventNameIgnoreCase(String trim);

    boolean existsByEventTypeIgnoreCase(String trim);

}
