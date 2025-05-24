package com.lawfirm.evidence.converter;

import com.lawfirm.model.evidence.dto.EvidencePersonnelDTO;
import com.lawfirm.model.evidence.entity.EvidencePersonnel;
import com.lawfirm.model.evidence.vo.EvidencePersonnelVO;

public class EvidencePersonnelConverter {
    public static EvidencePersonnel toEntity(EvidencePersonnelDTO dto) {
        if (dto == null) return null;
        EvidencePersonnel entity = new EvidencePersonnel();
        entity.setId(dto.getId());
        entity.setEvidenceId(dto.getEvidenceId());
        entity.setPersonnelId(dto.getPersonnelId());
        entity.setRoleType(dto.getRoleType());
        return entity;
    }

    public static void copyToEntity(EvidencePersonnelDTO dto, EvidencePersonnel entity) {
        if (dto == null || entity == null) return;
        entity.setEvidenceId(dto.getEvidenceId());
        entity.setPersonnelId(dto.getPersonnelId());
        entity.setRoleType(dto.getRoleType());
    }

    public static EvidencePersonnelVO toVO(EvidencePersonnel entity) {
        if (entity == null) return null;
        EvidencePersonnelVO vo = new EvidencePersonnelVO();
        vo.setId(entity.getId());
        vo.setEvidenceId(entity.getEvidenceId());
        vo.setPersonnelId(entity.getPersonnelId());
        vo.setRoleType(entity.getRoleType());
        return vo;
    }
} 