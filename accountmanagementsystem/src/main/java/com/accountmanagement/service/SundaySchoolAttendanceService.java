package com.accountmanagement.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.accountmanagement.constants.message.SundaySchoolAttendanceMessage;
import com.accountmanagement.exceptions.DuplicateRecordException;
import com.accountmanagement.exceptions.RecordNotFoundException;
import com.accountmanagement.model.SundaySchoolAttendance;
import com.accountmanagement.repository.SundaySchoolAttendanceRepository;
import com.accountmanagement.request.AttendanceItemRequest;
import com.accountmanagement.request.AttendanceUpdateRequest;
import com.accountmanagement.request.SundaySchoolAttendanceRequest;

@Service
public class SundaySchoolAttendanceService {

    private final SundaySchoolAttendanceRepository sundaySchoolAttendanceRepository;

    public SundaySchoolAttendanceService(SundaySchoolAttendanceRepository sundaySchoolAttendanceRepository) {
        this.sundaySchoolAttendanceRepository = sundaySchoolAttendanceRepository;
    }

    @Transactional
    public List<SundaySchoolAttendance> saveAttendance(SundaySchoolAttendanceRequest request) {

        List<SundaySchoolAttendance> attendanceList = new ArrayList<>();
        Set<UUID> studentIds = new HashSet<>();
        for (AttendanceItemRequest item : request.getAttendanceList()) {
            if (!studentIds.add(item.getStudentId())) {
                throw new DuplicateRecordException(
                        SundaySchoolAttendanceMessage.DUPLICATE_STUDENT_ID);
            }
            if (sundaySchoolAttendanceRepository.existsByOrganizationIdAndStudentIdAndAttendanceDate(
                    request.getOrganizationId(), item.getStudentId(), request.getAttendanceDate())) {
                throw new DuplicateRecordException(
                        SundaySchoolAttendanceMessage.ATTENDANCE_ALREADY_EXISTS);
            }
            SundaySchoolAttendance attendance = new SundaySchoolAttendance();
            attendance.setOrganizationId(request.getOrganizationId());
            attendance.setStudentId(item.getStudentId());
            attendance.setAttendanceDate(request.getAttendanceDate());
            attendance.setAttendanceStatus(item.getAttendanceStatus());
            attendanceList.add(attendance);
        }
        return sundaySchoolAttendanceRepository.saveAll(attendanceList);
    }

    public SundaySchoolAttendance updateSundaySchoolAttendance(UUID id, AttendanceUpdateRequest request) {
        SundaySchoolAttendance sundaySchoolAttendance = findBySundaySchoolAttendanceById(id);
        sundaySchoolAttendance.setAttendanceStatus(request.getAttendanceStatus());
        return sundaySchoolAttendanceRepository.save(sundaySchoolAttendance);
    }

    public SundaySchoolAttendance findBySundaySchoolAttendanceById(UUID id) {
        return sundaySchoolAttendanceRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(
                        SundaySchoolAttendanceMessage.SUNDAY_SCHOOL_ATTENDANCE_ID_NOT_FOUND));
    }

    public void deleteSundaySchoolAttendanceById(UUID id) {
        findBySundaySchoolAttendanceById(id);
        sundaySchoolAttendanceRepository.deleteById(id);
    }

    public List<SundaySchoolAttendance> viewAllSundaySchoolAttendance() {
        return sundaySchoolAttendanceRepository.findAll();
    }
}
