package com.accountmanagement.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.accountmanagement.constants.message.MeetingTypeMessage;
import com.accountmanagement.exceptions.DuplicateRecordException;
import com.accountmanagement.exceptions.RecordNotFoundException;
import com.accountmanagement.model.MeetingType;
import com.accountmanagement.repository.MeetingTypeRepository;
import com.accountmanagement.request.MeetingTypeRequest;

@Service
public class MeetingTypeService {

    private final MeetingTypeRepository meetingTypeRepository;

    public MeetingTypeService(MeetingTypeRepository meetingTypeRepository) {
        this.meetingTypeRepository = meetingTypeRepository;
    }

    public MeetingType createMeetingType(MeetingTypeRequest meetingTypeRequest) {
        validateCategory(meetingTypeRequest.getCategory());
        MeetingType meetingType = new MeetingType();
        meetingType.setCategory(meetingTypeRequest.getCategory());
        meetingType.setDescription(meetingTypeRequest.getDescription());
        return meetingTypeRepository.save(meetingType);
    }

    public MeetingType updateMeetingType(Integer id, MeetingTypeRequest meetingTypeRequest) {
        MeetingType meetingType = findMeetingTypeById(id);
        meetingType.setCategory(meetingTypeRequest.getCategory());
        meetingType.setDescription(meetingTypeRequest.getDescription());
        return meetingTypeRepository.save(meetingType);
    }

    public void deleteMeetingTypeById(Integer id) {
        findMeetingTypeById(id);
        meetingTypeRepository.deleteById(id);
    }

    public List<MeetingType> viewAllMeetingTypes() {
        return meetingTypeRepository.findAll();
    }

    public MeetingType findMeetingTypeById(Integer id) {
        return meetingTypeRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(MeetingTypeMessage.MEETING_TYPE_ID_NOT_FOUND));
    }

    public void validateCategory(String category) {
        boolean exists = meetingTypeRepository.existsByCategory(category);
        if (exists) {
            throw new DuplicateRecordException(MeetingTypeMessage.MEETING_TYPE_CATEGORY_EXISTS);
        }
    }
}
