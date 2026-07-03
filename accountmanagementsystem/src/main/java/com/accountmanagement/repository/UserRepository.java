package com.accountmanagement.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.accountmanagement.model.User;

public interface UserRepository extends JpaRepository<User, String> {

    User findByUserName(String userName);

    Optional<User> findByEmail(String email);

    boolean existsByUserName(String userName);

    boolean existsByEmail(String email);

    boolean existsByPhone(String phone);

    @Query("""
                Select u from User u where u.email=:loginId OR u.userName=:loginId OR u.phone=:loginId
            """)
    Optional<User> findByLoginUser(@Param("loginId") String loginId);

}
