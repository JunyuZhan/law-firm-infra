package com.lawfirm.evidence.converter;

import com.lawfirm.model.evidence.dto.EvidenceReviewDTO;
import com.lawfirm.model.evidence.entity.EvidenceReview;
import com.lawfirm.model.evidence.vo.EvidenceReviewVO;

public class EvidenceReviewConverter {
    public static EvidenceReview toEntity(EvidenceReviewDTO dto) {
        if (dto == null) return null;
        EvidenceReview entity = new EvidenceReview();
        entity.setId(dto.getId());
        entity.setEvidenceId(dto.getEvidenceId());
        entity.setReviewerId(dto.getReviewerId());
        entity.setReviewerName(dto.getReviewerName());
        entity.setReviewTime(dto.getReviewTime());
        entity.setReviewOpinion(dto.getReviewOpinion());
        entity.setReviewStatus(dto.getReviewStatus());
        return entity;
    }

    public static void copyToEntity(EvidenceReviewDTO dto, EvidenceReview entity) {
        if (dto == null || entity == null) return;
        entity.setEvidenceId(dto.getEvidenceId());
        entity.setReviewerId(dto.getReviewerId());
        entity.setReviewerName(dto.getReviewerName());
        entity.setReviewTime(dto.getReviewTime());
        entity.setReviewOpinion(dto.getReviewOpinion());
        entity.setReviewStatus(dto.getReviewStatus());
    }

    public static EvidenceReviewVO toVO(EvidenceReview entity) {
        if (entity == null) return null;
        EvidenceReviewVO vo = new EvidenceReviewVO();
        vo.setId(entity.getId());
        vo.setEvidenceId(entity.getEvidenceId());
        vo.setReviewerId(entity.getReviewerId());
        vo.setReviewerName(entity.getReviewerName());
        vo.setReviewTime(entity.getReviewTime());
        vo.setReviewOpinion(entity.getReviewOpinion());
        vo.setReviewStatus(entity.getReviewStatus());
        return vo;
    }
} 