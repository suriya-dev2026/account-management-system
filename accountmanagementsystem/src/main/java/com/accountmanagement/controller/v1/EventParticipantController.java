package com.accountmanagement.controller.v1;

import java.util.List;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.accountmanagement.constants.AppConstants;
import com.accountmanagement.constants.message.EventParticipantMessage;
import com.accountmanagement.model.EventParticipant;
import com.accountmanagement.request.EventParticipantRequest;
import com.accountmanagement.request.EventParticipantUpdateRequest;
import com.accountmanagement.response.ApiResponse;
import com.accountmanagement.service.EventParticipantService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/v1/event/participant")
@Tag(name = "EventParticipantController")
public class EventParticipantController {

    private final EventParticipantService eventParticipantService;

    public EventParticipantController(EventParticipantService eventParticipantService) {
        this.eventParticipantService = eventParticipantService;
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse> createEventParticipant(
            @Valid @RequestBody EventParticipantRequest eventParticipantRequest) {
        eventParticipantRequest.sanitizeInput();
        eventParticipantService.createEventParticipant(eventParticipantRequest);
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS, EventParticipantMessage.ADD_EVENT_PARTICIPANT,
                201);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse> updateEventParticipant(@PathVariable UUID id,
            @Valid @RequestBody EventParticipantUpdateRequest eventParticipantUpdateRequest) {
        eventParticipantUpdateRequest.sanitizeInput();
        eventParticipantService.updateEventParticipant(id, eventParticipantUpdateRequest);
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS, EventParticipantMessage.UPDATE_EVENT_PARTICIPANT,
                200);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteEventParticipantById(@PathVariable UUID id) {
        eventParticipantService.deleteEventParticipantById(id);
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS, EventParticipantMessage.DELETE_EVENT_PARTICIPANT,
                200);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<ApiResponse> viewAllEventParticipant() {
        List<EventParticipant> eventParticipant = eventParticipantService.viewAllEventParticipant();
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS, EventParticipantMessage.EVENT_PARTICIPANTS,
                200);
        response.setData(eventParticipant);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
