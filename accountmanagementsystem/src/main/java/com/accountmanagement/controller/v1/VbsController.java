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
import com.accountmanagement.constants.message.VbsAttendanceMessage;
import com.accountmanagement.constants.message.VbsClassMessage;
import com.accountmanagement.constants.message.VbsStudentMessage;
import com.accountmanagement.constants.message.VbsTeacherMessage;
import com.accountmanagement.constants.message.VbsYearMessage;
import com.accountmanagement.model.VbsAttendance;
import com.accountmanagement.model.VbsClass;
import com.accountmanagement.model.VbsStudent;
import com.accountmanagement.model.VbsTeacher;
import com.accountmanagement.model.VbsYear;
import com.accountmanagement.request.AttendanceUpdateRequest;
import com.accountmanagement.request.VbsAttendanceRequest;
import com.accountmanagement.request.VbsClassRequest;
import com.accountmanagement.request.VbsStudentRequest;
import com.accountmanagement.request.VbsStudentUpdateRequest;
import com.accountmanagement.request.VbsTeacherRequest;
import com.accountmanagement.request.VbsTeacherUpdateRequest;
import com.accountmanagement.request.VbsYearRequest;
import com.accountmanagement.response.ApiResponse;
import com.accountmanagement.service.VbsAttendanceService;
import com.accountmanagement.service.VbsClassService;
import com.accountmanagement.service.VbsStudentService;
import com.accountmanagement.service.VbsTeacherService;
import com.accountmanagement.service.VbsYearService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "/v1/vbs")
@Tag(name = "VbsController")
@RequiredArgsConstructor
public class VbsController {

    private final VbsYearService vbsYearService;

    private final VbsClassService vbsClassService;

    private final VbsStudentService vbsStudentService;

    private final VbsTeacherService vbsTeacherService;

    private final VbsAttendanceService vbsAttendanceService;

