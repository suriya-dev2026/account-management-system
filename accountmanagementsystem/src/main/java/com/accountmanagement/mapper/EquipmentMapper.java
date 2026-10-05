package com.accountmanagement.mapper;

import org.springframework.stereotype.Component;
import com.accountmanagement.model.Equipment;
import com.accountmanagement.request.EquipmentRequest;
import com.accountmanagement.request.EquipmentUpdateRequest;

@Component
public class EquipmentMapper {

    public Equipment toCreateEquipment(String serialNo, EquipmentRequest equipmentRequest) {
        Equipment equipment = new Equipment();
        equipment.setOrganizationId(equipmentRequest.getOrganizationId());
        equipment.setEquipmentName(equipmentRequest.getEquipmentName());
        equipment.setCategoryId(equipmentRequest.getCategoryId());
        equipment.setSerialNo(serialNo);
        equipment.setPurchaseDate(equipmentRequest.getPurchaseDate());
        equipment.setPurchaseCost(equipmentRequest.getPurchaseCost());
        equipment.setWarrantyExpiryDate(equipmentRequest.getWarrantyExpiryDate());
        equipment.setVendorDetails(equipmentRequest.getVendorDetails());
        return equipment;
    }

    public Equipment toUpdateEquipment(Equipment equipment, EquipmentUpdateRequest equipmentRequest) {
        equipment.setEquipmentName(equipmentRequest.getEquipmentName());
        equipment.setCategoryId(equipmentRequest.getCategoryId());
        equipment.setPurchaseDate(equipmentRequest.getPurchaseDate());
        equipment.setPurchaseCost(equipmentRequest.getPurchaseCost());
        equipment.setWarrantyExpiryDate(equipmentRequest.getWarrantyExpiryDate());
        equipment.setVendorDetails(equipmentRequest.getVendorDetails());
        return equipment;
    }
}
