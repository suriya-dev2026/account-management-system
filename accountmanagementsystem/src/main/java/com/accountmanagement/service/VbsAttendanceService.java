package com.accountmanagement.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import org.springframework.stereotype.Service;
import com.accountmanagement.exceptions.DuplicateRecordException;
import com.accountmanagement.exceptions.RecordNotFoundException;
import com.accountmanagement.model.VbsAttendance;
import com.accountmanagement.repository.VbsAttendanceRepository;
import com.accountmanagement.request.AttendanceUpdateRequest;
import com.accountmanagement.request.VbsAttendanceList;
import com.accountmanagement.request.VbsAttendanceRequest;

@Service
public class VbsAttendanceService {

    public final VbsAttendanceRepository vbsAttendanceRepository;

    public VbsAttendanceService(VbsAttendanceRepository vbsAttendanceRepository) {
        this.vbsAttendanceRepository = vbsAttendanceRepository;
    }

    public List<VbsAttendance> createVbsAttendance(VbsAttendanceRequest request) {
        List<VbsAttendance> attendanceList = new ArrayList<>();
        Set<UUID> studentIds = new HashSet<>();
        for (VbsAttendanceList item : request.getAttendanceList()) {
            if (!studentIds.add(item.getStudentId())) {
                throw new DuplicateRecordException(
                        "Duplicate Student Id Found In The Request.");
            }
            if (vbsAttendanceRepository.existsByOrganizationIdAndStudentIdAndAttendanceDate(
                    request.getOrganizationId(), item.getStudentId(), request.getAttendanceDate())) {
                throw new DuplicateRecordException(
                        "Attendance Record already exists for this student.");
            }
            VbsAttendance attendance = new VbsAttendance();
            attendance.setOrganizationId(request.getOrganizationId());
            attendance.setStudentId(item.getStudentId());
            attendance.setAttendanceDate(request.getAttendanceDate());
            attendance.setAttendanceStatus(item.getAttendanceStatus());
            attendanceList.add(attendance);
        }
        return vbsAttendanceRepository.saveAll(attendanceList);
    }

    public VbsAttendance updateVbsAttendance(UUID id, AttendanceUpdateRequest vbsAttendanceUpdateRequest) {
        VbsAttendance vbsAttendance = findVbsAttendanceById(id);
        vbsAttendance.setAttendanceStatus(vbsAttendanceUpdateRequest.getAttendanceStatus());
        return vbsAttendanceRepository.save(vbsAttendance);
    }

    public VbsAttendance findVbsAttendanceById(UUID id) {
        return vbsAttendanceRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Vbs Attendance Id Not Found"));
    }

    public void deleteVbsAttendanceById(UUID id) {
        findVbsAttendanceById(id);
        vbsAttendanceRepository.deleteById(id);
    }

    public List<VbsAttendance> viewAllVbsAttendance() {
        return vbsAttendanceRepository.findAll();
    }
}
