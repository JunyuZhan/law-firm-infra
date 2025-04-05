package com.lawfirm.schedule.integration;

import com.lawfirm.model.cases.entity.base.Case;
import com.lawfirm.model.cases.service.base.CaseService;
import com.lawfirm.model.cases.vo.base.CaseDetailVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 案件模块集成组件
 * 用于在日程模块中获取案件信息
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class CaseIntegration {
    
    private final CaseService caseService;
    
    /**
     * 获取案件信息
     *
     * @param caseId 案件ID
     * @return 案件信息
     */
    public CaseDetailVO getCaseInfo(Long caseId) {
        if (caseId == null) {
            return null;
        }
        
        try {
            return caseService.getCaseDetail(caseId);
        } catch (Exception e) {
            log.error("获取案件信息失败，案件ID：{}", caseId, e);
            return null;
        }
    }
    
    /**
     * 批量获取案件信息
     *
     * @param caseIds 案件ID集合
     * @return 案件信息映射 (caseId -> CaseDetailVO)
     */
    public Map<Long, CaseDetailVO> getCasesInfo(Set<Long> caseIds) {
        // TODO: 对接案件模块，实现批量获取案件信息
        log.info("批量获取案件信息，案件ID数量：{}", caseIds.size());
        
        if (caseIds.isEmpty()) {
            return Collections.emptyMap();
        }
        
        // 临时实现，生成模拟数据
        Map<Long, CaseDetailVO> result = new HashMap<>();
        for (Long caseId : caseIds) {
            CaseDetailVO caseDetail = new CaseDetailVO();
            caseDetail.setId(caseId);
            caseDetail.setCaseName("案件" + caseId);
            caseDetail.setCaseNumber("CASE-" + caseId);
            caseDetail.setStatus(1);
            caseDetail.setDescription("这是一个模拟案件");
            caseDetail.setClientId(100L + caseId % 10);
            caseDetail.setClientName("客户" + (100L + caseId % 10));
            
            result.put(caseId, caseDetail);
        }
        
        return result;
    }
    
    /**
     * 检查案件是否存在
     *
     * @param caseId 案件ID
     * @return 是否存在
     */
    public boolean caseExists(Long caseId) {
        // TODO: 对接案件模块，实现案件存在性检查
        log.info("检查案件是否存在，案件ID：{}", caseId);
        // 临时实现，假设所有案件ID都存在
        return caseId != null;
    }
    
    /**
     * 获取案件负责人ID
     *
     * @param caseId 案件ID
     * @return 负责人ID
     */
    public Long getCaseOwnerId(Long caseId) {
        if (caseId == null) {
            return null;
        }
        
        try {
            CaseDetailVO caseVO = caseService.getCaseDetail(caseId);
            return caseVO != null ? caseVO.getLeaderId() : null;
        } catch (Exception e) {
            log.error("获取案件负责人失败，案件ID：{}", caseId, e);
            return null;
        }
    }
} 