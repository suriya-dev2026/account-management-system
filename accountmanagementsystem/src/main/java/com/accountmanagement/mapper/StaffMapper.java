package com.accountmanagement.mapper;

import org.springframework.stereotype.Component;
import com.accountmanagement.model.Staff;
import com.accountmanagement.model.User;
import com.accountmanagement.model.UserProfile;
import com.accountmanagement.request.StaffRequest;
import com.accountmanagement.request.StaffUpdateRequest;

@Component
public class StaffMapper {

    public Staff toCreateStaff(User user, UserProfile userProfile, StaffRequest staffRequest) {
        Staff staff = new Staff();
        staff.setOrganizationId(staffRequest.getOrganizationId());
        staff.setStaffType(staffRequest.getStaffType());
        staff.setMemberId(staffRequest.getMemberId());
        staff.setQualification(staffRequest.getQualification());
        staff.setDesignation(staffRequest.getDesignation());
        staff.setAge(staffRequest.getAge());
        staff.setDateOfJoin(staffRequest.getDateOfJoin());
        staff.setIsWaterBaptised(staffRequest.getIsWaterBaptised());
        staff.setIsSpiritBaptised(staffRequest.getIsSpiritBaptised());
        if (user != null) {
            staff.setUserId(user.getId());
            staff.setFirstName(userProfile.getFirstName());
            staff.setLastName(userProfile.getLastName());
            staff.setDateOfBirth(userProfile.getDateOfBirth());
            staff.setContactNumber(user.getContactNumber());
            staff.setGender(userProfile.getGender());
        } else {
            staff.setUserId(staffRequest.getUserId());
            staff.setFirstName(staffRequest.getFirstName());
            staff.setLastName(staffRequest.getLastName());
            staff.setDateOfBirth(staffRequest.getDateOfBirth());
            staff.setContactNumber(staffRequest.getContactNumber());
        }
        return staff;
    }

    public Staff toUpdateStaff(Staff staff, StaffUpdateRequest staffUpdateRequest) {
        staff.setQualification(staffUpdateRequest.getQualification());
        staff.setDesignation(staffUpdateRequest.getDesignation());
        staff.setIsWaterBaptised(staffUpdateRequest.getIsWaterBaptised());
        staff.setIsSpiritBaptised(staffUpdateRequest.getIsSpiritBaptised());
        return staff;
    }
}
