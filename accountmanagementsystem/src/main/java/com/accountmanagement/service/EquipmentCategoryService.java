package com.accountmanagement.service;

import com.accountmanagement.exceptions.DuplicateRecordException;
import java.util.List;
import org.springframework.stereotype.Service;
import com.accountmanagement.constants.message.EquipmentCategoryMessage;
import com.accountmanagement.exceptions.RecordNotFoundException;
import com.accountmanagement.model.EquipmentCategory;
import com.accountmanagement.repository.EquipmentCategoryRepository;
import com.accountmanagement.request.EquipmentCategoryRequest;
import com.accountmanagement.request.EquipmentCategoryUpdateRequest;

@Service
public class EquipmentCategoryService {
    private final EquipmentCategoryRepository equipmentCategoryRepository;

    public EquipmentCategoryService(EquipmentCategoryRepository equipmentCategoryRepository) {
        this.equipmentCategoryRepository = equipmentCategoryRepository;

    }

    public EquipmentCategory createEquipmentCategory(EquipmentCategoryRequest equipmentCategoryRequest) {
        validateEquipmentCategory(equipmentCategoryRequest.getCategoryName());
        EquipmentCategory equipmentCategory = new EquipmentCategory();
        equipmentCategory.setOrganizationId(equipmentCategoryRequest.getOrganizationId());
        equipmentCategory.setCategoryName(equipmentCategoryRequest.getCategoryName());
        equipmentCategory.setDescription(equipmentCategoryRequest.getDescription());
        return equipmentCategoryRepository.save(equipmentCategory);
    }

    public EquipmentCategory updateEquipmentCategory(Integer id,
            EquipmentCategoryUpdateRequest equipmentCategoryUpdateRequest) {
        EquipmentCategory equipmentCategory = findEquipmentCategoryById(id);
        equipmentCategory.setCategoryName(equipmentCategoryUpdateRequest.getCategoryName());
        equipmentCategory.setDescription(equipmentCategoryUpdateRequest.getDescription());
        return equipmentCategoryRepository.save(equipmentCategory);
    }

    public void deleteEquipmentCategoryById(Integer id) {
        findEquipmentCategoryById(id);
        equipmentCategoryRepository.deleteById(id);
    }

    public List<EquipmentCategory> viewAllEquipmentCategory() {
        return equipmentCategoryRepository.findAll();
    }

    public EquipmentCategory findEquipmentCategoryById(Integer id) {
        return equipmentCategoryRepository.findById(id).orElseThrow(
                () -> new RecordNotFoundException(EquipmentCategoryMessage.EQUIPMENT_CATEGORY_ID_NOT_FOUND));
    }

    private void validateEquipmentCategory(String categoryName) {
        boolean exists = equipmentCategoryRepository.existsByCategoryNameIgnoreCase(categoryName);
        if (exists) {
            throw new DuplicateRecordException(EquipmentCategoryMessage.EQUIPMENT_CATEGORY_EXISTS);
        }
    }
}
