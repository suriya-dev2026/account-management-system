package com.accountmanagement.mapper;

import java.util.UUID;

import org.springframework.stereotype.Component;

import com.accountmanagement.model.MasterCity;
import com.accountmanagement.model.MasterCountry;
import com.accountmanagement.model.MasterState;
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

    public Organization toUpdateOrganization(Organization organization, OrganizationUpdationRequest organizationUpdateRequest) {
        organization.setName(organizationUpdateRequest.getName());
        organization.setAddress(organizationUpdateRequest.getAddress());
        organization.setWebsite(organizationUpdateRequest.getWebsite());
        organization.setCountryId(organizationUpdateRequest.getCountryId());
        organization.setStateId(organizationUpdateRequest.getStateId());
        organization.setCityId(organizationUpdateRequest.getCityId());
        organization.setPostalcode(organizationUpdateRequest.getPostalCode());
        organization.setPrimaryContactName(organizationUpdateRequest.getPrimaryContactName());
        organization.setPrimaryContactEmail(organizationUpdateRequest.getPrimaryContactEmail());
        organization.setPrimaryContactNumber(organizationUpdateRequest.getPrimaryContactNumber());
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
        return organizationSetting;
    }

    public OrganizationSetting toUpdateOrganizationSetting(OrganizationSetting organizationSetting,
            OrganizationUpdationRequest organizationUpdateRequest) {
        organizationSetting.setLogoUrl((organizationUpdateRequest.getLogoUrl()));
        organizationSetting.setFaviconUrl(organizationUpdateRequest.getFaviconUrl());
        organizationSetting.setPrimaryColor(organizationUpdateRequest.getPrimaryColor());
        organizationSetting.setTimeZone(organizationUpdateRequest.getTimeZone());
        organizationSetting.setCurrency(organizationUpdateRequest.getCurrency());
        organizationSetting.setLanguage(organizationUpdateRequest.getLanguage());
        return organizationSetting;
    }

}
