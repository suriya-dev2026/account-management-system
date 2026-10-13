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
import com.accountmanagement.constants.message.EventAttendanceMessage;
import com.accountmanagement.constants.message.EventMessage;
import com.accountmanagement.constants.message.EventParticipantMessage;
import com.accountmanagement.model.Event;
import com.accountmanagement.model.EventAttendance;
import com.accountmanagement.model.EventParticipant;
import com.accountmanagement.request.AttendanceUpdateRequest;
import com.accountmanagement.request.EventAttendanceRequest;
import com.accountmanagement.request.EventParticipantRequest;
import com.accountmanagement.request.EventParticipantUpdateRequest;
import com.accountmanagement.request.EventRequest;
import com.accountmanagement.response.ApiResponse;
import com.accountmanagement.service.EventAttendanceService;
import com.accountmanagement.service.EventParticipantService;
import com.accountmanagement.service.EventService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "/v1/event")
@Tag(name = "EventController")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;

    private final EventParticipantService eventParticipantService;

    private final EventAttendanceService eventAttendanceService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse> createEvent(@Valid @RequestBody EventRequest eventRequest) {
        eventRequest.sanitizeInput();
        eventService.createEvent(eventRequest);
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS, EventMessage.ADD_EVENT, 201);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
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

    @GetMapping("/view")
    public ResponseEntity<ApiResponse> viewAllEvents() {
        List<Event> event = eventService.viewAllEvent();
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS, EventMessage.EVENTS, 200);
        response.setData(event);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/participant/create")
    public ResponseEntity<ApiResponse> createEventParticipant(
            @Valid @RequestBody EventParticipantRequest eventParticipantRequest) {
        eventParticipantRequest.sanitizeInput();
        eventParticipantService.createEventParticipant(eventParticipantRequest);
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS, EventParticipantMessage.ADD_EVENT_PARTICIPANT,
                201);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/participant/update/{id}")
    public ResponseEntity<ApiResponse> updateEventParticipant(@PathVariable UUID id,
            @Valid @RequestBody EventParticipantUpdateRequest eventParticipantUpdateRequest) {
        eventParticipantUpdateRequest.sanitizeInput();
        eventParticipantService.updateEventParticipant(id, eventParticipantUpdateRequest);
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS, EventParticipantMessage.UPDATE_EVENT_PARTICIPANT,
                200);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/participant/delete/{id}")
    public ResponseEntity<ApiResponse> deleteEventParticipantById(@PathVariable UUID id) {
        eventParticipantService.deleteEventParticipantById(id);
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS, EventParticipantMessage.DELETE_EVENT_PARTICIPANT,
                200);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/participant/view")
    public ResponseEntity<ApiResponse> viewAllEventParticipant() {
        List<EventParticipant> eventParticipant = eventParticipantService.viewAllEventParticipant();
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS, EventParticipantMessage.EVENT_PARTICIPANTS,
                200);
        response.setData(eventParticipant);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/attendance/create")
    public ResponseEntity<ApiResponse> createEventAttendance(
            @Valid @RequestBody EventAttendanceRequest eventAttendanceRequest) {
        eventAttendanceService.createEventAttendance(eventAttendanceRequest);
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS, EventAttendanceMessage.CREATE_EVENT_ATTENDANCE,
                201);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/attendance/update/{id}")
    public ResponseEntity<ApiResponse> updateEventAttendance(@PathVariable UUID id,
            @Valid @RequestBody AttendanceUpdateRequest updateRequest) {
        eventAttendanceService.updateEventAttendance(id, updateRequest);
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS, EventAttendanceMessage.UPDATE_EVENT_ATTENDANCE,
                200);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/attendance/delete/{id}")
    public ResponseEntity<ApiResponse> deleteEventAttendanceById(@PathVariable UUID id) {
        eventAttendanceService.deleteEventAttendanceById(id);
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS, EventAttendanceMessage.DELETE_EVENT_ATTENDANCE,
                200);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/attendance")
    public ResponseEntity<ApiResponse> viewAllEventAttendance() {
        List<EventAttendance> eventAttendance = eventAttendanceService.viewAllEventAttendance();
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS, EventAttendanceMessage.VIEW_ALL_EVENT_ATTENDANCE,
                200);
        response.setData(eventAttendance);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
