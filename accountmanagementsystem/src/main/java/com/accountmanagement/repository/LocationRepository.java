package com.accountmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.accountmanagement.model.Location;

public interface LocationRepository extends JpaRepository<Location, Integer> {

    boolean existsByLocation(String location);

    boolean existsByLocationIgnoreCase(String trim);

}
