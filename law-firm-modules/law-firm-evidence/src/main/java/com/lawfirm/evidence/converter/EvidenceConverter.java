package com.lawfirm.evidence.converter;

import com.lawfirm.model.evidence.dto.EvidenceDTO;
import com.lawfirm.model.evidence.entity.Evidence;

public class EvidenceConverter {
    public static Evidence toEntity(EvidenceDTO dto) {
        if (dto == null) return null;
        Evidence entity = new Evidence();
        entity.setId(dto.getId());
        entity.setCaseId(dto.getCaseId());
        entity.setName(dto.getName());
        entity.setType(dto.getType());
        entity.setSource(dto.getSource());
        entity.setProofMatter(dto.getProofMatter());
        entity.setSubmitterId(dto.getSubmitterId());
        entity.setSubmitTime(dto.getSubmitTime());
        entity.setEvidenceChain(dto.getEvidenceChain());
        entity.setChallengeStatus(dto.getChallengeStatus());
        // entity.setDocumentIds(dto.getDocumentIds()); // 需特殊处理
        return entity;
    }

    public static void copyToEntity(EvidenceDTO dto, Evidence entity) {
        if (dto == null || entity == null) return;
        entity.setCaseId(dto.getCaseId());
        entity.setName(dto.getName());
        entity.setType(dto.getType());
        entity.setSource(dto.getSource());
        entity.setProofMatter(dto.getProofMatter());
        entity.setSubmitterId(dto.getSubmitterId());
        entity.setSubmitTime(dto.getSubmitTime());
        entity.setEvidenceChain(dto.getEvidenceChain());
        entity.setChallengeStatus(dto.getChallengeStatus());
        // entity.setDocumentIds(dto.getDocumentIds()); // 需特殊处理
    }
} 