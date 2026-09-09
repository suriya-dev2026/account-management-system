package com.accountmanagement.service;

import com.accountmanagement.repository.UserRepository;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.accountmanagement.constants.AppConstants;
import com.accountmanagement.constants.message.StaffMessage;
import com.accountmanagement.exceptions.DuplicateRecordException;
import com.accountmanagement.exceptions.RecordNotFoundException;
import com.accountmanagement.mapper.StaffMapper;
import com.accountmanagement.model.Staff;
import com.accountmanagement.model.User;
import com.accountmanagement.model.UserProfile;
import com.accountmanagement.repository.StaffRepository;
import com.accountmanagement.repository.UserProfileRepository;
import com.accountmanagement.request.StaffRequest;
import com.accountmanagement.request.StaffUpdateRequest;

@Service
public class StaffService {

    private final UserRepository userRepository;

    private final UserProfileRepository userProfileRepository;

    private final StaffRepository staffRepository;

    private final StaffMapper staffMapper;

    public StaffService(StaffRepository staffRepository, StaffMapper staffMapper, UserRepository userRepository,
            UserProfileRepository userProfileRepository) {
        this.staffRepository = staffRepository;
        this.staffMapper = staffMapper;
        this.userRepository = userRepository;
        this.userProfileRepository = userProfileRepository;
    }

    public Staff createStaff(StaffRequest staffRequest) {
        User user = null;
        UserProfile userProfile = null;
        if (staffRequest.getUserId() != null) {
            user = userRepository.findById(staffRequest.getUserId())
                    .orElseThrow(() -> new RecordNotFoundException("User Id Not Found"));
            userProfile = userProfileRepository.findByUserId(user.getId())
                    .orElseThrow(() -> new RecordNotFoundException("User Profile Not Found"));
        }
        Staff staff = staffMapper.toCreateStaff(user, userProfile, staffRequest);
        validateStaff(staffRequest.getUserId());
        return staffRepository.save(staff);
    }

    public Staff updateStaff(UUID id, StaffUpdateRequest staffUpdateRequest) {
        Staff staff = findByStaffId(id);
        Staff updatedStaff = staffMapper.toUpdateStaff(staff, staffUpdateRequest);
        return staffRepository.save(updatedStaff);
    }

    public void deleteStaffById(UUID id) {
        Staff staff = findByStaffId(id);
        staff.setStatus(AppConstants.INACTIVE);
        staffRepository.save(staff);
    }

    public List<Staff> viewAllStaffs() {
        return staffRepository.findAll();
    }

    public Staff findByStaffId(UUID id) {
        return staffRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(StaffMessage.STAFF_ID_NOT_FOUND));
    }

    public void validateStaff(UUID userId) {
        boolean exists = staffRepository.existsByUserId(userId);
        if (exists) {
            throw new DuplicateRecordException(StaffMessage.STAFF_EXISTS);
        }
    }

}
