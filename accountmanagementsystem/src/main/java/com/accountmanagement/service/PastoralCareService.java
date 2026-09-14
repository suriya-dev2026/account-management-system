package com.accountmanagement.service;

import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;
import com.accountmanagement.constants.AppConstants;
import com.accountmanagement.constants.message.MemberMessage;
import com.accountmanagement.constants.message.PastoralCareMessage;
import com.accountmanagement.exceptions.DuplicateRecordException;
import com.accountmanagement.exceptions.RecordNotFoundException;
import com.accountmanagement.mapper.PastoralCareMapper;
import com.accountmanagement.model.Member;
import com.accountmanagement.model.PastoralCare;
import com.accountmanagement.repository.MemberRepository;
import com.accountmanagement.repository.PastoralCareRepository;
import com.accountmanagement.request.PastoralCareRequest;
import com.accountmanagement.request.PastoralCareUpdateRequest;

@Service
public class PastoralCareService {

    private final PastoralCareRepository pastoralCareRepository;

    private final PastoralCareMapper pastoralCareMapper;

    private final MemberRepository memberRepository;

    public PastoralCareService(PastoralCareRepository pastoralCareRepository, PastoralCareMapper pastoralCareMapper,
            MemberRepository memberRepository) {
        this.pastoralCareRepository = pastoralCareRepository;
        this.pastoralCareMapper = pastoralCareMapper;
        this.memberRepository = memberRepository;
    }

    public PastoralCare createPastoralCare(PastoralCareRequest pastoralCareRequest) {
        validatePastoralCare(pastoralCareRequest);
        Member member = null;
        if (pastoralCareRequest.getMemberId() != null) {
            member = memberRepository.findById(pastoralCareRequest.getMemberId())
                    .orElseThrow(() -> new RecordNotFoundException(MemberMessage.MEMBER_NOT_FOUND));
        }
        PastoralCare pastoralCare = pastoralCareMapper.toAddPastoralCare(member, pastoralCareRequest);
        return pastoralCareRepository.save(pastoralCare);
    }

    public PastoralCare updatePastoralCare(UUID id, PastoralCareUpdateRequest pastoralCareUpdateRequest) {
        PastoralCare pastoralCare = findPastoralCareById(id);
        PastoralCare updatedPastoralCare = pastoralCareMapper.toUpdatePastoralCare(pastoralCare,
                pastoralCareUpdateRequest);
        return pastoralCareRepository.save(updatedPastoralCare);
    }

    public void deletePastoralCareById(UUID id) {
        PastoralCare pastoralCare = findPastoralCareById(id);
        pastoralCare.setStatus(AppConstants.INACTIVE);
        pastoralCareRepository.save(pastoralCare);
    }

    public List<PastoralCare> viewAllPastoralCare() {
        return pastoralCareRepository.findAll();
    }

    public PastoralCare findPastoralCareById(UUID id) {
        return pastoralCareRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(PastoralCareMessage.PASTORAL_CARE_ID_NOT_FOUND));
    }

    public void validatePastoralCare(PastoralCareRequest pastoralCareRequest) {
        Boolean exists = pastoralCareRepository.existsByVisitorNameAndVisitorContactNumber(
                pastoralCareRequest.getVisitorName(),
                pastoralCareRequest.getVisitorContactNumber());
        if (exists) {
            throw new DuplicateRecordException(PastoralCareMessage.PASTORAL_CARE_EXISTS);
        }
    }
}
