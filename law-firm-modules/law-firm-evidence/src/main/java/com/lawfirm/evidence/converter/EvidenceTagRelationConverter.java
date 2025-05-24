package com.lawfirm.evidence.converter;

import com.lawfirm.model.evidence.dto.EvidenceTagRelationDTO;
import com.lawfirm.model.evidence.entity.EvidenceTagRelation;
import com.lawfirm.model.evidence.vo.EvidenceTagRelationVO;

public class EvidenceTagRelationConverter {
    public static EvidenceTagRelation toEntity(EvidenceTagRelationDTO dto) {
        if (dto == null) return null;
        EvidenceTagRelation entity = new EvidenceTagRelation();
        entity.setId(dto.getId());
        entity.setEvidenceId(dto.getEvidenceId());
        entity.setTagId(dto.getTagId());
        return entity;
    }

    public static void copyToEntity(EvidenceTagRelationDTO dto, EvidenceTagRelation entity) {
        if (dto == null || entity == null) return;
        entity.setEvidenceId(dto.getEvidenceId());
        entity.setTagId(dto.getTagId());
    }

    public static EvidenceTagRelationVO toVO(EvidenceTagRelation entity) {
        if (entity == null) return null;
        EvidenceTagRelationVO vo = new EvidenceTagRelationVO();
        vo.setId(entity.getId());
        vo.setEvidenceId(entity.getEvidenceId());
        vo.setTagId(entity.getTagId());
        return vo;
    }
} 