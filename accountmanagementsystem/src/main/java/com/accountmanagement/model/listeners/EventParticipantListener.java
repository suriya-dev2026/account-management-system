package com.accountmanagement.model.listeners;

import java.time.LocalDateTime;
import com.accountmanagement.constants.AppConstants;
import com.accountmanagement.model.EventParticipant;
import lombok.Data;

@Data
public class EventParticipantListener {

    public void onCreateEventParticipant(EventParticipant eventParticipant) {
        eventParticipant.setStatus(AppConstants.ACTIVE);
        eventParticipant.setCreatedAt(LocalDateTime.now());
    }

    public void onUpdateEventParticipant(EventParticipant eventParticipant) {
        eventParticipant.setUpdatedAt(LocalDateTime.now());
    }

}
