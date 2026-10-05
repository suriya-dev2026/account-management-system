package com.accountmanagement.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.accountmanagement.constants.message.OrganizationOfferingTypeMessage;
import com.accountmanagement.exceptions.DuplicateRecordException;
import com.accountmanagement.exceptions.RecordNotFoundException;
import com.accountmanagement.model.OrganizationOfferingType;
import com.accountmanagement.repository.OrganizationOfferingTypeRepository;
import com.accountmanagement.request.OrganizationOfferingTypeRequest;

@Service
public class OrganizationOfferingTypeService {

    private final OrganizationOfferingTypeRepository organizationOfferingTypeRepository;

    public OrganizationOfferingTypeService(OrganizationOfferingTypeRepository organizationOfferingTypeRepository) {
        this.organizationOfferingTypeRepository = organizationOfferingTypeRepository;
    }

    public OrganizationOfferingType createOrganizationOfferingType(
            OrganizationOfferingTypeRequest organizationOfferingTypeRequest) {
        validateTypeName(organizationOfferingTypeRequest.getTypeName());
        OrganizationOfferingType organizationOfferingtype = new OrganizationOfferingType();
        organizationOfferingtype.setOrganizationId(organizationOfferingTypeRequest.getOrganizationId());
        organizationOfferingtype.setAccountId(organizationOfferingTypeRequest.getAccountId());
        organizationOfferingtype.setTypeName(organizationOfferingTypeRequest.getTypeName());
        return organizationOfferingTypeRepository.save(organizationOfferingtype);
    }

    public OrganizationOfferingType updateOrganizationOfferingType(Integer id,
            OrganizationOfferingTypeRequest organizationOfferingTypeRequest) {
        OrganizationOfferingType organizationOfferingType = findOrganizationOfferingTypeById(id);
        organizationOfferingType.setOrganizationId(organizationOfferingTypeRequest.getOrganizationId());
        organizationOfferingType.setAccountId(organizationOfferingTypeRequest.getAccountId());
        organizationOfferingType.setTypeName(organizationOfferingTypeRequest.getTypeName());
        return organizationOfferingTypeRepository.save(organizationOfferingType);
    }

    public void deleteOrganizationOfferingTypeById(Integer id) {
        findOrganizationOfferingTypeById(id);
        deleteOrganizationOfferingTypeById(id);
    }

    public List<OrganizationOfferingType> viewAllOrganizationOfferingTypes() {
        return organizationOfferingTypeRepository.findAll();
    }

    public OrganizationOfferingType findOrganizationOfferingTypeById(Integer id) {
        return organizationOfferingTypeRepository.findById(id).orElseThrow(() -> new RecordNotFoundException(""));
    }

    public void validateTypeName(String typeName) {
        boolean exists = organizationOfferingTypeRepository.existsByTypeName(typeName);
        if (exists) {
            throw new DuplicateRecordException(OrganizationOfferingTypeMessage.ORGANIZATION_OFFERING_TYPE_EXISTS);
        }
    }
}
