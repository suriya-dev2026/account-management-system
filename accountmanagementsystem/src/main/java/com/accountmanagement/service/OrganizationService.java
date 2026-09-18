package com.accountmanagement.service;

import java.util.List;
import java.util.UUID;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.accountmanagement.constants.AppConstants;
import com.accountmanagement.constants.message.OrganizationMessage;
import com.accountmanagement.exceptions.RecordNotFoundException;
import com.accountmanagement.exceptions.UserAlreadyExistsException;
import com.accountmanagement.mapper.OrganizationMapper;
import com.accountmanagement.model.Organization;
import com.accountmanagement.model.OrganizationSetting;
import com.accountmanagement.repository.MasterCityRepository;
import com.accountmanagement.repository.MasterCountryRepository;
import com.accountmanagement.repository.MasterStateRepository;
import com.accountmanagement.repository.OrganizationRepository;
import com.accountmanagement.repository.OrganizationSettingRepository;
import com.accountmanagement.request.OrganizationRegistrationRequest;
import com.accountmanagement.request.OrganizationUpdationRequest;

@Service
public class OrganizationService {

    private final OrganizationRepository organizationRepository;

    private final OrganizationSettingRepository organizationSettingRepository;

    private final OrganizationMapper organizationMapper;

    private final MasterCountryRepository masterCountryRepository;

    private final MasterStateRepository masterStateRepository;

    private final MasterCityRepository masterCityRepository;

    private final JdbcTemplate jdbcTemplate;

    private final OrganizationAuditLogService organizationAuditLogService;

    OrganizationService(OrganizationRepository organizationRepository, OrganizationMapper organizationMapper,
            MasterCountryRepository masterCountryRepository, MasterStateRepository masterStateRepository,
            MasterCityRepository masterCityRepository,
            JdbcTemplate jdbcTemplate, OrganizationSettingRepository organizationSettingRepository,
            OrganizationAuditLogService organizationAuditLogService) {
        this.organizationRepository = organizationRepository;
        this.organizationMapper = organizationMapper;
        this.jdbcTemplate = jdbcTemplate;
        this.masterCountryRepository = masterCountryRepository;
        this.masterStateRepository = masterStateRepository;
        this.masterCityRepository = masterCityRepository;
        this.organizationSettingRepository = organizationSettingRepository;
        this.organizationAuditLogService = organizationAuditLogService;
    }

    @Transactional
    public Organization registerOrganization(OrganizationRegistrationRequest organizationRequest) {
        validateOrganization(organizationRequest);
        String code = generateOrganizationCode();
        Organization organization = organizationMapper.toRegisterOrganization(code, organizationRequest);
        Organization registeredOrganization = organizationRepository.save(organization);
        OrganizationSetting registeredOrganizationSetting = organizationMapper
                .toRegisterOrganizatinSetting(registeredOrganization.getId(), organizationRequest);
        organizationSettingRepository.save(registeredOrganizationSetting);
        organizationAuditLogService.log(organization.getCode(), null, "Organization",
                registeredOrganization.getId().toString(), "Create Organization", null, null, "Registered Organization",
                null);
        return registeredOrganization;
    }

    @Transactional
    public Organization updateOrganization(UUID id, OrganizationUpdationRequest organizationUpdateRequest) {
        Organization organization = findById(id);
        Organization updatedOrganization = organizationMapper.toUpdateOrganization(organization,
                organizationUpdateRequest);
        organizationRepository.save(updatedOrganization);
        OrganizationSetting organizationSetting = findByOrganizationId(id);
        OrganizationSetting updatedOrganizationSetting = organizationMapper
                .toUpdateOrganizationSetting(organizationSetting, organizationUpdateRequest);
        organizationSettingRepository.save(updatedOrganizationSetting);
        organizationAuditLogService.log(organization.getCode(), null, "Organization",
                updatedOrganization.getId().toString(), "Update Organization", null, null, null, null);
        return organization;
    }

    public void deleteOrganizationById(UUID id) {
        Organization organization = findById(id);
        organization.setStatus(AppConstants.DELETED);
        organizationRepository.save(organization);
    }

    public List<Organization> viewAll() {
        return organizationRepository.findAll();
    }

    private String generateOrganizationCode() {
        Long sequence = jdbcTemplate.queryForObject("select nextval('organization_code_seq')", Long.class);
        return String.format("ORG-%05d", sequence);
    }

    public Organization findById(UUID id) {
        return organizationRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(OrganizationMessage.ORGANIZATION_ID_NOT_FOUND));
    }

    public OrganizationSetting findByOrganizationId(UUID id) {
        return organizationSettingRepository.findByOrganizationId(id)
                .orElseThrow(() -> new RecordNotFoundException(OrganizationMessage.ORGANIZATION_ID_NOT_FOUND));
    }

    private void validateOrganization(OrganizationRegistrationRequest request) {
        validateRegistrationNumber(request.getRegistrationNumber());
        validateCountry(request.getCountryId());
        validateState(request.getStateId());
        validateCity(request.getCityId());
        validatePrimaryContactEmail(request.getContactEmail());
        validatePrimaryContactNumber(request.getContactNumber());
    }

    private void validateRegistrationNumber(String registrationNumber) {
        if (organizationRepository.existsByRegistrationNumber(registrationNumber)) {
            throw new UserAlreadyExistsException(OrganizationMessage.REGISTRATION_EXISTS);
        }
    }

    private void validatePrimaryContactEmail(String primaryContactEmail) {
        if (organizationRepository.existsByContactEmail(primaryContactEmail.trim())) {
            throw new UserAlreadyExistsException(OrganizationMessage.EMAIL_EXISTS);
        }
    }

    private void validatePrimaryContactNumber(String primaryContactNumber) {
        if (organizationRepository.existsByContactNumber(primaryContactNumber.trim())) {
            throw new UserAlreadyExistsException(OrganizationMessage.CONTACT_NUMBER_EXISTS);
        }
    }

    private void validateCountry(Integer countryId) {
        if (!masterCountryRepository.existsById(countryId)) {
            throw new RecordNotFoundException(OrganizationMessage.COUNTRY_ID_NOT_FOUND);
        }
    }

    private void validateState(Integer stateId) {
        if (!masterStateRepository.existsById(stateId)) {
            throw new RecordNotFoundException(OrganizationMessage.STATE_ID_NOT_FOUND);
        }
    }

    private void validateCity(Integer cityId) {
        if (!masterCityRepository.existsById(cityId)) {
            throw new RecordNotFoundException(OrganizationMessage.CITY_ID_NOT_FOUND);
        }
    }
}
