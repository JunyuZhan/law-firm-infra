package com.lawfirm.evidence.service.impl;

import com.lawfirm.model.evidence.dto.EvidenceTagRelationDTO;
import com.lawfirm.model.evidence.vo.EvidenceTagRelationVO;
import com.lawfirm.model.evidence.entity.EvidenceTagRelation;
import com.lawfirm.model.evidence.mapper.EvidenceTagRelationMapper;
import com.lawfirm.model.evidence.service.EvidenceTagRelationService;
import com.lawfirm.evidence.converter.EvidenceTagRelationConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service("evidenceTagRelationService")
public class EvidenceTagRelationServiceImpl implements EvidenceTagRelationService {
    @Autowired
    private EvidenceTagRelationMapper evidenceTagRelationMapper;

    @Override
    public Long addTagRelation(EvidenceTagRelationDTO dto) {
        EvidenceTagRelation entity = EvidenceTagRelationConverter.toEntity(dto);
        evidenceTagRelationMapper.insert(entity);
        return entity.getId();
    }

    @Override
    public void updateTagRelation(EvidenceTagRelationDTO dto) {
        EvidenceTagRelation entity = evidenceTagRelationMapper.selectById(dto.getId());
        EvidenceTagRelationConverter.copyToEntity(dto, entity);
        evidenceTagRelationMapper.updateById(entity);
    }

    @Override
    public void deleteTagRelation(Long id) {
        evidenceTagRelationMapper.deleteById(id);
    }

    @Override
    public EvidenceTagRelationVO getTagRelation(Long id) {
        EvidenceTagRelation entity = evidenceTagRelationMapper.selectById(id);
        return EvidenceTagRelationConverter.toVO(entity);
    }

    @Override
    public List<EvidenceTagRelationVO> listByEvidence(Long evidenceId) {
        List<EvidenceTagRelation> list = evidenceTagRelationMapper.selectList(
            new com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<EvidenceTagRelation>().eq("evidence_id", evidenceId)
        );
        return list.stream().map(EvidenceTagRelationConverter::toVO).collect(Collectors.toList());
    }
} 