package com.lawfirm.evidence.service.impl;

import com.lawfirm.model.evidence.dto.EvidenceChallengeDTO;
import com.lawfirm.model.evidence.vo.EvidenceChallengeVO;
import com.lawfirm.model.evidence.entity.EvidenceChallenge;
import com.lawfirm.model.evidence.mapper.EvidenceChallengeMapper;
import com.lawfirm.model.evidence.service.EvidenceChallengeService;
import com.lawfirm.evidence.converter.EvidenceChallengeConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service("evidenceChallengeService")
public class EvidenceChallengeServiceImpl implements EvidenceChallengeService {
    @Autowired
    private EvidenceChallengeMapper evidenceChallengeMapper;

    @Override
    public Long addChallenge(EvidenceChallengeDTO dto) {
        EvidenceChallenge entity = EvidenceChallengeConverter.toEntity(dto);
        evidenceChallengeMapper.insert(entity);
        return entity.getId();
    }

    @Override
    public void updateChallenge(EvidenceChallengeDTO dto) {
        EvidenceChallenge entity = evidenceChallengeMapper.selectById(dto.getId());
        EvidenceChallengeConverter.copyToEntity(dto, entity);
        evidenceChallengeMapper.updateById(entity);
    }

    @Override
    public void deleteChallenge(Long id) {
        evidenceChallengeMapper.deleteById(id);
    }

    @Override
    public EvidenceChallengeVO getChallenge(Long id) {
        EvidenceChallenge entity = evidenceChallengeMapper.selectById(id);
        return EvidenceChallengeConverter.toVO(entity);
    }

    @Override
    public List<EvidenceChallengeVO> listByEvidence(Long evidenceId) {
        List<EvidenceChallenge> list = evidenceChallengeMapper.selectList(
            new com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<EvidenceChallenge>().eq("evidence_id", evidenceId)
        );
        return list.stream().map(EvidenceChallengeConverter::toVO).collect(Collectors.toList());
    }
} 