package com.accountmanagement.mapper;

import org.springframework.stereotype.Component;
import com.accountmanagement.model.Member;
import com.accountmanagement.model.PastoralCare;
import com.accountmanagement.request.PastoralCareRequest;
import com.accountmanagement.request.PastoralCareUpdateRequest;

@Component
public class PastoralCareMapper {

    public PastoralCare toAddPastoralCare(Member member, PastoralCareRequest pastoralCareRequest) {
        PastoralCare pastoralCare = new PastoralCare();
        pastoralCare.setOrganizationId(pastoralCareRequest.getOrganizationId());
        pastoralCare.setMemberId(pastoralCareRequest.getMemberId());
        pastoralCare.setVisitDate(pastoralCareRequest.getVisitDate());
        pastoralCare.setVisitType(pastoralCareRequest.getVisitType());
        pastoralCare.setNotes(pastoralCareRequest.getNotes());
        if (member != null) {
            pastoralCare.setVisitorName(member.getUserName());
            pastoralCare.setVisitorContactNumber(member.getContactNumber());
        } else {
            pastoralCare.setVisitorName(pastoralCareRequest.getVisitorName());
            pastoralCare.setVisitorContactNumber(pastoralCareRequest.getVisitorContactNumber());
        }
        return pastoralCare;
    }

    public PastoralCare toUpdatePastoralCare(PastoralCare pastoralCare,
            PastoralCareUpdateRequest pastoralCareUpdateRequest) {
        pastoralCare.setVisitorName(pastoralCareUpdateRequest.getVisitorName());
        pastoralCare.setVisitorContactNumber(pastoralCareUpdateRequest.getVisitorContactNumber());
        pastoralCare.setVisitType(pastoralCareUpdateRequest.getVisitType());
        pastoralCare.setVisitDate(pastoralCareUpdateRequest.getVisitDate());
        pastoralCare.setNotes(pastoralCareUpdateRequest.getNotes());
        return pastoralCare;
    }
}
