package com.lawfirm.evidence.converter;

import com.lawfirm.model.evidence.dto.EvidenceChallengeDTO;
import com.lawfirm.model.evidence.entity.EvidenceChallenge;
import com.lawfirm.model.evidence.vo.EvidenceChallengeVO;

public class EvidenceChallengeConverter {
    public static EvidenceChallenge toEntity(EvidenceChallengeDTO dto) {
        if (dto == null) return null;
        EvidenceChallenge entity = new EvidenceChallenge();
        entity.setId(dto.getId());
        entity.setEvidenceId(dto.getEvidenceId());
        entity.setChallengerId(dto.getChallengerId());
        entity.setChallengerName(dto.getChallengerName());
        entity.setChallengeTime(dto.getChallengeTime());
        entity.setOpinion(dto.getOpinion());
        entity.setConclusion(dto.getConclusion());
        entity.setAttachmentId(dto.getAttachmentId());
        return entity;
    }

    public static void copyToEntity(EvidenceChallengeDTO dto, EvidenceChallenge entity) {
        if (dto == null || entity == null) return;
        entity.setEvidenceId(dto.getEvidenceId());
        entity.setChallengerId(dto.getChallengerId());
        entity.setChallengerName(dto.getChallengerName());
        entity.setChallengeTime(dto.getChallengeTime());
        entity.setOpinion(dto.getOpinion());
        entity.setConclusion(dto.getConclusion());
        entity.setAttachmentId(dto.getAttachmentId());
    }

    public static EvidenceChallengeVO toVO(EvidenceChallenge entity) {
        if (entity == null) return null;
        EvidenceChallengeVO vo = new EvidenceChallengeVO();
        vo.setId(entity.getId());
        vo.setEvidenceId(entity.getEvidenceId());
        vo.setChallengerId(entity.getChallengerId());
        vo.setChallengerName(entity.getChallengerName());
        vo.setChallengeTime(entity.getChallengeTime());
        vo.setOpinion(entity.getOpinion());
        vo.setConclusion(entity.getConclusion());
        vo.setAttachmentId(entity.getAttachmentId());
        return vo;
    }
} 