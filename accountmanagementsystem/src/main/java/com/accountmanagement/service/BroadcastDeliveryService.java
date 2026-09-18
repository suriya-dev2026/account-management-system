package com.accountmanagement.service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;
import com.accountmanagement.constants.message.BroadcastMessage;
import com.accountmanagement.enums.DeliveryStatus;
import com.accountmanagement.exceptions.DuplicateRecordException;
import com.accountmanagement.exceptions.RecordNotFoundException;
import com.accountmanagement.model.Broadcast;
import com.accountmanagement.model.BroadcastDelivery;
import com.accountmanagement.repository.BroadcastDeliveryRepository;
import com.accountmanagement.repository.BroadcastRepository;
import com.accountmanagement.request.BroadcastDeliveryRequest;

@Service
public class BroadcastDeliveryService {

    private final BroadcastDeliveryRepository broadcastDeliveryRepository;

    private final BroadcastRepository broadcastRepository;

    private final EmailQueueService emailQueueService;

    public BroadcastDeliveryService(BroadcastDeliveryRepository broadcastDeliveryRepository,
            BroadcastRepository broadcastRepository, EmailQueueService emailQueueService) {
        this.broadcastDeliveryRepository = broadcastDeliveryRepository;
        this.broadcastRepository = broadcastRepository;
        this.emailQueueService = emailQueueService;
    }

    public BroadcastDelivery createBroadcastDelivery(BroadcastDeliveryRequest broadcastDeliveryRequest) {
        validateBroadcastDelivery(broadcastDeliveryRequest);
        BroadcastDelivery broadcastDelivery = new BroadcastDelivery();
        broadcastDelivery.setBroadcastId(broadcastDeliveryRequest.getBroadcastId());
        broadcastDelivery.setUserId(broadcastDeliveryRequest.getUserId());
        broadcastDelivery.setDeliveryStatus(DeliveryStatus.PENDING);
        broadcastDelivery.setSentTo(broadcastDeliveryRequest.getSentTo());
        Broadcast broadcast = findBroadcastById(broadcastDeliveryRequest.getBroadcastId());
        emailQueueService.addToEmailQueue(broadcastDeliveryRequest.getUserId(), broadcastDeliveryRequest.getSentTo(),
                broadcast.getMessage());
        return broadcastDeliveryRepository.save(broadcastDelivery);
    }

    public void updateBroadcastDeliveryStatus(UUID userId, String sentTo,
            DeliveryStatus deliveryStatus) {
        BroadcastDelivery delivery = broadcastDeliveryRepository.findByUserIdAndSentTo(userId, sentTo)
                .orElseThrow(() -> new RecordNotFoundException(BroadcastMessage.BROADCAST_DELIVERY_NOT_EXISTS));
        delivery.setDeliveryStatus(deliveryStatus);
        if (deliveryStatus == DeliveryStatus.SENT) {
            delivery.setSentAt(LocalDate.now());
        }
        broadcastDeliveryRepository.save(delivery);
    }

    public void validateBroadcastDelivery(BroadcastDeliveryRequest request) {
        boolean exists = broadcastDeliveryRepository.existsByBroadcastIdAndSentTo(request.getBroadcastId(),
                request.getSentTo());
        if (exists) {
            throw new DuplicateRecordException(BroadcastMessage.BROADCAST_ALREADY_SENT);
        }
    }

    public List<BroadcastDelivery> viewAllBroadcastDelivery() {
        return broadcastDeliveryRepository.findAll();
    }

    public Broadcast findBroadcastById(UUID broadcastId) {
        return broadcastRepository.findById(broadcastId)
                .orElseThrow(() -> new RecordNotFoundException(BroadcastMessage.BROADCAST_ID_NOT_FOUND));
    }

}
