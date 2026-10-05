package com.accountmanagement.service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.accountmanagement.constants.AppConstants;
import com.accountmanagement.constants.message.EquipmentMessage;
import com.accountmanagement.exceptions.RecordNotFoundException;
import com.accountmanagement.mapper.EquipmentMapper;
import com.accountmanagement.model.Equipment;
import com.accountmanagement.repository.EquipmentRepository;
import com.accountmanagement.request.EquipmentRequest;
import com.accountmanagement.request.EquipmentUpdateRequest;

@Service
public class EquipmentService {

    private final EquipmentRepository equipmentRepository;

    private final EquipmentMapper equipmentMapper;

    public EquipmentService(EquipmentRepository equipmentRepository, EquipmentMapper equipmentMapper) {
        this.equipmentRepository = equipmentRepository;
        this.equipmentMapper = equipmentMapper;
    }

    public Equipment createEquipment(EquipmentRequest equipmentRequest) {
        String serialNo = generateEquipmentSerialNo();
        Equipment equipment = equipmentMapper.toCreateEquipment(serialNo, equipmentRequest);
        return equipmentRepository.save(equipment);
    }

    public Equipment updateEquipment(UUID id, EquipmentUpdateRequest equipmentUpdateRequest) {
        Equipment equipment = findEquipmentById(id);
        Equipment updatedEquipment = equipmentMapper.toUpdateEquipment(equipment, equipmentUpdateRequest);
        return equipmentRepository.save(updatedEquipment);
    }

    public void deleteEquipmentById(UUID id) {
        Equipment equipment = findEquipmentById(id);
        equipment.setStatus(AppConstants.INACTIVE);
        equipmentRepository.save(equipment);
    }

    public List<Equipment> viewAllEquipments() {
        return equipmentRepository.findAll();
    }

    public Equipment findEquipmentById(UUID id) {
        return equipmentRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(EquipmentMessage.EQUIPMENT_ID_NOT_FOUND));
    }

    private String generateEquipmentSerialNo() {
        int year = LocalDate.now().getYear() % 100;
        long count = equipmentRepository.count() + 1;
        return String.format("EQ-%02d-%05d", year, count);
    }
}
