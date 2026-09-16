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
import com.accountmanagement.constants.message.EventMessage;
import com.accountmanagement.model.Event;
import com.accountmanagement.request.EventRequest;
import com.accountmanagement.response.ApiResponse;
import com.accountmanagement.service.EventService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/v1/event")
@Tag(name = "EventController")
public class EventController {

    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse> createEvent(@Valid @RequestBody EventRequest eventRequest) {
        eventRequest.sanitizeInput();
        eventService.createEvent(eventRequest);
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS, EventMessage.ADD_EVENT, 201);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse> updateEvent(@PathVariable UUID id,
            @Valid @RequestBody EventRequest eventRequest) {
        eventRequest.sanitizeInput();
        eventService.updateEvent(id, eventRequest);
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS, EventMessage.UPDATE_EVENT, 200);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteEventById(@PathVariable UUID id) {
        eventService.deleteEventById(id);
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS, EventMessage.DELETE_EVENT, 200);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<ApiResponse> viewAllEvents() {
        List<Event> event = eventService.viewAllEvent();
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS, EventMessage.EVENTS, 200);
        response.setData(event);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
