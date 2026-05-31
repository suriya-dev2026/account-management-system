package com.accountmanagement.model;

import java.time.LocalDateTime;
import org.hibernate.annotations.UuidGenerator;
import com.accountmanagement.model.listeners.UserLogListener;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "users_logs")
@EntityListeners(UserLogListener.class)
public class UserLog {

    @Id
    @UuidGenerator
    @Column(name = "id")
    private String id;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "user_action")
    private String userAction;

    @Column(name = "return_result")
    private String returnResult;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

}
