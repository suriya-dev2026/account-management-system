package com.accountmanagement.controller.v1;

import java.util.List;

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
import com.accountmanagement.constants.message.MeetingMessage;
import com.accountmanagement.model.Meeting;
import com.accountmanagement.request.MeetingRequest;
import com.accountmanagement.request.MeetingUpdateRequest;
import com.accountmanagement.response.ApiResponse;
import com.accountmanagement.service.MeetingService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/v1/meeting")
@Tag(name = "MeetingController")
public class MeetingController {

    private final MeetingService meetingService;

    public MeetingController(MeetingService meetingService) {
        this.meetingService = meetingService;
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse> createMeeting(@Valid @RequestBody MeetingRequest meetingRequest) {
        meetingRequest.sanitizeInput();
        meetingService.createMeeting(meetingRequest);
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS, MeetingMessage.ADD_MEETING, 201);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse> updateMeetingById(@PathVariable Integer id,
            @Valid @RequestBody MeetingUpdateRequest meetingUpdateRequest) {
        meetingUpdateRequest.sanitizeInput();
        meetingService.updateMeetingById(id, meetingUpdateRequest);
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS, MeetingMessage.UPDATE_MEETING, 200);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteMeetingById(@PathVariable Integer id) {
        meetingService.deleteMeetingById(id);
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS, MeetingMessage.DELETE_MEETING, 200);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<ApiResponse> viewAllMeetings() {
        List<Meeting> meetings = meetingService.viewAllMeetings();
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS, MeetingMessage.VIEW_ALL_MEETINGS, 200);
        response.setData(meetings);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
