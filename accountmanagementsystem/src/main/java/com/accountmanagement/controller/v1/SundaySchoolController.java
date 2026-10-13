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
import com.accountmanagement.constants.message.SundaySchoolAttendanceMessage;
import com.accountmanagement.constants.message.SundaySchoolClassMessage;
import com.accountmanagement.constants.message.SundaySchoolStudentMessage;
import com.accountmanagement.constants.message.SundaySchoolTeacherMessage;
import com.accountmanagement.constants.message.SundaySchoolTransitionMessage;
import com.accountmanagement.model.SundaySchoolAttendance;
import com.accountmanagement.model.SundaySchoolClass;
import com.accountmanagement.model.SundaySchoolStudent;
import com.accountmanagement.model.SundaySchoolTeacher;
import com.accountmanagement.model.SundaySchoolTransition;
import com.accountmanagement.request.AttendanceUpdateRequest;
import com.accountmanagement.request.SundaySchoolAttendanceRequest;
import com.accountmanagement.request.SundaySchoolClassRequest;
import com.accountmanagement.request.SundaySchoolClassUpdateRequest;
import com.accountmanagement.request.SundaySchoolStudentRequest;
import com.accountmanagement.request.SundaySchoolStudentUpdateRequest;
import com.accountmanagement.request.SundaySchoolTeacherRequest;
import com.accountmanagement.request.SundaySchoolTeacherUpdateRequest;
import com.accountmanagement.request.SundaySchoolTransitionRequest;
import com.accountmanagement.response.ApiResponse;
import com.accountmanagement.service.SundaySchoolAttendanceService;
import com.accountmanagement.service.SundaySchoolClassService;
import com.accountmanagement.service.SundaySchoolStudentService;
import com.accountmanagement.service.SundaySchoolTeacherService;
import com.accountmanagement.service.SundaySchoolTransitionService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "/v1/sunday/school")
@Tag(name = "SundaySchoolController")
@RequiredArgsConstructor
public class SundaySchoolController {

        private final SundaySchoolClassService sundaySchoolClassService;

        private final SundaySchoolStudentService sundaySchoolStudentService;

        private final SundaySchoolTeacherService sundaySchoolTeacherService;

        private final SundaySchoolTransitionService sundaySchoolTransitionService;

        private final SundaySchoolAttendanceService sundaySchoolAttendanceService;

        @PostMapping("/class/create")
        public ResponseEntity<ApiResponse> addSundaySchoolClass(
                        @Valid @RequestBody SundaySchoolClassRequest sundaySchoolClassRequest) {
                sundaySchoolClassRequest.sanitizeInput();
                sundaySchoolClassService.addSundaySchoolClass(sundaySchoolClassRequest);
                ApiResponse response = new ApiResponse(AppConstants.SUCCESS,
                                SundaySchoolClassMessage.ADD_SUNDAY_SCHOOL_CLASS,
                                201);
                return new ResponseEntity<>(response, HttpStatus.CREATED);
        }

        @PutMapping("/class/update/{id}")
        public ResponseEntity<ApiResponse> updateSundaySchoolClass(@PathVariable UUID id,
                        @Valid @RequestBody SundaySchoolClassUpdateRequest sundaySchoolClassRequest) {
                sundaySchoolClassRequest.sanitizeInput();
                sundaySchoolClassService.updateSundaySchoolClass(id, sundaySchoolClassRequest);
                ApiResponse response = new ApiResponse(AppConstants.SUCCESS,
                                SundaySchoolClassMessage.UPDATE_SUNDAY_SCHOOL_CLASS,
                                200);
                return new ResponseEntity<>(response, HttpStatus.OK);
        }

        @DeleteMapping("/class/delete/{id}")
        public ResponseEntity<ApiResponse> deleteSundaySchoolClassById(@PathVariable UUID id) {
                sundaySchoolClassService.deleteSundaySchoolClassById(id);
                ApiResponse response = new ApiResponse(AppConstants.SUCCESS,
                                SundaySchoolClassMessage.DELETE_SUNDAY_SCHOOL_CLASS, 200);
                return new ResponseEntity<>(response, HttpStatus.OK);
        }

        @GetMapping("/class/view")
        public ResponseEntity<ApiResponse> viewAllSundaySchoolClass() {
                List<SundaySchoolClass> sundaySchoolClass = sundaySchoolClassService.viewAllSundaySchoolClasses();
                ApiResponse response = new ApiResponse(AppConstants.SUCCESS,
                                SundaySchoolClassMessage.VIEW_ALL_SUNDAY_SCHOOL_CLASS, 200);
                response.setData(sundaySchoolClass);
                return new ResponseEntity<>(response, HttpStatus.OK);
        }

