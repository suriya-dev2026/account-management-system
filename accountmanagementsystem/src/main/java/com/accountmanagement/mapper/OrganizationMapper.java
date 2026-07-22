package com.accountmanagement.mapper;

import org.springframework.stereotype.Component;

import com.accountmanagement.model.Organization;
import com.accountmanagement.request.OrganizationRequest;

@Component
public class OrganizationMapper {

    public Organization toCreateOrganization(String code, OrganizationRequest organizationRequest) {
        Organization organization = new Organization();
        organization.setCode(code);
        organization.setName(organizationRequest.getName());
        organization.setRegistrationNumber(organizationRequest.getRegistrationNumber());
        organization.setEmail(organizationRequest.getEmail());
        organization.setContactNumber(organizationRequest.getContactNumber());
        organization.setWebsite(organizationRequest.getWebsite());
        organization.setAddress(organizationRequest.getAddress());
        organization.setCity(organizationRequest.getCity());
        organization.setState(organizationRequest.getState());
        organization.setCountry(organizationRequest.getCountry());
        organization.setPostalcode(organizationRequest.getPostalCode());
        organization.setPrimaryContactName(organizationRequest.getPrimaryContactName());
        organization.setPrimaryContactEmail(organizationRequest.getPrimaryContactEmail());
        organization.setPrimaryContactPhone(organizationRequest.getPrimaryContactPhone());
        return organization;
    }

    public Organization toUpdateOrganization(Organization organization, OrganizationRequest organizationRequest) {
        organization.setName(organizationRequest.getName());
        organization.setRegistrationNumber(organizationRequest.getRegistrationNumber());
        organization.setEmail(organizationRequest.getEmail());
        organization.setContactNumber(organizationRequest.getContactNumber());
        organization.setWebsite(organizationRequest.getWebsite());
        organization.setAddress(organizationRequest.getAddress());
        organization.setCity(organizationRequest.getCity());
        organization.setState(organizationRequest.getState());
        organization.setCountry(organizationRequest.getCountry());
        organization.setPostalcode(organizationRequest.getPostalCode());
        organization.setPrimaryContactName(organizationRequest.getPrimaryContactName());
        organization.setPrimaryContactEmail(organizationRequest.getPrimaryContactEmail());
        organization.setPrimaryContactPhone(organizationRequest.getPrimaryContactPhone());
        return organization;
    }

}
