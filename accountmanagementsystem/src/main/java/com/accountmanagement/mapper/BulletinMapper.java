package com.accountmanagement.mapper;

import org.springframework.stereotype.Component;

import com.accountmanagement.model.Bulletin;
import com.accountmanagement.request.BulletinRequest;
import com.accountmanagement.request.BulletinUpdateRequest;

@Component
public class BulletinMapper {

    public Bulletin toCreateBulletin(BulletinRequest bulletinRequest) {
        Bulletin bulletin = new Bulletin();
        bulletin.setOrganizationId(bulletinRequest.getOrganizationId());
        bulletin.setTitle(bulletinRequest.getTitle());
        bulletin.setContent(bulletinRequest.getContent());
        bulletin.setBulletinDate(bulletinRequest.getBulletinDate());
        bulletin.setPublishDate(bulletinRequest.getPublishDate());
        bulletin.setExpiryDate(bulletinRequest.getExpiryDate());
        return bulletin;
    }

    public Bulletin toUpdateBulletin(Bulletin bulletin, BulletinUpdateRequest bulletinUpdateRequest) {
        bulletin.setTitle(bulletinUpdateRequest.getTitle());
        bulletin.setContent(bulletinUpdateRequest.getContent());
        bulletin.setBulletinDate(bulletinUpdateRequest.getBulletinDate());
        bulletin.setPublishDate(bulletinUpdateRequest.getPublishDate());
        bulletin.setExpiryDate(bulletinUpdateRequest.getExpiryDate());
        return bulletin;
    }
}
