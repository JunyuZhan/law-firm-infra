package com.lawfirm.evidence.service.impl;

import com.lawfirm.model.evidence.dto.EvidenceFactRelationDTO;
import com.lawfirm.model.evidence.vo.EvidenceFactRelationVO;
import com.lawfirm.model.evidence.entity.EvidenceFactRelation;
import com.lawfirm.model.evidence.mapper.EvidenceFactRelationMapper;
import com.lawfirm.model.evidence.service.EvidenceFactRelationService;
import com.lawfirm.evidence.converter.EvidenceFactRelationConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service("evidenceFactRelationService")
public class EvidenceFactRelationServiceImpl implements EvidenceFactRelationService {
    @Autowired
    private EvidenceFactRelationMapper evidenceFactRelationMapper;

    @Override
    public Long addFactRelation(EvidenceFactRelationDTO dto) {
        EvidenceFactRelation entity = EvidenceFactRelationConverter.toEntity(dto);
        evidenceFactRelationMapper.insert(entity);
        return entity.getId();
    }

    @Override
    public void updateFactRelation(EvidenceFactRelationDTO dto) {
        EvidenceFactRelation entity = evidenceFactRelationMapper.selectById(dto.getId());
        EvidenceFactRelationConverter.copyToEntity(dto, entity);
        evidenceFactRelationMapper.updateById(entity);
    }

    @Override
    public void deleteFactRelation(Long id) {
        evidenceFactRelationMapper.deleteById(id);
    }

    @Override
    public EvidenceFactRelationVO getFactRelation(Long id) {
        EvidenceFactRelation entity = evidenceFactRelationMapper.selectById(id);
        return EvidenceFactRelationConverter.toVO(entity);
    }

    @Override
    public List<EvidenceFactRelationVO> listByEvidence(Long evidenceId) {
        List<EvidenceFactRelation> list = evidenceFactRelationMapper.selectList(
            new com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<EvidenceFactRelation>().eq("evidence_id", evidenceId)
        );
        return list.stream().map(EvidenceFactRelationConverter::toVO).collect(Collectors.toList());
    }
} 