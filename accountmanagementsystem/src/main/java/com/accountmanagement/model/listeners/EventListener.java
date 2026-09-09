package com.accountmanagement.model.listeners;

import java.time.LocalDateTime;
import com.accountmanagement.constants.AppConstants;
import com.accountmanagement.model.Event;
import lombok.Data;

@Data
public class EventListener {

    public void onCreateEvent(Event event) {
        event.setStatus(AppConstants.SUCCESS);
        event.setCreatedAt(LocalDateTime.now());
    }

    public void onUpdateEvent(Event event) {
        event.setUpdatedAt(LocalDateTime.now());
    }

}
