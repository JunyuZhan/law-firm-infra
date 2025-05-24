package com.lawfirm.analysis.service.impl;

import com.lawfirm.model.analysis.dto.AnalysisRequestDTO;
import com.lawfirm.model.analysis.service.IAnalysisService;
import com.lawfirm.model.analysis.vo.CaseAnalysisResultVO;
import com.lawfirm.model.cases.service.base.CaseService;
import com.lawfirm.model.cases.dto.base.CaseQueryDTO;
import com.lawfirm.model.cases.enums.base.CaseTypeEnum;
import com.lawfirm.model.cases.vo.base.CaseQueryVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.ArrayList;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lawfirm.core.message.service.MessageSender;
import com.lawfirm.core.storage.service.support.FileOperator;
import com.lawfirm.model.search.service.SearchService;
import org.springframework.beans.factory.annotation.Qualifier;
import com.lawfirm.model.log.service.AuditService;
import com.lawfirm.model.ai.service.AiService;

/**
 * 分析服务实现
 */
@Service("analysisServiceImpl")
public class AnalysisServiceImpl implements IAnalysisService {
    @Autowired
    private CaseService caseService;

    @Autowired
    @Qualifier("analysisMessageSender")
    private MessageSender messageSender;

    @Autowired
    @Qualifier("analysisFileOperator")
    private FileOperator fileOperator;

    @Autowired(required = false)
    @Qualifier("analysisSearchService")
    private SearchService searchService;

    @Autowired
    @Qualifier("analysisAuditService")
    private AuditService auditService;

    @Autowired(required = false)
    @Qualifier("analysisAiService")
    private AiService aiService;

    @Override
    public List<CaseAnalysisResultVO> caseAnalysis(AnalysisRequestDTO requestDTO) {
        // 1. 构建查询参数
        CaseQueryDTO queryDTO = new CaseQueryDTO();
        if (requestDTO.getStartDate() != null) {
            queryDTO.setFilingTimeStart(requestDTO.getStartDate());
        }
        if (requestDTO.getEndDate() != null) {
            queryDTO.setFilingTimeEnd(requestDTO.getEndDate());
        }
        // 2. 查询全部案件（不分页）
        // 这里假设有list(QueryWrapper)方法，实际可根据BaseService实现调整
        // 若无直接list方法，可用pageCases+大页码实现
        // 这里只做演示，实际应考虑数据量大时的处理
        List<CaseQueryVO> caseList = caseService.list(new QueryWrapper<>())
            .stream()
            .map(caseEntity -> {
                CaseQueryVO vo = new CaseQueryVO();
                vo.setCaseTypeEnum(caseEntity.getCaseTypeEnum());
                return vo;
            })
            .collect(Collectors.toList());
        // 3. 按类型分组统计
        Map<CaseTypeEnum, Long> typeCount = caseList.stream()
            .collect(Collectors.groupingBy(CaseQueryVO::getCaseTypeEnum, Collectors.counting()));
        // 4. 组装结果
        List<CaseAnalysisResultVO> result = new ArrayList<>();
        for (Map.Entry<CaseTypeEnum, Long> entry : typeCount.entrySet()) {
            CaseAnalysisResultVO vo = new CaseAnalysisResultVO();
            vo.setCaseType(entry.getKey() != null ? entry.getKey().getDescription() : "未知类型");
            vo.setTotal(entry.getValue().intValue());
            result.add(vo);
        }
        return result;
    }
} 