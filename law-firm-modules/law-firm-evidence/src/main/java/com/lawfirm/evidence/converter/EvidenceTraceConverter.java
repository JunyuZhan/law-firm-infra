package com.lawfirm.evidence.converter;

import com.lawfirm.model.evidence.dto.EvidenceTraceDTO;
import com.lawfirm.model.evidence.entity.EvidenceTrace;
import com.lawfirm.model.evidence.vo.EvidenceTraceVO;

public class EvidenceTraceConverter {
    public static EvidenceTrace toEntity(EvidenceTraceDTO dto) {
        if (dto == null) return null;
        EvidenceTrace entity = new EvidenceTrace();
        entity.setId(dto.getId());
        entity.setEvidenceId(dto.getEvidenceId());
        entity.setOperationType(dto.getOperationType());
        entity.setOperatorId(dto.getOperatorId());
        entity.setOperatorName(dto.getOperatorName());
        entity.setOperationTime(dto.getOperationTime());
        entity.setNode(dto.getNode());
        entity.setPreservationNo(dto.getPreservationNo());
        return entity;
    }

    public static void copyToEntity(EvidenceTraceDTO dto, EvidenceTrace entity) {
        if (dto == null || entity == null) return;
        entity.setEvidenceId(dto.getEvidenceId());
        entity.setOperationType(dto.getOperationType());
        entity.setOperatorId(dto.getOperatorId());
        entity.setOperatorName(dto.getOperatorName());
        entity.setOperationTime(dto.getOperationTime());
        entity.setNode(dto.getNode());
        entity.setPreservationNo(dto.getPreservationNo());
    }

    public static EvidenceTraceVO toVO(EvidenceTrace entity) {
        if (entity == null) return null;
        EvidenceTraceVO vo = new EvidenceTraceVO();
        vo.setId(entity.getId());
        vo.setEvidenceId(entity.getEvidenceId());
        vo.setOperationType(entity.getOperationType());
        vo.setOperatorId(entity.getOperatorId());
        vo.setOperatorName(entity.getOperatorName());
        vo.setOperationTime(entity.getOperationTime());
        vo.setNode(entity.getNode());
        vo.setPreservationNo(entity.getPreservationNo());
        return vo;
    }
} 