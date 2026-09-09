package com.accountmanagement.model.listeners;

import java.time.LocalDateTime;
import com.accountmanagement.model.EventAttendance;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Data;

@Data 
public class EventAttendanceListener {

    @PrePersist 
    public void onCreateEventAttendance(EventAttendance eventAttendance){
        eventAttendance.setCreatedAt(LocalDateTime.now());
    }

    @PreUpdate  
    public void onUpdateEventAttendance(EventAttendance eventAttendance){
        eventAttendance.setUpdatedAt(LocalDateTime.now());
    }

}
