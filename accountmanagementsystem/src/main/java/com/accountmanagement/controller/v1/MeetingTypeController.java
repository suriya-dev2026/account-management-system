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
import com.accountmanagement.constants.message.MeetingTypeMessage;
import com.accountmanagement.model.MeetingType;
import com.accountmanagement.request.MeetingTypeRequest;
import com.accountmanagement.response.ApiResponse;
import com.accountmanagement.service.MeetingTypeService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/v1/meeting/type")
@Tag(name = "MeetingTypeController")
public class MeetingTypeController {

    private final MeetingTypeService meetingTypeService;

    public MeetingTypeController(MeetingTypeService meetingTypeService) {
        this.meetingTypeService = meetingTypeService;
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse> createMeetingType(@Valid @RequestBody MeetingTypeRequest meetingTypeRequest) {
        meetingTypeRequest.sanitizeInput();
        meetingTypeService.createMeetingType(meetingTypeRequest);
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS, MeetingTypeMessage.ADD_MEETING_TYPE, 201);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse> updateMeetingType(@PathVariable Integer id,
            @Valid @RequestBody MeetingTypeRequest meetingTypeRequest) {
        meetingTypeRequest.sanitizeInput();
        meetingTypeService.updateMeetingType(id, meetingTypeRequest);
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS, MeetingTypeMessage.UPDATE_MEETING_TYPE, 200);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteMeetingType(@PathVariable Integer id) {
        meetingTypeService.deleteMeetingTypeById(id);
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS, MeetingTypeMessage.DELETE_MEETING_TYPE, 200);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<ApiResponse> viewAllMeetingTypes() {
        List<MeetingType> meetingType = meetingTypeService.viewAllMeetingTypes();
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS, MeetingTypeMessage.VIEW_ALL_MEETING_TYPES, 200);
        response.setData(meetingType);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