        @PostMapping("/student/create")
        public ResponseEntity<ApiResponse> addSundaySchoolStudent(
                        @Valid @RequestBody SundaySchoolStudentRequest sundaySchoolStudentRequest) {
                sundaySchoolStudentRequest.sanitizeInput();
                sundaySchoolStudentService.addSundaySchoolStudent(sundaySchoolStudentRequest);
                ApiResponse response = new ApiResponse(AppConstants.SUCCESS,
                                SundaySchoolStudentMessage.ADD_SUNDAY_SCHOOL_STUDENT, 201);
                return new ResponseEntity<>(response, HttpStatus.CREATED);
        }

        @PutMapping("/student/update/{id}")
        public ResponseEntity<ApiResponse> updateSundaySchoolStudent(@PathVariable UUID id,
                        @Valid @RequestBody SundaySchoolStudentUpdateRequest sundaySchoolStudentUpdateRequest) {
                sundaySchoolStudentUpdateRequest.sanitizeInput();
                sundaySchoolStudentService.updateSundaySchoolStudent(id, sundaySchoolStudentUpdateRequest);
                ApiResponse response = new ApiResponse(AppConstants.SUCCESS,
                                SundaySchoolStudentMessage.UPDATE_SUNDAY_SCHOOL_STUDENT, 201);
                return new ResponseEntity<>(response, HttpStatus.OK);
        }

        @DeleteMapping("/student/delete/{id}")
        public ResponseEntity<ApiResponse> deleteSundaySchoolStudentbyId(@PathVariable UUID id) {
                sundaySchoolStudentService.deleteSundaySchoolStudentById(id);
                ApiResponse response = new ApiResponse(AppConstants.SUCCESS,
                                SundaySchoolStudentMessage.DELETE_SUNDAY_SCHOOL_STUDENT, 200);
                return new ResponseEntity<>(response, HttpStatus.OK);
        }

        @GetMapping("/student/view")
        public ResponseEntity<ApiResponse> viewAllSundaySchoolStudent() {
                List<SundaySchoolStudent> sundaySchoolStudent = sundaySchoolStudentService.viewAllSundaySchoolStudent();
                ApiResponse response = new ApiResponse(AppConstants.SUCCESS,
                                SundaySchoolStudentMessage.VIEW_ALL_SUNDAY_SCHOOL_STUDENT, 200);
                response.setData(sundaySchoolStudent);
                return new ResponseEntity<>(response, HttpStatus.OK);
        }

        @PostMapping("/teacher/create")
        public ResponseEntity<ApiResponse> addSundaySchoolTeacher(
                        @Valid @RequestBody SundaySchoolTeacherRequest sundaySchoolTeacherRequest) {
                sundaySchoolTeacherService.addSundaySchoolTeacher(sundaySchoolTeacherRequest);
                ApiResponse response = new ApiResponse(AppConstants.SUCCESS,
                                SundaySchoolTeacherMessage.ADD_SUNDAY_SCHOOL_TEACHER,
                                201);
                return new ResponseEntity<>(response, HttpStatus.CREATED);
        }

        @PutMapping("/teacher/update/{id}")
        public ResponseEntity<ApiResponse> updateSundaySchoolTeacher(@PathVariable UUID id,
                        @Valid @RequestBody SundaySchoolTeacherUpdateRequest sundaySchoolTeacherUpdateRequest) {
                sundaySchoolTeacherService.updateSundaySchoolTeacher(id, sundaySchoolTeacherUpdateRequest);
                ApiResponse response = new ApiResponse(AppConstants.SUCCESS,
                                SundaySchoolTeacherMessage.UPDATE_SUNDAY_SCHOOL_TEACHER,
                                200);
                return new ResponseEntity<>(response, HttpStatus.OK);
        }

        @DeleteMapping("/teacher/delete/{id}")
        public ResponseEntity<ApiResponse> deleteSundaySchoolTeacherById(@PathVariable UUID id) {
                sundaySchoolTeacherService.deleteSundaySchoolTeacherById(id);
                ApiResponse response = new ApiResponse(AppConstants.SUCCESS,
                                SundaySchoolTeacherMessage.DELETE_SUNDAY_SCHOOL_TEACHER,
                                200);
                return new ResponseEntity<>(response, HttpStatus.OK);
        }

        @GetMapping("/teacher/view")
        public ResponseEntity<ApiResponse> viewAllSundaySchoolTeachers() {
                List<SundaySchoolTeacher> sundaySchoolTeacher = sundaySchoolTeacherService
                                .viewAllSundaySchoolTeachers();
                ApiResponse response = new ApiResponse(AppConstants.SUCCESS,
                                SundaySchoolTeacherMessage.VIEW_ALL_SUNDAY_SCHOOL_TEACHER,
                                200);
                response.setData(sundaySchoolTeacher);
                return new ResponseEntity<>(response, HttpStatus.OK);
        }

