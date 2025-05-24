package com.lawfirm.evidence.service.impl;

import com.lawfirm.model.evidence.dto.EvidenceAttachmentDTO;
import com.lawfirm.model.evidence.vo.EvidenceAttachmentVO;
import com.lawfirm.model.evidence.entity.EvidenceAttachment;
import com.lawfirm.model.evidence.mapper.EvidenceAttachmentMapper;
import com.lawfirm.model.evidence.service.EvidenceAttachmentService;
import com.lawfirm.evidence.converter.EvidenceAttachmentConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service("evidenceAttachmentService")
public class EvidenceAttachmentServiceImpl implements EvidenceAttachmentService {
    @Autowired
    private EvidenceAttachmentMapper evidenceAttachmentMapper;

    @Override
    public Long addAttachment(EvidenceAttachmentDTO dto) {
        EvidenceAttachment entity = EvidenceAttachmentConverter.toEntity(dto);
        evidenceAttachmentMapper.insert(entity);
        return entity.getId();
    }

    @Override
    public void updateAttachment(EvidenceAttachmentDTO dto) {
        EvidenceAttachment entity = evidenceAttachmentMapper.selectById(dto.getId());
        EvidenceAttachmentConverter.copyToEntity(dto, entity);
        evidenceAttachmentMapper.updateById(entity);
    }

    @Override
    public void deleteAttachment(Long id) {
        evidenceAttachmentMapper.deleteById(id);
    }

    @Override
    public EvidenceAttachmentVO getAttachment(Long id) {
        EvidenceAttachment entity = evidenceAttachmentMapper.selectById(id);
        return EvidenceAttachmentConverter.toVO(entity);
    }

    @Override
    public List<EvidenceAttachmentVO> listByEvidence(Long evidenceId) {
        List<EvidenceAttachment> list = evidenceAttachmentMapper.selectList(
            new com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<EvidenceAttachment>().eq("evidence_id", evidenceId)
        );
        return list.stream().map(EvidenceAttachmentConverter::toVO).collect(Collectors.toList());
    }
} 