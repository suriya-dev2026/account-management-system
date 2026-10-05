package com.accountmanagement.service;

import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;
import com.accountmanagement.constants.AppConstants;
import com.accountmanagement.constants.message.OrganizationPaymentMethodMessage;
import com.accountmanagement.exceptions.DuplicateRecordException;
import com.accountmanagement.exceptions.RecordNotFoundException;
import com.accountmanagement.model.OrganizationPaymentMethod;
import com.accountmanagement.repository.OrganizationPaymentMethodRepository;
import com.accountmanagement.request.OrganizationPaymentMethodRequest;

@Service
public class OrganizationPaymentMethodService {

    private final OrganizationPaymentMethodRepository organizationPaymentMethodRepository;

    public OrganizationPaymentMethodService(OrganizationPaymentMethodRepository organizationPaymentMethodRepository) {
        this.organizationPaymentMethodRepository = organizationPaymentMethodRepository;
    }

    public OrganizationPaymentMethod createOrganizatonPaymentMethod(
            OrganizationPaymentMethodRequest organizationPaymentMethodRequest) {
        validatemethodName(organizationPaymentMethodRequest.getOrganizationId(),
                organizationPaymentMethodRequest.getCategory());
        OrganizationPaymentMethod organizationPaymentMethod = new OrganizationPaymentMethod();
        organizationPaymentMethod.setOrganizationId(organizationPaymentMethodRequest.getOrganizationId());
        organizationPaymentMethod.setAccountId(organizationPaymentMethodRequest.getAccountId());
        organizationPaymentMethod.setMethodName(organizationPaymentMethodRequest.getMethodName());
        organizationPaymentMethod.setCategory(organizationPaymentMethodRequest.getCategory());
        return organizationPaymentMethodRepository.save(organizationPaymentMethod);
    }

    public OrganizationPaymentMethod updateOrganizationPaymentMethodById(Integer id,
            OrganizationPaymentMethodRequest organizationPaymentMethodRequest) {
        OrganizationPaymentMethod organizationPaymentMethod = findOrganizationPaymentById(id);
        organizationPaymentMethod.setAccountId(organizationPaymentMethodRequest.getAccountId());
        if (organizationPaymentMethodRequest.getMethodName() != null) {
            organizationPaymentMethod.setMethodName(organizationPaymentMethodRequest.getMethodName());
        }
        if (organizationPaymentMethodRequest.getCategory() != null) {
            organizationPaymentMethod.setCategory(organizationPaymentMethodRequest.getCategory());
        }
        return organizationPaymentMethodRepository.save(organizationPaymentMethod);
    }

    public OrganizationPaymentMethod deleteOrganizationPaymentMethodById(Integer id) {
        OrganizationPaymentMethod organizationPaymentMethod = findOrganizationPaymentById(id);
        organizationPaymentMethod.setStatus(AppConstants.DELETED);
        return organizationPaymentMethodRepository.save(organizationPaymentMethod);
    }

    public List<OrganizationPaymentMethod> viewAllOrganizationPaymentMethods() {
        return organizationPaymentMethodRepository.findAll();
    }

    public OrganizationPaymentMethod findOrganizationPaymentById(Integer id) {
        return organizationPaymentMethodRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(
                        OrganizationPaymentMethodMessage.ORGANIZATION_PAYMENT_METHOD_ID_NOT_FOUND));
    }

    private void validatemethodName(UUID organizationId, String methodName) {
        boolean exists = organizationPaymentMethodRepository.existsByOrganizationIdAndMethodName(organizationId,
                methodName);
        if (exists) {
            throw new DuplicateRecordException(OrganizationPaymentMethodMessage.ORGANIZATION_PAYMENT_METHOD_EXISTS);
        }
    }

}