        @PostMapping("/transition/create")
        public ResponseEntity<ApiResponse> addSundaySchoolTransition(
                        @Valid @RequestBody SundaySchoolTransitionRequest sundaySchoolTransitionRequest) {
                sundaySchoolTransitionService.addSundaySchoolTransition(sundaySchoolTransitionRequest);
                ApiResponse response = new ApiResponse(AppConstants.SUCCESS,
                                SundaySchoolTransitionMessage.ADD_SUNDAY_SCHOOL_TRANSITION, 201);
                return new ResponseEntity<>(response, HttpStatus.CREATED);
        }

        @PutMapping("/transition/update/{id}")
        public ResponseEntity<ApiResponse> updateSundaySchoolTransition(@PathVariable UUID id,
                        @Valid @RequestBody SundaySchoolTransitionRequest sundaySchoolTransitionRequest) {
                sundaySchoolTransitionService.updateSundaySchoolTransition(id, sundaySchoolTransitionRequest);
                ApiResponse response = new ApiResponse(AppConstants.SUCCESS,
                                SundaySchoolTransitionMessage.UPDATE_SUNDAY_SCHOOL_TRANSITION, 200);
                return new ResponseEntity<>(response, HttpStatus.OK);
        }

        @DeleteMapping("/transition/delete/{id}")
        public ResponseEntity<ApiResponse> deleteSundaySchoolTransitionById(@PathVariable UUID id) {
                sundaySchoolTransitionService.deleteSundaySchoolTransitionById(id);
                ApiResponse response = new ApiResponse(AppConstants.SUCCESS,
                                SundaySchoolTransitionMessage.DELETE_SUNDAY_SCHOOL_TRANSITION, 200);
                return new ResponseEntity<>(response, HttpStatus.OK);
        }

        @GetMapping("/transition/view")
        public ResponseEntity<ApiResponse> viewAllSundaySchoolTransition() {
                List<SundaySchoolTransition> sundaySchoolTransition = sundaySchoolTransitionService
                                .viewAllSundaySchoolTransition();
                ApiResponse response = new ApiResponse(AppConstants.SUCCESS,
                                SundaySchoolTransitionMessage.VIEW_ALL_SUNDAY_SCHOOL_TRANSITION, 200);
                response.setData(sundaySchoolTransition);
                return new ResponseEntity<>(response, HttpStatus.OK);
        }

        @PostMapping("/attendance/create")
        public ResponseEntity<ApiResponse> addSundaySchoolAttendance(
                        @Valid @RequestBody SundaySchoolAttendanceRequest sundaySchoolAttendanceRequest) {
                sundaySchoolAttendanceService.saveAttendance(sundaySchoolAttendanceRequest);
                ApiResponse response = new ApiResponse(AppConstants.SUCCESS,
                                SundaySchoolAttendanceMessage.ADD_SUNDAY_SCHOOL_ATTENDANCE, 201);
                return new ResponseEntity<>(response, HttpStatus.CREATED);
        }

        @PutMapping("/attendance/update/{id}")
        public ResponseEntity<ApiResponse> updateSundaySchoolAttendance(@PathVariable UUID id,
                        @Valid @RequestBody AttendanceUpdateRequest request) {
                sundaySchoolAttendanceService.updateSundaySchoolAttendance(id, request);
                ApiResponse response = new ApiResponse(AppConstants.SUCCESS,
                                SundaySchoolAttendanceMessage.UPDATE_SUNDAY_SCHOOL_ATTENDANCE, 200);
                return new ResponseEntity<>(response, HttpStatus.OK);
        }

        @DeleteMapping("/attendance/delete/{id}")
        public ResponseEntity<ApiResponse> deleteSundaySchoolAttendanceById(@PathVariable UUID id) {
                sundaySchoolAttendanceService.deleteSundaySchoolAttendanceById(id);
                ApiResponse response = new ApiResponse(AppConstants.SUCCESS,
                                SundaySchoolAttendanceMessage.DELETE_SUNDAY_SCHOOL_ATTENDANCE, 200);
                return new ResponseEntity<>(response, HttpStatus.OK);
        }

        @GetMapping("/attendance/view")
        public ResponseEntity<ApiResponse> viewAllSundaySchoolAttendances() {
                List<SundaySchoolAttendance> sundaySchoolAttendance = sundaySchoolAttendanceService
                                .viewAllSundaySchoolAttendance();
                ApiResponse response = new ApiResponse(AppConstants.SUCCESS,
                                SundaySchoolAttendanceMessage.VIEW_ALL_SUNDAY_SCHOOL_ATTENDANCE, 200);
                response.setData(sundaySchoolAttendance);
                return new ResponseEntity<>(response, HttpStatus.OK);
        }

}
