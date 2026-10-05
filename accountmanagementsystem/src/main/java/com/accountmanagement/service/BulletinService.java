package com.accountmanagement.service;

import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;
import com.accountmanagement.constants.AppConstants;
import com.accountmanagement.constants.message.BulletinMessage;
import com.accountmanagement.exceptions.RecordNotFoundException;
import com.accountmanagement.mapper.BulletinMapper;
import com.accountmanagement.model.Bulletin;
import com.accountmanagement.repository.BulletinRepository;
import com.accountmanagement.request.BulletinRequest;
import com.accountmanagement.request.BulletinUpdateRequest;

@Service
public class BulletinService {

    private final BulletinRepository bulletinRepository;
    private final BulletinMapper bulletinMapper;

    public BulletinService(BulletinRepository bulletinRepository, BulletinMapper bulletinMapper) {
        this.bulletinRepository = bulletinRepository;
        this.bulletinMapper = bulletinMapper;
    }

    public Bulletin createBulletin(BulletinRequest bulletinRequest) {
        validateBulletin(bulletinRequest);
        Bulletin bulletin = bulletinMapper.toCreateBulletin(bulletinRequest);
        return bulletinRepository.save(bulletin);
    }

    public Bulletin updateBulletin(UUID id, BulletinUpdateRequest bulletinUpdateRequest) {
        Bulletin bulletin = findBulletinById(id);
        Bulletin updatedBulletin = bulletinMapper.toUpdateBulletin(bulletin, bulletinUpdateRequest);
        return bulletinRepository.save(updatedBulletin);
    }

    public void deleteBulletinById(UUID id) {
        Bulletin bulletin = findBulletinById(id);
        bulletin.setStatus(AppConstants.INACTIVE);
        bulletinRepository.save(bulletin);
    }

    public List<Bulletin> viewAllBulletins() {
        return bulletinRepository.findAll();
    }

    public Bulletin findBulletinById(UUID id) {
        return bulletinRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(BulletinMessage.BULLETIN_ID_NOT_FOUND));
    }

    private void validateBulletin(BulletinRequest bulletinRequest) {
        boolean exists = bulletinRepository
                .existsByOrganizationIdAndTitleIgnoreCaseAndBulletinDate(
                        bulletinRequest.getOrganizationId(),
                        bulletinRequest.getTitle(),
                        bulletinRequest.getBulletinDate());
        if (exists) {
            throw new RecordNotFoundException(
                    BulletinMessage.BULLETIN_EXISTS);
        }
    }
}
