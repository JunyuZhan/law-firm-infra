package com.lawfirm.api.adaptor.cases;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lawfirm.api.adaptor.BaseAdaptor;
import com.lawfirm.model.cases.dto.base.CaseCreateDTO;
import com.lawfirm.model.cases.dto.base.CaseQueryDTO;
import com.lawfirm.model.cases.dto.base.CaseUpdateDTO;
import com.lawfirm.model.cases.entity.base.Case;
import com.lawfirm.model.cases.service.base.CaseService;
import com.lawfirm.model.cases.vo.base.CaseDetailVO;
import com.lawfirm.model.cases.vo.base.CaseQueryVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 案件适配器
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class CaseAdaptor extends BaseAdaptor {

    private final CaseService caseService;

    /**
     * 创建案件
     */
    public Long createCase(CaseCreateDTO createDTO) {
        log.info("创建案件: {}", createDTO);
        return caseService.createCase(createDTO);
    }

    /**
     * 更新案件
     */
    public boolean updateCase(Long id, CaseUpdateDTO updateDTO) {
        log.info("更新案件: id={}, {}", id, updateDTO);
        updateDTO.setId(id);
        return caseService.updateCase(updateDTO);
    }

    /**
     * 删除案件
     */
    public boolean deleteCase(Long id) {
        log.info("删除案件: {}", id);
        return caseService.deleteCase(id);
    }

    /**
     * 获取案件详情
     */
    public CaseDetailVO getCaseDetail(Long id) {
        log.info("获取案件详情: {}", id);
        return caseService.getCaseDetail(id);
    }

    /**
     * 分页查询案件
     */
    public IPage<CaseQueryVO> pageCases(CaseQueryDTO queryDTO) {
        log.info("分页查询案件: {}", queryDTO);
        return caseService.pageCases(queryDTO);
    }

    /**
     * 变更案件状态
     */
    public boolean changeStatus(Long id, Integer targetStatus, String reason) {
        log.info("变更案件状态: id={}, targetStatus={}, reason={}", id, targetStatus, reason);
        return caseService.changeStatus(id, targetStatus, reason);
    }

    /**
     * 审批案件状态变更
     */
    public boolean approveStatusChange(Long id, boolean approved, String opinion) {
        log.info("审批案件状态变更: id={}, approved={}, opinion={}", id, approved, opinion);
        return caseService.approveStatusChange(id, approved, opinion);
    }

    /**
     * 归档案件
     */
    public boolean archiveCase(Long id) {
        log.info("归档案件: {}", id);
        return caseService.archiveCase(id);
    }

    /**
     * 重新激活案件
     */
    public boolean reactivateCase(Long id, String reason) {
        log.info("重新激活案件: id={}, reason={}", id, reason);
        return caseService.reactivateCase(id, reason);
    }

    /**
     * 获取用户相关案件
     */
    public List<CaseQueryVO> getUserCases(Long userId) {
        log.info("获取用户相关案件: userId={}", userId);
        List<Case> cases = caseService.getUserCases(userId);
        return cases.stream()
                .map(c -> convert(c, CaseQueryVO.class))
                .collect(Collectors.toList());
    }
} 