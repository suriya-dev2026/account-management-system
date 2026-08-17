package com.accountmanagement.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.accountmanagement.constants.AppConstants;
import com.accountmanagement.exceptions.DuplicateRecordException;
import com.accountmanagement.exceptions.RecordNotFoundException;
import com.accountmanagement.mapper.AccessControlRouteMapper;
import com.accountmanagement.model.AccessControlRoute;
import com.accountmanagement.repository.AccessControlRouteRepository;
import com.accountmanagement.request.AccessControlRouteRequest;

@Service
public class AccessControlRouteService {

    private final AccessControlRouteRepository accessControlRouteRepository;

    private final AccessControlRouteMapper accessControlRouteMapper;

    public AccessControlRouteService(AccessControlRouteRepository accessControlRouteRepository,
            AccessControlRouteMapper accessControlRouteMapper) {
        this.accessControlRouteRepository = accessControlRouteRepository;
        this.accessControlRouteMapper = accessControlRouteMapper;
    }

    public AccessControlRoute createRoute(AccessControlRouteRequest accessControlRouteRequest) {
        validateAccessControlRoute(accessControlRouteRequest);
        AccessControlRoute accessControlRoute = accessControlRouteMapper.toAddRoute(accessControlRouteRequest);
        return accessControlRouteRepository.save(accessControlRoute);
    }

    public AccessControlRoute updateRoute(Integer id, AccessControlRouteRequest accessControlRouteRequest) {
        AccessControlRoute accessControlRoute = findByAccessControlRouteId(id);
        AccessControlRoute updatedAccessControlRoute = accessControlRouteMapper.toUpdateroute(accessControlRoute,
                accessControlRouteRequest);
        return accessControlRouteRepository.save(updatedAccessControlRoute);
    }

    public AccessControlRoute findByAccessControlRouteId(Integer id) {
        return accessControlRouteRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Access Control Route id not found"));
    }

    public List<AccessControlRoute> viewAllAccessControlRoutes() {
        return accessControlRouteRepository.findAll();
    }

    public void deleteById(Integer id) {
        AccessControlRoute accessControlRoute = findByAccessControlRouteId(id);
        accessControlRoute.setStatus(AppConstants.INACTIVE);
        accessControlRouteRepository.save(accessControlRoute);
    }

    private void validateBackendRoute(String backendRoute) {
        if (accessControlRouteRepository
                .existsByBackendRouteIgnoreCase(backendRoute.trim())) {
            throw new DuplicateRecordException(
                    "BAckend Route Already Exists");
        }
    }

    private void validateFrontendRoute(String frontendRoute) {
        if (accessControlRouteRepository
                .existsByFrontendRouteIgnoreCase(frontendRoute.trim())) {
            throw new DuplicateRecordException(
                    "Frontend Route Already Exists");
        }
    }

    public void validateAccessControlRoute(AccessControlRouteRequest accessControlRouteRequest) {
        validateBackendRoute(accessControlRouteRequest.getBackendRoute());
        validateFrontendRoute(accessControlRouteRequest.getFrontendRoute());
    }

}
