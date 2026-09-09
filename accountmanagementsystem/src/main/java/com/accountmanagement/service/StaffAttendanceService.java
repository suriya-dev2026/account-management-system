package com.accountmanagement.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import org.springframework.stereotype.Service;
import com.accountmanagement.exceptions.DuplicateRecordException;
import com.accountmanagement.exceptions.RecordNotFoundException;
import com.accountmanagement.model.StaffAttendance;
import com.accountmanagement.repository.StaffAttendanceRepository;
import com.accountmanagement.request.StaffAttendanceList;
import com.accountmanagement.request.StaffAttendanceRequest;
import com.accountmanagement.request.StaffAttendanceUpdateRequest;

@Service
public class StaffAttendanceService {

    private final StaffAttendanceRepository staffAttendanceRepository;

    public StaffAttendanceService(StaffAttendanceRepository staffAttendanceRepository) {
        this.staffAttendanceRepository = staffAttendanceRepository;
    }

    public List<StaffAttendance> createStaffAttendance(StaffAttendanceRequest request) {
        validateDuplicateStaffId(request.getAttendanceList());
        validateExistingAttendance(request);
        List<StaffAttendance> attendanceList = toCreateAttendance(request);
        return staffAttendanceRepository.saveAll(attendanceList);
    }

    private void validateDuplicateStaffId(List<StaffAttendanceList> attendanceList) {
        Set<UUID> staffIds = new HashSet<>();
        for (StaffAttendanceList item : attendanceList) {
            if (!staffIds.add(item.getStaffId())) {
                throw new DuplicateRecordException("Duplicate Staff Id Found In The Request");
            }
        }
    }

    private void validateExistingAttendance(StaffAttendanceRequest request) {
        for (StaffAttendanceList item : request.getAttendanceList()) {
            boolean exists = staffAttendanceRepository
                    .existsByOrganizationIdAndStaffIdAndAttendanceDate(
                            request.getOrganizationId(),
                            item.getStaffId(),
                            request.getAttendanceDate());
            if (exists) {
                throw new DuplicateRecordException(
                        "Attendance Record already exists for this staff.");
            }
        }
    }

    private List<StaffAttendance> toCreateAttendance(StaffAttendanceRequest request) {

        List<StaffAttendance> attendanceList = new ArrayList<>();
        for (StaffAttendanceList item : request.getAttendanceList()) {
            StaffAttendance staffAttendance = new StaffAttendance();
            staffAttendance.setOrganizationId(request.getOrganizationId());
            staffAttendance.setAttendanceDate(request.getAttendanceDate());
            staffAttendance.setStaffId(item.getStaffId());
            staffAttendance.setAttendanceStatus(item.getAttendanceStatus());
            attendanceList.add(staffAttendance);
        }
        return attendanceList;
    }

    public StaffAttendance updateStaffAttendance(UUID id, StaffAttendanceUpdateRequest staffAttendanceRequest) {
        StaffAttendance attendance = findStaffAttendanceById(id);
        attendance.setAttendanceStatus(staffAttendanceRequest.getAttendanceStatus());
        return staffAttendanceRepository.save(attendance);
    }

    public void deleteStaffAttendanceById(UUID id) {
        findStaffAttendanceById(id);
        staffAttendanceRepository.deleteById(id);
    }

    public List<StaffAttendance> viewAllStaffAttendances() {
        return staffAttendanceRepository.findAll();
    }

    public StaffAttendance findStaffAttendanceById(UUID Id) {
        return staffAttendanceRepository.findById(Id)
                .orElseThrow(() -> new RecordNotFoundException("Staff Attendance Id Not Found"));
    }

}
