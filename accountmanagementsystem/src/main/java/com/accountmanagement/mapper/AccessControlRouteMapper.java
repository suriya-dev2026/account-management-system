package com.accountmanagement.mapper;

import org.springframework.stereotype.Component;

import com.accountmanagement.model.AccessControlRoute;
import com.accountmanagement.request.AccessControlRouteRequest;

@Component
public class AccessControlRouteMapper {

    public AccessControlRoute toAddRoute(AccessControlRouteRequest accessControlRouteRequest) {
        AccessControlRoute accessControlRoute = new AccessControlRoute();
        accessControlRoute.setControllerName(accessControlRouteRequest.getControllerName());
        accessControlRoute.setBackendRoute(accessControlRouteRequest.getBackendRoute());
        accessControlRoute.setFrontendRoute(accessControlRouteRequest.getFrontendRoute());
        accessControlRoute.setDescription(accessControlRouteRequest.getDescription());
        accessControlRoute.setIsDefault(accessControlRouteRequest.getIsDefault());
        return accessControlRoute;
    }

     public AccessControlRoute toUpdateroute(AccessControlRoute accessControlRoute, AccessControlRouteRequest accessControlRouteRequest) {
        accessControlRoute.setControllerName(accessControlRouteRequest.getControllerName());
        accessControlRoute.setBackendRoute(accessControlRouteRequest.getBackendRoute());
        accessControlRoute.setFrontendRoute(accessControlRouteRequest.getFrontendRoute());
        accessControlRoute.setDescription(accessControlRouteRequest.getDescription());
        accessControlRoute.setIsDefault(accessControlRouteRequest.getIsDefault());
        return accessControlRoute;
    }
}
