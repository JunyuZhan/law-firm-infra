package com.lawfirm.evidence.service.impl;

import com.lawfirm.model.evidence.dto.EvidenceTraceDTO;
import com.lawfirm.model.evidence.vo.EvidenceTraceVO;
import com.lawfirm.model.evidence.entity.EvidenceTrace;
import com.lawfirm.model.evidence.mapper.EvidenceTraceMapper;
import com.lawfirm.model.evidence.service.EvidenceTraceService;
import com.lawfirm.evidence.converter.EvidenceTraceConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service("evidenceTraceService")
public class EvidenceTraceServiceImpl implements EvidenceTraceService {

    @Autowired
    private EvidenceTraceMapper evidenceTraceMapper;

    @Override
    public Long addTrace(EvidenceTraceDTO dto) {
        EvidenceTrace entity = EvidenceTraceConverter.toEntity(dto);
        evidenceTraceMapper.insert(entity);
        return entity.getId();
    }

    @Override
    public void updateTrace(EvidenceTraceDTO dto) {
        EvidenceTrace entity = evidenceTraceMapper.selectById(dto.getId());
        EvidenceTraceConverter.copyToEntity(dto, entity);
        evidenceTraceMapper.updateById(entity);
    }

    @Override
    public void deleteTrace(Long id) {
        evidenceTraceMapper.deleteById(id);
    }

    @Override
    public EvidenceTraceVO getTrace(Long id) {
        EvidenceTrace entity = evidenceTraceMapper.selectById(id);
        return EvidenceTraceConverter.toVO(entity);
    }

    @Override
    public List<EvidenceTraceVO> listByEvidence(Long evidenceId) {
        List<EvidenceTrace> list = evidenceTraceMapper.selectList(
            new com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<EvidenceTrace>().eq("evidence_id", evidenceId)
        );
        return list.stream().map(EvidenceTraceConverter::toVO).collect(Collectors.toList());
    }
} 