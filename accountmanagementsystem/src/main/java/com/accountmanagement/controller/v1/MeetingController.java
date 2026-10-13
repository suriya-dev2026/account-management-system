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
import com.accountmanagement.constants.message.MeetingAttendanceMessage;
import com.accountmanagement.constants.message.MeetingMessage;
import com.accountmanagement.constants.message.MeetingTypeMessage;
import com.accountmanagement.model.Meeting;
import com.accountmanagement.model.MeetingAttendance;
import com.accountmanagement.model.MeetingType;
import com.accountmanagement.request.AttendanceUpdateRequest;
import com.accountmanagement.request.MeetingAttendanceRequest;
import com.accountmanagement.request.MeetingRequest;
import com.accountmanagement.request.MeetingTypeRequest;
import com.accountmanagement.request.MeetingUpdateRequest;
import com.accountmanagement.response.ApiResponse;
import com.accountmanagement.service.MeetingAttendanceService;
import com.accountmanagement.service.MeetingService;
import com.accountmanagement.service.MeetingTypeService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "/v1/meeting")
@Tag(name = "MeetingController")
@RequiredArgsConstructor
public class MeetingController {

    private final MeetingService meetingService;

    private final MeetingTypeService meetingTypeService;

    private final MeetingAttendanceService meetingAttendanceService;

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

    @GetMapping("/view")
    public ResponseEntity<ApiResponse> viewAllMeetings() {
        List<Meeting> meetings = meetingService.viewAllMeetings();
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS, MeetingMessage.VIEW_ALL_MEETINGS, 200);
        response.setData(meetings);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/type/create")
    public ResponseEntity<ApiResponse> createMeetingType(@Valid @RequestBody MeetingTypeRequest meetingTypeRequest) {
        meetingTypeRequest.sanitizeInput();
        meetingTypeService.createMeetingType(meetingTypeRequest);
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS, MeetingTypeMessage.ADD_MEETING_TYPE, 201);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/type/update/{id}")
    public ResponseEntity<ApiResponse> updateMeetingType(@PathVariable Integer id,
            @Valid @RequestBody MeetingTypeRequest meetingTypeRequest) {
        meetingTypeRequest.sanitizeInput();
        meetingTypeService.updateMeetingType(id, meetingTypeRequest);
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS, MeetingTypeMessage.UPDATE_MEETING_TYPE, 200);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/type/delete/{id}")
    public ResponseEntity<ApiResponse> deleteMeetingType(@PathVariable Integer id) {
        meetingTypeService.deleteMeetingTypeById(id);
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS, MeetingTypeMessage.DELETE_MEETING_TYPE, 200);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/type/view")
    public ResponseEntity<ApiResponse> viewAllMeetingTypes() {
        List<MeetingType> meetingType = meetingTypeService.viewAllMeetingTypes();
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS, MeetingTypeMessage.VIEW_ALL_MEETING_TYPES, 200);
        response.setData(meetingType);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/attendance/create")
    public ResponseEntity<ApiResponse> createMeetingAttendance(
            @Valid @RequestBody MeetingAttendanceRequest meetingAttendanceRequest) {
        meetingAttendanceService.createMeetingAttendance(meetingAttendanceRequest);
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS, MeetingAttendanceMessage.ADD_MEETING_ATTENDANCE,
                201);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/attendance/update/{id}")
    public ResponseEntity<ApiResponse> updateMeetingAttendanceById(@PathVariable UUID id,
            @Valid @RequestBody AttendanceUpdateRequest request) {
        meetingAttendanceService.updateMeetingAttendanceById(id, request);
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS, MeetingAttendanceMessage.UPDATE_MEETING_ATTENDANCE,
                200);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/attendance/delete/{id}")
    public ResponseEntity<ApiResponse> deleteMeetingAttendanceById(@PathVariable UUID id) {
        meetingAttendanceService.deleteMeetingAttendanceById(id);
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS, MeetingAttendanceMessage.DELETE_MEETING_ATTENDANCE,
                200);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/attendance")
    public ResponseEntity<ApiResponse> viewAllMeetingAttendances() {
        List<MeetingAttendance> meetingAttendance = meetingAttendanceService.viewAllMeetingAttendances();
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS,
                MeetingAttendanceMessage.VIEW_ALL_MEETING_ATTENDANCE,
                200);
        response.setData(meetingAttendance);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
