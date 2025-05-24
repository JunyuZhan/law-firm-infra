package com.lawfirm.evidence.converter;

import com.lawfirm.model.evidence.dto.EvidenceAttachmentDTO;
import com.lawfirm.model.evidence.entity.EvidenceAttachment;
import com.lawfirm.model.evidence.vo.EvidenceAttachmentVO;

public class EvidenceAttachmentConverter {
    public static EvidenceAttachment toEntity(EvidenceAttachmentDTO dto) {
        if (dto == null) return null;
        EvidenceAttachment entity = new EvidenceAttachment();
        entity.setId(dto.getId());
        entity.setEvidenceId(dto.getEvidenceId());
        entity.setAttachmentType(dto.getAttachmentType());
        entity.setFileId(dto.getFileId());
        entity.setDescription(dto.getDescription());
        return entity;
    }

    public static void copyToEntity(EvidenceAttachmentDTO dto, EvidenceAttachment entity) {
        if (dto == null || entity == null) return;
        entity.setEvidenceId(dto.getEvidenceId());
        entity.setAttachmentType(dto.getAttachmentType());
        entity.setFileId(dto.getFileId());
        entity.setDescription(dto.getDescription());
    }

    public static EvidenceAttachmentVO toVO(EvidenceAttachment entity) {
        if (entity == null) return null;
        EvidenceAttachmentVO vo = new EvidenceAttachmentVO();
        vo.setId(entity.getId());
        vo.setEvidenceId(entity.getEvidenceId());
        vo.setAttachmentType(entity.getAttachmentType());
        vo.setFileId(entity.getFileId());
        vo.setDescription(entity.getDescription());
        return vo;
    }
} 