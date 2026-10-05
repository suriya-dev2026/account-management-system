package com.accountmanagement.service;

import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;
import com.accountmanagement.constants.message.OrganizationExpenseTypeMessage;
import com.accountmanagement.exceptions.DuplicateRecordException;
import com.accountmanagement.exceptions.RecordNotFoundException;
import com.accountmanagement.model.OrganizationExpenseType;
import com.accountmanagement.repository.OrganizationExpenseTypeRepository;
import com.accountmanagement.request.OrganizationExpenseTypeRequest;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrganizationExpenseTypeService {

    private final OrganizationExpenseTypeRepository organizationExpenseTypeRepository;

    public OrganizationExpenseType createOrganizationExpenseType(
            OrganizationExpenseTypeRequest organizationExpenseTypeRequest) {
        validateTypeName(organizationExpenseTypeRequest.getOrganizationId(),
                organizationExpenseTypeRequest.getTypeName());
        OrganizationExpenseType organizationExpenseType = new OrganizationExpenseType();
        organizationExpenseType.setOrganizationId(organizationExpenseTypeRequest.getOrganizationId());
        organizationExpenseType.setAccountId(organizationExpenseTypeRequest.getAccountId());
        organizationExpenseType.setTypeName(organizationExpenseTypeRequest.getTypeName());
        return organizationExpenseTypeRepository.save(organizationExpenseType);
    }

    public OrganizationExpenseType updateOrganizationExpenseType(Integer id,
            OrganizationExpenseTypeRequest organizationExpenseTypeRequest) {
        OrganizationExpenseType organizationExpenseType = findOrganizationExpenseTypeById(id);
        organizationExpenseType.setAccountId(organizationExpenseTypeRequest.getAccountId());
        organizationExpenseType.setTypeName(organizationExpenseTypeRequest.getTypeName());
        return organizationExpenseTypeRepository.save(organizationExpenseType);
    }

    public void deleteOrganizationExpenseTypeById(Integer id) {
        findOrganizationExpenseTypeById(id);
        organizationExpenseTypeRepository.deleteById(id);
    }

    public List<OrganizationExpenseType> viewAllOrganizationExpenseTypes() {
        return organizationExpenseTypeRepository.findAll();
    }

    public OrganizationExpenseType findOrganizationExpenseTypeById(Integer id) {
        return organizationExpenseTypeRepository.findById(id).orElseThrow(() -> new RecordNotFoundException(
                OrganizationExpenseTypeMessage.ORGANIZATION_EXPENSE_TYPE_ID_NOT_FOUND));
    }

    public void validateTypeName(UUID organizationId, String typeName) {
        boolean exists = organizationExpenseTypeRepository.existsByOrganizationIdAndTypeName(organizationId, typeName);
        if (exists) {
            throw new DuplicateRecordException(OrganizationExpenseTypeMessage.ORGANIZATION_EXPENSE_TYPE_EXISTS);
        }
    }
}
