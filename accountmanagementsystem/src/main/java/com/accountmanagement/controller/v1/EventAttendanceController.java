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
import com.accountmanagement.model.EventAttendance;
import com.accountmanagement.request.AttendanceUpdateRequest;
import com.accountmanagement.request.EventAttendanceRequest;
import com.accountmanagement.response.ApiResponse;
import com.accountmanagement.service.EventAttendanceService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/v1/event/attendance")
@Tag(name = "EventAttendanceController")
public class EventAttendanceController {

    private final EventAttendanceService eventAttendanceService;

    public EventAttendanceController(EventAttendanceService eventAttendanceService) {
        this.eventAttendanceService = eventAttendanceService;
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse> createEventAttendance(
            @Valid @RequestBody EventAttendanceRequest eventAttendanceRequest) {
        eventAttendanceService.createEventAttendance(eventAttendanceRequest);
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS, EventAttendanceMessage.CREATE_EVENT_ATTENDANCE,
                201);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse> updateEventAttendance(@PathVariable UUID id,
            @Valid @RequestBody AttendanceUpdateRequest updateRequest) {
        eventAttendanceService.updateEventAttendance(id, updateRequest);
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS, EventAttendanceMessage.UPDATE_EVENT_ATTENDANCE,
                200);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteEventAttendanceById(@PathVariable UUID id) {
        eventAttendanceService.deleteEventAttendanceById(id);
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS, EventAttendanceMessage.DELETE_EVENT_ATTENDANCE,
                200);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<ApiResponse> viewAllEventAttendance() {
        List<EventAttendance> eventAttendance = eventAttendanceService.viewAllEventAttendance();
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS, EventAttendanceMessage.VIEW_ALL_EVENT_ATTENDANCE,
                200);
        response.setData(eventAttendance);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
