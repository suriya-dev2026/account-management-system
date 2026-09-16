package com.accountmanagement.service;

import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;
import com.accountmanagement.constants.AppConstants;
import com.accountmanagement.constants.message.BroadcastMessage;
import com.accountmanagement.exceptions.DuplicateRecordException;
import com.accountmanagement.exceptions.RecordNotFoundException;
import com.accountmanagement.model.Broadcast;
import com.accountmanagement.repository.BroadcastRepository;
import com.accountmanagement.request.BroadcastRequest;

@Service
public class BroadcastService {

    private final BroadcastRepository broadcastRepository;

    public BroadcastService(BroadcastRepository broadcastRepository) {
        this.broadcastRepository = broadcastRepository;
    }

    public Broadcast createBroadcast(BroadcastRequest broadcastRequest) {
        validateBroadcastType(broadcastRequest.getBroadcastType());
        Broadcast broadcast = new Broadcast();
        broadcast.setOrganizationId(broadcastRequest.getOrganizationId());
        broadcast.setBroadcastType(broadcastRequest.getBroadcastType());
        broadcast.setMessage(broadcastRequest.getMessage());
        return broadcastRepository.save(broadcast);
    }

    public Broadcast updateBroadcast(UUID id, BroadcastRequest broadcastRequest) {
        Broadcast broadcast = findBroadcastById(id);
        broadcast.setBroadcastType(broadcastRequest.getBroadcastType());
        broadcast.setMessage(broadcastRequest.getMessage());
        return broadcastRepository.save(broadcast);
    }

    public Broadcast deleteBroadcastById(UUID id) {
        Broadcast broadcast = findBroadcastById(id);
        broadcast.setStatus(AppConstants.INACTIVE);
        return broadcastRepository.save(broadcast);
    }

    public List<Broadcast> viewAllBroadcasts() {
        return broadcastRepository.findAll();
    }

    public Broadcast findBroadcastById(UUID id) {
        return broadcastRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(BroadcastMessage.BROADCAST_ID_NOT_FOUND));
    }

    public void validateBroadcastType(String broadcastType) {
        boolean exists = broadcastRepository.existsByBroadcastTypeEqualsIgnoreCase(broadcastType);
        if (exists) {
            throw new DuplicateRecordException(BroadcastMessage.BROADCAST_EXISTS);
        }
    }

}
