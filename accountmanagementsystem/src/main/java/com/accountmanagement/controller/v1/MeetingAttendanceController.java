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
import com.accountmanagement.model.MeetingAttendance;
import com.accountmanagement.request.AttendanceUpdateRequest;
import com.accountmanagement.request.MeetingAttendanceRequest;
import com.accountmanagement.response.ApiResponse;
import com.accountmanagement.service.MeetingAttendanceService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/v1/meeting/attendance")
@Tag(name = "MeetingAttendanceController")
public class MeetingAttendanceController {

    private final MeetingAttendanceService meetingAttendanceService;

    public MeetingAttendanceController(MeetingAttendanceService meetingAttendanceService) {
        this.meetingAttendanceService = meetingAttendanceService;
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse> createMeetingAttendance(
            @Valid @RequestBody MeetingAttendanceRequest meetingAttendanceRequest) {
        meetingAttendanceService.createMeetingAttendance(meetingAttendanceRequest);
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS, MeetingAttendanceMessage.ADD_MEETING_ATTENDANCE,
                201);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse> updateMeetingAttendanceById(@PathVariable UUID id,
            @Valid @RequestBody AttendanceUpdateRequest request) {
        meetingAttendanceService.updateMeetingAttendanceById(id, request);
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS, MeetingAttendanceMessage.UPDATE_MEETING_ATTENDANCE,
                200);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteMeetingAttendanceById(@PathVariable UUID id) {
        meetingAttendanceService.deleteMeetingAttendanceById(id);
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS, MeetingAttendanceMessage.DELETE_MEETING_ATTENDANCE,
                200);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<ApiResponse> viewAllMeetingAttendance() {
        List<MeetingAttendance> meetingAttendance = meetingAttendanceService.viewAllMeetingAttendances();
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS,
                MeetingAttendanceMessage.VIEW_ALL_MEETING_ATTENDANCE,
                200);
        response.setData(meetingAttendance);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
