package com.accountmanagement.controller.v1;

import java.util.List;
import java.util.UUID;
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
import com.accountmanagement.constants.message.BulletinMessage;
import com.accountmanagement.model.Bulletin;
import com.accountmanagement.request.BulletinRequest;
import com.accountmanagement.request.BulletinUpdateRequest;
import com.accountmanagement.response.ApiResponse;
import com.accountmanagement.service.BulletinService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/v1/bulletin")
@Tag(name = "BulletinController")
public class BulletinController {

    private final BulletinService bulletinService;

    public BulletinController(BulletinService bulletinService) {
        this.bulletinService = bulletinService;
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse> createBulletin(@Valid @RequestBody BulletinRequest bulletinRequest) {
        bulletinRequest.sanitizeInput();
        bulletinService.createBulletin(bulletinRequest);
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS, BulletinMessage.CREATE_BULLETIN, 201);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse> updateBulletin(@PathVariable UUID id,
            @Valid @RequestBody BulletinUpdateRequest bulletinUpdateRequest) {
        bulletinUpdateRequest.sanitizeInput();
        bulletinService.updateBulletin(id, bulletinUpdateRequest);
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS, BulletinMessage.UPDATE_BULLETIN, 200);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteBulletin(@PathVariable UUID id) {
        bulletinService.deleteBulletinById(id);
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS, BulletinMessage.DELETE_BULLETIN, 200);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<ApiResponse> viewAllBulletins() {
        List<Bulletin> bulletin = bulletinService.viewAllBulletins();
        ApiResponse response = new ApiResponse(AppConstants.SUCCESS, BulletinMessage.VIEW_ALL_BULLETINS, 200);
        response.setData(bulletin);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
