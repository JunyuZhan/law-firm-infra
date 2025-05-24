package com.lawfirm.evidence.service.impl;

import com.lawfirm.model.evidence.dto.EvidencePersonnelDTO;
import com.lawfirm.model.evidence.vo.EvidencePersonnelVO;
import com.lawfirm.model.evidence.entity.EvidencePersonnel;
import com.lawfirm.model.evidence.mapper.EvidencePersonnelMapper;
import com.lawfirm.model.evidence.service.EvidencePersonnelService;
import com.lawfirm.evidence.converter.EvidencePersonnelConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service("evidencePersonnelService")
public class EvidencePersonnelServiceImpl implements EvidencePersonnelService {
    @Autowired
    private EvidencePersonnelMapper evidencePersonnelMapper;

    @Override
    public Long addPersonnel(EvidencePersonnelDTO dto) {
        EvidencePersonnel entity = EvidencePersonnelConverter.toEntity(dto);
        evidencePersonnelMapper.insert(entity);
        return entity.getId();
    }

    @Override
    public void updatePersonnel(EvidencePersonnelDTO dto) {
        EvidencePersonnel entity = evidencePersonnelMapper.selectById(dto.getId());
        EvidencePersonnelConverter.copyToEntity(dto, entity);
        evidencePersonnelMapper.updateById(entity);
    }

    @Override
    public void deletePersonnel(Long id) {
        evidencePersonnelMapper.deleteById(id);
    }

    @Override
    public EvidencePersonnelVO getPersonnel(Long id) {
        EvidencePersonnel entity = evidencePersonnelMapper.selectById(id);
        return EvidencePersonnelConverter.toVO(entity);
    }

    @Override
    public List<EvidencePersonnelVO> listByEvidence(Long evidenceId) {
        List<EvidencePersonnel> list = evidencePersonnelMapper.selectList(
            new com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<EvidencePersonnel>().eq("evidence_id", evidenceId)
        );
        return list.stream().map(EvidencePersonnelConverter::toVO).collect(Collectors.toList());
    }
} 