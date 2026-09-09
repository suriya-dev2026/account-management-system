package com.accountmanagement.mapper;

import org.springframework.stereotype.Component;

import com.accountmanagement.model.VbsClass;
import com.accountmanagement.request.VbsClassRequest;

@Component
public class VbsClassMapper {

    public VbsClass toAddVbsClass(VbsClassRequest vbsClassRequest) {
        VbsClass vbsClass = new VbsClass();
        vbsClass.setOrganizationId(vbsClassRequest.getOrganizationId());
        vbsClass.setClassName(vbsClassRequest.getClassName());
        vbsClass.setYearId(vbsClassRequest.getYearId());
        vbsClass.setTeacherId(vbsClassRequest.getTeacherId());
        return vbsClass;
    }

    public VbsClass toUpdateVbsClass(VbsClass vbsClass, VbsClassRequest vbsClassRequest) {
        vbsClass.setOrganizationId(vbsClassRequest.getOrganizationId());
        vbsClass.setClassName(vbsClassRequest.getClassName());
        vbsClass.setYearId(vbsClassRequest.getYearId());
        vbsClass.setTeacherId(vbsClassRequest.getTeacherId());
        return vbsClass;
    }
}
