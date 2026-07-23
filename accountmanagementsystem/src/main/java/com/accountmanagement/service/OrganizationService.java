package com.accountmanagement.service;

import java.util.List;
import java.util.UUID;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import com.accountmanagement.constants.AppConstants;
import com.accountmanagement.constants.message.OrganizationMessage;
import com.accountmanagement.exceptions.InvalidCredentialsException;
import com.accountmanagement.exceptions.RecordNotFoundException;
import com.accountmanagement.exceptions.UserAlreadyExistsException;
import com.accountmanagement.mapper.OrganizationMapper;
import com.accountmanagement.model.Organization;
import com.accountmanagement.repository.OrganizationRepository;
import com.accountmanagement.request.OrganizationRequest;

import jakarta.transaction.Transactional;

@Service
public class OrganizationService {

    private final OrganizationRepository organizationRepository;

    private final OrganizationMapper organizationMapper;

    private final JdbcTemplate jdbcTemplate;

    OrganizationService(OrganizationRepository organizationRepository, OrganizationMapper organizationMapper,
            JdbcTemplate jdbcTemplate) {
        this.organizationRepository = organizationRepository;
        this.organizationMapper = organizationMapper;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Transactional
    public Organization addOrganization(OrganizationRequest organizationRequest) {
        validateOrganization(organizationRequest);
        String code = generateOrganizationCode();
        Organization organization = organizationMapper.toCreateOrganization(code, organizationRequest);
        return organizationRepository.save(organization);
    }

    @Transactional
    public Organization updateOrganization(UUID id, OrganizationRequest organizationRequest) {
        Organization organization = organizationRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Organization id not found"));
        Organization updatedOrganization = organizationMapper.toUpdateOrganization(organization, organizationRequest);
        return organizationRepository.save(updatedOrganization);
    }

    public void deleteOrganizationById(UUID id) {
        Organization organization = organizationRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Organization id not found"));
        organization.setStatus(AppConstants.INACTIVE);
        organizationRepository.save(organization);
    }

    private String generateOrganizationCode() {
        Long sequence = jdbcTemplate.queryForObject("select nextval('organization_code_seq')", Long.class);
        return String.format("CH_%05d", sequence);
    }

    public List<Organization> viewAll() {
        List<Organization> organization = organizationRepository.findAll();
        if (organization == null || organization.isEmpty()) {
            throw new RecordNotFoundException(OrganizationMessage.ORGANIZATIONS_NOT_FOUND);
        }
        return organization;
    }

    private void validateOrganization(OrganizationRequest organizationRequest) {
        if (organizationRepository.existsByName(organizationRequest.getName())) {
            throw new UserAlreadyExistsException("Organization already exists");
        }
        if (organizationRepository.existsByEmail(organizationRequest.getEmail())) {
            throw new UserAlreadyExistsException("Email already registered");
        }
        if (organizationRepository.existsByContactNumber(organizationRequest.getContactNumber())) {
            throw new UserAlreadyExistsException("Phone number already registered");
        }
        if (organizationRepository.existsByRegistrationNumber(organizationRequest.getRegistrationNumber())) {
            throw new UserAlreadyExistsException("Register number already registered");
        }
        if (organizationRepository.existsByWebsite(organizationRequest.getWebsite())) {
            throw new UserAlreadyExistsException("Website already registered");
        }
    }
}
