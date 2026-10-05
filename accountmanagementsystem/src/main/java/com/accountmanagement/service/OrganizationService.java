package com.accountmanagement.service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
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
import com.google.gson.Gson;

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
        OrganizationSetting setting = organizationSettingRepository.save(registeredOrganizationSetting);
        String updatedJson = addOrganizationJson(registeredOrganization, setting);
        organizationAuditLogService.log(organization.getCode(), null, "Organization",
                registeredOrganization.getId().toString(), "Create Organization", null, updatedJson,
                OrganizationMessage.ADD_ORGANIZATION,
                null);
        return registeredOrganization;
    }

    @Transactional
    public Organization updateOrganization(UUID id, OrganizationUpdationRequest organizationUpdateRequest) {
        Organization organization = findById(id);
        OrganizationSetting organizationSetting = findByOrganizationId(id);
        String existingJson = addOrganizationJson(organization, organizationSetting);
        Organization updatedOrganization = organizationMapper.toUpdateOrganization(organization,
                organizationUpdateRequest);
        Organization savedUpdatedOrganization = organizationRepository.save(updatedOrganization);
        OrganizationSetting updatedOrganizationSetting = organizationMapper
                .toUpdateOrganizationSetting(organizationSetting, organizationUpdateRequest);
        OrganizationSetting savedOrganizationSetting = organizationSettingRepository.save(updatedOrganizationSetting);
        String updatedJson = addOrganizationJson(savedUpdatedOrganization, savedOrganizationSetting);
        organizationAuditLogService.log(organization.getCode(), null, "Organization",
                updatedOrganization.getId().toString(), "Update Organization", existingJson, updatedJson,
                OrganizationMessage.UPDATE_ORGANIZATION,
                null);
        return savedUpdatedOrganization;
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

    // public String addOrganizationJson(Organization savedOrganization,
    // OrganizationSetting savedOrganizationSetting) {
    // return "{"
    // + "\"Id\":\"" + savedOrganization.getId() + "\","
    // + "\"OrganizationCode\":\"" + savedOrganization.getCode() + "\","
    // + "\"Name\":\"" + savedOrganization.getName() + "\","
    // + "\"RegistrationNumber\":\"" + savedOrganization.getRegistrationNumber() +
    // "\","
    // + "\"Website\":\"" + savedOrganization.getWebsite() + "\","
    // + "\"Address\":\"" + savedOrganization.getAddress() + "\","
    // + "\"CountryId\":\"" + savedOrganization.getCountryId() + "\","
    // + "\"StateId\":\"" + savedOrganization.getStateId() + "\","
    // + "\"CityId\":\"" + savedOrganization.getCityId() + "\","
    // + "\"PostalCode\":\"" + savedOrganization.getPostalcode() + "\","
    // + "\"ContactName\":\"" + savedOrganization.getContactName() + "\","
    // + "\"ContactEmail\":\"" + savedOrganization.getContactEmail() + "\","
    // + "\"ContactNumber\":\"" + savedOrganization.getContactNumber() + "\","
    // + "\"Status\":\"" + savedOrganization.getStatus() + "\","
    // + "\"LogoUrl\":\"" + savedOrganizationSetting.getLogoUrl() + "\","
    // + "\"FaviconUrl\":\"" + savedOrganizationSetting.getFaviconUrl() + "\","
    // + "\"PrimaryColor\":\"" + savedOrganizationSetting.getPrimaryColor() + "\","
    // + "\"Timezone\":\"" + savedOrganizationSetting.getTimeZone() + "\","
    // + "\"Currency\":\"" + savedOrganizationSetting.getCurrency() + "\","
    // + "\"Language\":\"" + savedOrganizationSetting.getLanguage() + "\""
    // + "}";
    // }
    public String addOrganizationJson(
            Organization savedOrganization,
            OrganizationSetting savedOrganizationSetting) {
        Map<String, Object> data = new LinkedHashMap<>();
        data.put("Id", savedOrganization.getId());
        data.put("OrganizationCode", savedOrganization.getCode());
        data.put("Name", savedOrganization.getName());
        data.put("RegistrationNumber", savedOrganization.getRegistrationNumber());
        data.put("Website", savedOrganization.getWebsite());
        data.put("Address", savedOrganization.getAddress());
        data.put("CountryId", savedOrganization.getCountryId());
        data.put("StateId", savedOrganization.getStateId());
        data.put("CityId", savedOrganization.getCityId());
        data.put("PostalCode", savedOrganization.getPostalcode());
        data.put("ContactName", savedOrganization.getContactName());
        data.put("ContactEmail", savedOrganization.getContactEmail());
        data.put("ContactNumber", savedOrganization.getContactNumber());
        data.put("Status", savedOrganization.getStatus());
        data.put("LogoUrl", savedOrganizationSetting.getLogoUrl());
        data.put("FaviconUrl", savedOrganizationSetting.getFaviconUrl());
        data.put("PrimaryColor", savedOrganizationSetting.getPrimaryColor());
        data.put("Timezone", savedOrganizationSetting.getTimeZone());
        data.put("Currency", savedOrganizationSetting.getCurrency());
        data.put("Language", savedOrganizationSetting.getLanguage());
        return new Gson().toJson(data);
    }

}