    @PostMapping("/year/create")
    public ResponseEntity<ApiResponse> addVbsYear(@Valid @RequestBody VbsYearRequest vbsYearRequest) {
        vbsYearRequest.sanitizeInput();
        vbsYearService.createVbsYear(vbsYearRequest);
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS, VbsYearMessage.ADD_VBS_YEAR, 201);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/year/update/{id}")
    public ResponseEntity<ApiResponse> updateVbsYear(@PathVariable Integer id,
            @Valid @RequestBody VbsYearRequest vbsYearRequest) {
        vbsYearService.updateVbsYear(id, vbsYearRequest);
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS, VbsYearMessage.UPDATE_VBS_YEAR, 200);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/year/delete/{id}")
    public ResponseEntity<ApiResponse> deleteVbsYearById(@PathVariable Integer id) {
        vbsYearService.deleteVbsYearById(id);
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS, VbsYearMessage.DELETE_VBS_YEAR, 200);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/year/view")
    public ResponseEntity<ApiResponse> viewAllVbsYears() {
        List<VbsYear> vbsYears = vbsYearService.viewAllVbsYears();
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS, VbsYearMessage.VIEW_ALL_VBS_YEARS, 200);
        response.setData(vbsYears);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/class/create")
    public ResponseEntity<ApiResponse> addVbsClass(@Valid @RequestBody VbsClassRequest vbsClassRequest) {
        vbsClassRequest.sanitizeInput();
        vbsClassService.createVbsClass(vbsClassRequest);
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS, VbsClassMessage.ADD_VBS_CLASS, 201);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/class/update/{id}")
    public ResponseEntity<ApiResponse> updateVbsClass(@PathVariable Integer id,
            @Valid @RequestBody VbsClassRequest vbsClassRequest) {
        vbsClassRequest.sanitizeInput();
        vbsClassService.updateVbsClass(id, vbsClassRequest);
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS, VbsClassMessage.UPDATE_VBS_CLASS, 200);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/class/delete/{id}")
    public ResponseEntity<ApiResponse> deleteVbsClassById(@PathVariable Integer id) {
        vbsClassService.deleteVbsClassById(id);
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS, VbsClassMessage.DELETE_VBS_CLASS, 200);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/class/view")
    public ResponseEntity<ApiResponse> viewAllVbsClass() {
        List<VbsClass> vbsClass = vbsClassService.viewAllVbsClass();
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS, VbsClassMessage.VIEW_ALL_VBS_CLASSES, 200);
        response.setData(vbsClass);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/student/create")
    public ResponseEntity<ApiResponse> addVbsStudent(@Valid @RequestBody VbsStudentRequest vbsStudentRequest) {
        vbsStudentRequest.sanitizeInput();
        vbsStudentService.createVbsStudent(vbsStudentRequest);
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS, VbsStudentMessage.ADD_VBS_STUDENT, 201);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/student/update/{id}")
    public ResponseEntity<ApiResponse> updateVbsStudent(@PathVariable UUID id,
            @Valid @RequestBody VbsStudentUpdateRequest vbsStudentUpdateRequest) {
        vbsStudentService.updateVbsStudent(id, vbsStudentUpdateRequest);
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS, VbsStudentMessage.UPDATE_VBS_STUDENT, 200);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/student/delete/{id}")
    public ResponseEntity<ApiResponse> deleteVbsStudentById(@PathVariable UUID id) {
        vbsStudentService.deleteVbsStudentById(id);
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS, VbsStudentMessage.DELETE_VBS_STUDENT, 200);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/student/view")
    public ResponseEntity<ApiResponse> viewAllVbsStudent() {
        List<VbsStudent> vbsStudent = vbsStudentService.viewAllStudents();
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS, VbsStudentMessage.VIEW_ALL_VBS_STUDENTS, 200);
        response.setData(vbsStudent);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/teacher/create")
    public ResponseEntity<ApiResponse> createVbsTeacher(@Valid @RequestBody VbsTeacherRequest vbsTeacherRequest) {
        vbsTeacherRequest.sanitizeInput();
        vbsTeacherService.createVbsTeacher(vbsTeacherRequest);
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS, VbsTeacherMessage.ADD_VBS_TEACHER, 201);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/teacher/update/{id}")
    public ResponseEntity<ApiResponse> updateVbsTeacher(@PathVariable UUID id,
            @Valid @RequestBody VbsTeacherUpdateRequest vbsTeacherUpdateRequest) {
        vbsTeacherUpdateRequest.sanitizeInput();
        vbsTeacherService.updateVbsTeacher(id, vbsTeacherUpdateRequest);
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS, VbsTeacherMessage.UPDATE_VBS_TEACHER, 200);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/teacher/delete/{id}")
    public ResponseEntity<ApiResponse> deleteVbsTeacherById(@PathVariable UUID id) {
        vbsTeacherService.deleteVbsTeacherById(id);
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS, VbsTeacherMessage.DELETE_VBS_TEACHER, 200);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/teacher/view")
    public ResponseEntity<ApiResponse> viewAllVbsTeacher() {
        List<VbsTeacher> vbsTeacher = vbsTeacherService.viewAllVbsTeachers();
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS, VbsTeacherMessage.VIEW_ALL_VBS_TEACHERS, 200);
        response.setData(vbsTeacher);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/attendance/create")
    public ResponseEntity<ApiResponse> createVbsAttendance(
            @Valid @RequestBody VbsAttendanceRequest vbsAttendanceRequest) {
        vbsAttendanceService.createVbsAttendance(vbsAttendanceRequest);
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS, VbsAttendanceMessage.ADD_VBS_ATTENDANCE, 201);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/attendance/update/{id}")
    public ResponseEntity<ApiResponse> updateVbsAttendance(@PathVariable UUID id,
            @Valid @RequestBody AttendanceUpdateRequest vbsAttendanceUpdateRequest) {
        vbsAttendanceService.updateVbsAttendance(id, vbsAttendanceUpdateRequest);
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS, VbsAttendanceMessage.UPDATE_VBS_ATTENDANCE, 200);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/attendance/delete/{id}")
    public ResponseEntity<ApiResponse> deleteVbsAttendanceById(@PathVariable UUID id) {
        vbsAttendanceService.deleteVbsAttendanceById(id);
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS, VbsAttendanceMessage.DELETE_VBS_ATTENDANCE, 200);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/attendance/view")
    public ResponseEntity<ApiResponse> viewAllVbsAttendance() {
        List<VbsAttendance> vbsAttendance = vbsAttendanceService.viewAllVbsAttendance();
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS, VbsAttendanceMessage.VIEW_ALL_VBS_ATTENDANCE, 200);
        response.setData(vbsAttendance);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
