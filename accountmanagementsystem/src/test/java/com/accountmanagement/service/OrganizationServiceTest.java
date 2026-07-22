package com.accountmanagement.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;

import com.accountmanagement.mapper.OrganizationMapper;
import com.accountmanagement.model.Organization;
import com.accountmanagement.repository.OrganizationRepository;
import com.accountmanagement.request.OrganizationRequest;

@ExtendWith(MockitoExtension.class)
public class OrganizationServiceTest {

    @Mock
    private OrganizationRepository organizationRepository;

    @Mock
    private OrganizationMapper organizationMapper;

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private OrganizationService organizationService;

    @Test
    void shouldAddOrganization() {
        UUID id = UUID.randomUUID();
        OrganizationRequest organizationRequest = new OrganizationRequest();
        organizationRequest.setName("CSI Church");
        organizationRequest.setRegistrationNumber("REG123");
        organizationRequest.setEmail("csi@gmail.com");
        organizationRequest.setContactNumber("9876543210");
        organizationRequest.setWebsite("www.csichurch.com");
        organizationRequest.setAddress("Nagercoil");
        organizationRequest.setCity("Nagercoil");
        organizationRequest.setState("Tamil Nadu");
        organizationRequest.setCountry("India");
        organizationRequest.setPostalCode("629001");
        organizationRequest.setPrimaryContactName("Ajay");
        organizationRequest.setPrimaryContactEmail("ajay@gmail.com");
        organizationRequest.setPrimaryContactPhone("9876543210");
        Organization organization = new Organization();
        organization.setId(id);
        organization.setCode("CH_00001");
        organization.setName("CSI Church");
        when(jdbcTemplate.queryForObject("select nextval('organization_code_seq')", Long.class)).thenReturn(1L);
        when(organizationMapper.toCreateOrganization("CH_00001", organizationRequest))
                .thenReturn(organization);
        when(organizationRepository.save(organization))
                .thenReturn(organization);
        Organization result = organizationService.addOrganization(organizationRequest);
        assertNotNull(result);
        assertEquals("CH_00001", result.getCode());
        assertEquals("CSI Church", result.getName());
        verify(jdbcTemplate)
                .queryForObject("select nextval('organization_code_seq')", Long.class);
        verify(organizationMapper)
                .toCreateOrganization("CH_00001", organizationRequest);
        verify(organizationRepository)
                .save(organization);
    }
}
