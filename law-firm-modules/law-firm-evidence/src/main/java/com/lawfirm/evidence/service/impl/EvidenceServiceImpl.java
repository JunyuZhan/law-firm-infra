package com.lawfirm.evidence.service.impl;

import com.lawfirm.model.evidence.dto.EvidenceDTO;
import com.lawfirm.model.evidence.vo.EvidenceVO;
import com.lawfirm.model.evidence.service.EvidenceService;
import com.lawfirm.model.evidence.entity.Evidence;
import com.lawfirm.model.evidence.mapper.EvidenceMapper;
import com.lawfirm.evidence.converter.EvidenceConverter;
import com.lawfirm.model.ai.service.DocProcessService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import java.util.stream.Collectors;
import java.time.LocalDateTime;
import java.util.Map;

@Service("evidenceService")
public class EvidenceServiceImpl implements EvidenceService {
    @Autowired
    private EvidenceMapper evidenceMapper;

    @Autowired
    @Qualifier("evidenceDocProcessService")
    private DocProcessService docProcessService;

    @Override
    public Long addEvidence(EvidenceDTO dto) {
        Evidence entity = EvidenceConverter.toEntity(dto);
        evidenceMapper.insert(entity);
        return entity.getId();
    }

    @Override
    public void updateEvidence(EvidenceDTO dto) {
        Evidence entity = evidenceMapper.selectById(dto.getId());
        EvidenceConverter.copyToEntity(dto, entity);
        evidenceMapper.updateById(entity);
    }

    @Override
    public void deleteEvidence(Long evidenceId) {
        evidenceMapper.deleteById(evidenceId);
    }

    @Override
    public EvidenceVO getEvidence(Long evidenceId) {
        Evidence entity = evidenceMapper.selectById(evidenceId);
        if (entity == null) return null;
        EvidenceVO vo = new EvidenceVO();
        vo.setId(entity.getId());
        vo.setCaseId(entity.getCaseId());
        vo.setName(entity.getName());
        vo.setType(entity.getType());
        vo.setSource(entity.getSource());
        vo.setProofMatter(entity.getProofMatter());
        vo.setSubmitterId(entity.getSubmitterId());
        vo.setSubmitTime(entity.getSubmitTime());
        vo.setEvidenceChain(entity.getEvidenceChain());
        vo.setChallengeStatus(entity.getChallengeStatus());
        // vo.setDocumentIds(entity.getDocumentIds()); // 需特殊处理
        return vo;
    }

    @Override
    public List<EvidenceVO> listEvidenceByCase(Long caseId) {
        List<Evidence> list = evidenceMapper.selectList(
            new com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<Evidence>().eq("case_id", caseId)
        );
        return list.stream().map(entity -> {
            EvidenceVO vo = new EvidenceVO();
            vo.setId(entity.getId());
            vo.setCaseId(entity.getCaseId());
            vo.setName(entity.getName());
            vo.setType(entity.getType());
            vo.setSource(entity.getSource());
            vo.setProofMatter(entity.getProofMatter());
            vo.setSubmitterId(entity.getSubmitterId());
            vo.setSubmitTime(entity.getSubmitTime());
            vo.setEvidenceChain(entity.getEvidenceChain());
            vo.setChallengeStatus(entity.getChallengeStatus());
            // vo.setDocumentIds(entity.getDocumentIds()); // 需特殊处理
            return vo;
        }).collect(Collectors.toList());
    }

    @Override
    public void archiveEvidence(Long evidenceId) {
        Evidence entity = evidenceMapper.selectById(evidenceId);
        if (entity == null) return;
        if (Boolean.TRUE.equals(entity.getArchived())) return; // 已归档不重复归档
        entity.setArchived(true);
        entity.setArchiveTime(LocalDateTime.now());
        evidenceMapper.updateById(entity);
    }

    @Override
    public Map<String, Object> extractEvidenceInfo(Long evidenceId) {
        Evidence entity = evidenceMapper.selectById(evidenceId);
        if (entity == null) throw new RuntimeException("证据不存在");
        String content = entity.getProofMatter();
        if (content == null || content.isEmpty()) throw new RuntimeException("证据内容为空");
        return docProcessService.extractDocumentInfo(content, null);
    }
} 