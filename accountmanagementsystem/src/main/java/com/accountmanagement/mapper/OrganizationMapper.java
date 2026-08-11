package com.accountmanagement.mapper;

import java.util.UUID;

import org.springframework.stereotype.Component;
import com.accountmanagement.model.Organization;
import com.accountmanagement.model.OrganizationSetting;
import com.accountmanagement.request.OrganizationRegistrationRequest;
import com.accountmanagement.request.OrganizationUpdationRequest;

@Component
public class OrganizationMapper {

    public Organization toRegisterOrganization(String code, OrganizationRegistrationRequest organizationRequest) {
        Organization organization = new Organization();
        organization.setCode(code);
        organization.setName(organizationRequest.getName());
        organization.setRegistrationNumber(organizationRequest.getRegistrationNumber());
        organization.setWebsite(organizationRequest.getWebsite());
        System.out.println("Save Organization Website" + organizationRequest.getWebsite());
        organization.setAddress(organizationRequest.getAddress());
        organization.setCountryId(organizationRequest.getCountryId());
        organization.setStateId(organizationRequest.getStateId());
        organization.setCityId(organizationRequest.getCityId());
        organization.setPostalcode(organizationRequest.getPostalCode());
        organization.setPrimaryContactName(organizationRequest.getPrimaryContactName());
        organization.setPrimaryContactEmail(organizationRequest.getPrimaryContactEmail());
        organization.setPrimaryContactNumber(organizationRequest.getPrimaryContactNumber());
        return organization;
    }

    public Organization toUpdateOrganization(Organization organization,
            OrganizationUpdationRequest organizationUpdateRequest) {
        System.out.println("UpdateOrganization Website" + organizationUpdateRequest.getWebsite());
        if (organizationUpdateRequest.getName() != null && !organizationUpdateRequest.getName().trim().isEmpty()) {
            organization.setName(organizationUpdateRequest.getName());
        }
        if (organizationUpdateRequest.getAddress() != null
                && !organizationUpdateRequest.getAddress().trim().isEmpty()) {
            organization.setAddress(organizationUpdateRequest.getAddress());
        }
        if (organizationUpdateRequest.getWebsite() != null
                && !organizationUpdateRequest.getWebsite().trim().isEmpty()) {
            organization.setWebsite(organizationUpdateRequest.getWebsite());
        }
        if (organizationUpdateRequest.getCountryId() != null) {
            organization.setCountryId(organizationUpdateRequest.getCountryId());
        }
        if (organizationUpdateRequest.getStateId() != null) {
            organization.setStateId(organizationUpdateRequest.getStateId());
        }
        if (organizationUpdateRequest.getCityId() != null) {
            organization.setCityId(organizationUpdateRequest.getCityId());
        }
        if (organizationUpdateRequest.getPostalCode() != null) {
            organization.setPostalcode(organizationUpdateRequest.getPostalCode());
        }
        if (organizationUpdateRequest.getPrimaryContactName() != null
                && !organizationUpdateRequest.getPrimaryContactName().trim().isEmpty()) {
            organization.setPrimaryContactName(organizationUpdateRequest.getPrimaryContactName());
        }
        if (organizationUpdateRequest.getPrimaryContactEmail() != null
                && !organizationUpdateRequest.getPrimaryContactEmail().trim().isEmpty()) {
            organization.setPrimaryContactEmail(organizationUpdateRequest.getPrimaryContactEmail());
        }
        if (organizationUpdateRequest.getPrimaryContactNumber() != null
                && !organizationUpdateRequest.getPrimaryContactNumber().trim().isEmpty()) {
            organization.setPrimaryContactNumber(organizationUpdateRequest.getPrimaryContactNumber());
        }
        return organization;
    }

    public OrganizationSetting toRegisterOrganizatinSetting(UUID id,
            OrganizationRegistrationRequest organizationRequest) {
        OrganizationSetting organizationSetting = new OrganizationSetting();
        organizationSetting.setOrganizationId(id);
        organizationSetting.setLogoUrl((organizationRequest.getLogoUrl()));
        organizationSetting.setFaviconUrl(organizationRequest.getFaviconUrl());
        organizationSetting.setPrimaryColor(organizationRequest.getPrimaryColor());
        organizationSetting.setTimeZone(organizationRequest.getTimeZone());
        organizationSetting.setCurrency(organizationRequest.getCurrency());
        organizationSetting.setLanguage(organizationRequest.getLanguage());
        System.out.println("PrimaryColor" + organizationRequest.getPrimaryColor());
        return organizationSetting;
    }

    public OrganizationSetting toUpdateOrganizationSetting(OrganizationSetting organizationSetting,
            OrganizationUpdationRequest organizationUpdateRequest) {
        if (organizationUpdateRequest.getLogoUrl() != null
                && !organizationUpdateRequest.getLogoUrl().trim().isEmpty()) {
            organizationSetting.setLogoUrl((organizationUpdateRequest.getLogoUrl()));
        }
        if (organizationUpdateRequest.getFaviconUrl() != null
                && !organizationUpdateRequest.getFaviconUrl().trim().isEmpty()) {
            organizationSetting.setFaviconUrl(organizationUpdateRequest.getFaviconUrl());
        }
        if (organizationUpdateRequest.getPrimaryColor() != null
                && !organizationUpdateRequest.getPrimaryColor().trim().isEmpty()) {
            organizationSetting.setPrimaryColor(organizationUpdateRequest.getPrimaryColor());
        }
        if (organizationUpdateRequest.getTimeZone() != null
                && !organizationUpdateRequest.getTimeZone().trim().isEmpty()) {
            organizationSetting.setTimeZone(organizationUpdateRequest.getTimeZone());
        }
        if (organizationUpdateRequest.getCurrency() != null
                && !organizationUpdateRequest.getCurrency().trim().isEmpty()) {
            organizationSetting.setCurrency(organizationUpdateRequest.getCurrency());
        }
        if (organizationUpdateRequest.getLanguage() != null
                && !organizationUpdateRequest.getLanguage().trim().isEmpty()) {
            organizationSetting.setLanguage(organizationUpdateRequest.getLanguage());
        }
        return organizationSetting;
    }

}
