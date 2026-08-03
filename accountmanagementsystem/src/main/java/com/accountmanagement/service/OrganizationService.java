package com.accountmanagement.service;

import java.util.List;
import java.util.UUID;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import com.accountmanagement.constants.AppConstants;
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

import jakarta.transaction.Transactional;

@Service
public class OrganizationService {

    private final OrganizationRepository organizationRepository;

    private final OrganizationSettingRepository organizationSettingRepository;

    private final OrganizationMapper organizationMapper;

    private final MasterCountryRepository masterCountryRepository;

    private final MasterStateRepository masterStateRepository;

    private final MasterCityRepository masterCityRepository;

    private final JdbcTemplate jdbcTemplate;

    OrganizationService(OrganizationRepository organizationRepository, OrganizationMapper organizationMapper,
            MasterCountryRepository masterCountryRepository, MasterStateRepository masterStateRepository,
            MasterCityRepository masterCityRepository,
            JdbcTemplate jdbcTemplate, OrganizationSettingRepository organizationSettingRepository) {
        this.organizationRepository = organizationRepository;
        this.organizationMapper = organizationMapper;
        this.jdbcTemplate = jdbcTemplate;
        this.masterCountryRepository = masterCountryRepository;
        this.masterStateRepository = masterStateRepository;
        this.masterCityRepository = masterCityRepository;
        this.organizationSettingRepository = organizationSettingRepository;
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
        return organization;
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
                .orElseThrow(() -> new RecordNotFoundException("Organization id not found"));
    }

    public OrganizationSetting findByOrganizationId(UUID id) {
        return organizationSettingRepository.findByOrganizationId(id)
                .orElseThrow(() -> new RecordNotFoundException("Organization setting not found."));
    }

    private void validateOrganization(OrganizationRegistrationRequest request) {
        validateRegistrationNumber(request.getRegistrationNumber());
        validateWebsite(request.getWebsite());
        validateCountry(request.getCountryId());
        validateState(request.getStateId());
        validateCity(request.getCityId());
        validatePrimaryContactEmail(request.getPrimaryContactEmail());
        validatePrimaryContactNumber(request.getPrimaryContactNumber());
    }

    private void validateRegistrationNumber(String registrationNumber) {
        if (organizationRepository.existsByRegistrationNumber(registrationNumber)) {
            throw new UserAlreadyExistsException("Registration number already in use.");
        }
    }

    private void validateWebsite(String website) {
        if (StringUtils.hasText(website)
                && organizationRepository.existsByWebsite(website.trim())) {
            throw new UserAlreadyExistsException("Website already in use.");
        }
    }

    private void validatePrimaryContactEmail(String primaryContactEmail) {
        if (organizationRepository.existsByPrimaryContactEmail(primaryContactEmail.trim())) {
            throw new UserAlreadyExistsException("contact email already in use.");
        }
    }

    private void validatePrimaryContactNumber(String primaryContactNumber) {
        if (organizationRepository.existsByPrimaryContactNumber(primaryContactNumber.trim())) {
            throw new UserAlreadyExistsException("contact email already in use.");
        }
    }

    private void validateCountry(Integer countryId) {
        if (!masterCountryRepository.existsById(countryId)) {
            throw new RecordNotFoundException("Country not found.");
        }
    }

    private void validateState(Integer stateId) {
        if (!masterStateRepository.existsById(stateId)) {
            throw new RecordNotFoundException("State not found.");
        }
    }

    private void validateCity(Integer cityId) {
        if (!masterCityRepository.existsById(cityId)) {
            throw new RecordNotFoundException("City not found.");
        }
    }
}
