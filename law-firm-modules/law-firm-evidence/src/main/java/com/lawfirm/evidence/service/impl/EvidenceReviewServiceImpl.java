package com.lawfirm.evidence.service.impl;

import com.lawfirm.model.evidence.dto.EvidenceReviewDTO;
import com.lawfirm.model.evidence.vo.EvidenceReviewVO;
import com.lawfirm.model.evidence.entity.EvidenceReview;
import com.lawfirm.model.evidence.mapper.EvidenceReviewMapper;
import com.lawfirm.model.evidence.service.EvidenceReviewService;
import com.lawfirm.evidence.converter.EvidenceReviewConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service("evidenceReviewService")
public class EvidenceReviewServiceImpl implements EvidenceReviewService {
    @Autowired
    private EvidenceReviewMapper evidenceReviewMapper;

    @Override
    public Long addReview(EvidenceReviewDTO dto) {
        EvidenceReview entity = EvidenceReviewConverter.toEntity(dto);
        evidenceReviewMapper.insert(entity);
        return entity.getId();
    }

    @Override
    public void updateReview(EvidenceReviewDTO dto) {
        EvidenceReview entity = evidenceReviewMapper.selectById(dto.getId());
        EvidenceReviewConverter.copyToEntity(dto, entity);
        evidenceReviewMapper.updateById(entity);
    }

    @Override
    public void deleteReview(Long id) {
        evidenceReviewMapper.deleteById(id);
    }

    @Override
    public EvidenceReviewVO getReview(Long id) {
        EvidenceReview entity = evidenceReviewMapper.selectById(id);
        return EvidenceReviewConverter.toVO(entity);
    }

    @Override
    public List<EvidenceReviewVO> listByEvidence(Long evidenceId) {
        List<EvidenceReview> list = evidenceReviewMapper.selectList(
            new com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<EvidenceReview>().eq("evidence_id", evidenceId)
        );
        return list.stream().map(EvidenceReviewConverter::toVO).collect(Collectors.toList());
    }
} 