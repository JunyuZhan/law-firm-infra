package com.lawfirm.evidence.converter;

import com.lawfirm.model.evidence.dto.EvidenceFactRelationDTO;
import com.lawfirm.model.evidence.entity.EvidenceFactRelation;
import com.lawfirm.model.evidence.vo.EvidenceFactRelationVO;

public class EvidenceFactRelationConverter {
    public static EvidenceFactRelation toEntity(EvidenceFactRelationDTO dto) {
        if (dto == null) return null;
        EvidenceFactRelation entity = new EvidenceFactRelation();
        entity.setId(dto.getId());
        entity.setEvidenceId(dto.getEvidenceId());
        entity.setFactId(dto.getFactId());
        entity.setProofMatter(dto.getProofMatter());
        entity.setProofLevel(dto.getProofLevel());
        return entity;
    }

    public static void copyToEntity(EvidenceFactRelationDTO dto, EvidenceFactRelation entity) {
        if (dto == null || entity == null) return;
        entity.setEvidenceId(dto.getEvidenceId());
        entity.setFactId(dto.getFactId());
        entity.setProofMatter(dto.getProofMatter());
        entity.setProofLevel(dto.getProofLevel());
    }

    public static EvidenceFactRelationVO toVO(EvidenceFactRelation entity) {
        if (entity == null) return null;
        EvidenceFactRelationVO vo = new EvidenceFactRelationVO();
        vo.setId(entity.getId());
        vo.setEvidenceId(entity.getEvidenceId());
        vo.setFactId(entity.getFactId());
        vo.setProofMatter(entity.getProofMatter());
        vo.setProofLevel(entity.getProofLevel());
        return vo;
    }
} 