package com.accountmanagement.controller.v1;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.accountmanagement.constants.AppConstants;
import com.accountmanagement.constants.message.AccessControlRouteMessage;
import com.accountmanagement.model.AccessControlRoute;
import com.accountmanagement.request.AccessControlRouteRequest;
import com.accountmanagement.response.ApiResponse;
import com.accountmanagement.service.AccessControlRouteService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/v1/access/control/route")
@Tag(name = "AccessControlRouteController")
public class AccessControlRouteController {

    private final AccessControlRouteService accessControlRouteService;

    AccessControlRouteController(AccessControlRouteService accessControlRouteService) {
        this.accessControlRouteService = accessControlRouteService;
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> createRoute(
            @Valid @RequestBody AccessControlRouteRequest accessControlRouteRequest) {
        accessControlRouteRequest.sanitizeInput();
        accessControlRouteService.createRoute(accessControlRouteRequest);
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS, AccessControlRouteMessage.ADD_ROUTE, 201);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse> updateRoute(@PathVariable Integer id,
            @Valid @RequestBody AccessControlRouteRequest accessControlRouteRequest) {
        accessControlRouteRequest.sanitizeInput();
        accessControlRouteService.updateRoute(id, accessControlRouteRequest);
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS, AccessControlRouteMessage.UPDATE_ROUTE, 200);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<ApiResponse> deleteRouteById(@PathVariable Integer id) {
        accessControlRouteService.deleteById(id);
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS, AccessControlRouteMessage.DELETE_ROUTE, 200);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<ApiResponse> viewAllRoutes() {
        List<AccessControlRoute> accessControlRoute = accessControlRouteService.viewAllAccessControlRoutes();
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS, AccessControlRouteMessage.VIEW_ALL_ROUTES, 200);
        response.setData(accessControlRoute);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
