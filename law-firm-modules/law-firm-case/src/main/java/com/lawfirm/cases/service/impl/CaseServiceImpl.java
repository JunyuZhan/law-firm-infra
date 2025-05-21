package com.lawfirm.cases.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lawfirm.cases.core.audit.CaseAuditProvider;
import com.lawfirm.cases.core.message.CaseMessageManager;
import com.lawfirm.cases.core.search.CaseSearchManager;
import com.lawfirm.cases.core.workflow.CaseWorkflowManager;
import com.lawfirm.cases.core.ai.CaseAIManager;
import com.lawfirm.cases.integration.client.ClientComponent;
import com.lawfirm.model.cases.mapper.base.CaseMapper;
import com.lawfirm.model.cases.mapper.team.CaseTeamMemberMapper;
import com.lawfirm.model.base.service.impl.BaseServiceImpl;
import com.lawfirm.model.cases.dto.base.CaseBaseDTO;
import com.lawfirm.model.cases.dto.base.CaseCreateDTO;
import com.lawfirm.model.cases.dto.base.CaseQueryDTO;
import com.lawfirm.model.cases.dto.base.CaseUpdateDTO;
import com.lawfirm.model.cases.entity.base.Case;
import com.lawfirm.model.cases.service.base.CaseService;
import com.lawfirm.model.cases.vo.base.CaseDetailVO;
import com.lawfirm.model.cases.vo.base.CaseQueryVO;
import com.lawfirm.model.client.dto.ClientDTO;
import com.lawfirm.model.archive.dto.CaseArchiveDTO;
import com.lawfirm.model.archive.service.ArchiveService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * 案件服务实现类
 */
@Slf4j
@Service("caseServiceImpl")
@RequiredArgsConstructor
public class CaseServiceImpl extends BaseServiceImpl<CaseMapper, Case> implements CaseService {

    private final CaseWorkflowManager workflowManager;
    private final CaseAuditProvider auditProvider;
    private final CaseSearchManager searchManager;
    private final CaseMessageManager messageManager;
    private final ClientComponent clientComponent;
    private final CaseTeamMemberMapper caseTeamMemberMapper;
    private final ArchiveService archiveService;
    private final CaseAIManager aiManager;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createCase(CaseCreateDTO createDTO) {
        log.info("创建案件: {}", createDTO.getCaseName());

        // 1. 生成案件编号
        if (createDTO.getCaseNumber() == null || createDTO.getCaseNumber().isEmpty()) {
            createDTO.setCaseNumber(generateCaseNumber(createDTO.getCaseType().getValue()));
        } else if (checkCaseNumberExists(createDTO.getCaseNumber())) {
            throw new RuntimeException("案件编号已存在: " + createDTO.getCaseNumber());
        }

        // 2. 创建案件实体
        Case caseEntity = new Case();
        BeanUtils.copyProperties(createDTO, caseEntity);
        caseEntity.setCreateTime(LocalDateTime.now());
        caseEntity.setUpdateTime(LocalDateTime.now());
        caseEntity.setCreateBy(createDTO.getCreatorId().toString());
        caseEntity.setCaseStatus(createDTO.getCaseStatus() != null ? createDTO.getCaseStatus().getValue() : 1); // 初始状态：待处理

        // 3. 保存案件
        save(caseEntity);
        Long caseId = caseEntity.getId();

        // 4. 启动工作流
        Map<String, Object> variables = new HashMap<>();
        variables.put("caseId", caseId);
        variables.put("caseType", createDTO.getCaseType().getValue());
        variables.put("creatorId", createDTO.getCreatorId());
        String processInstanceId = workflowManager.startCaseWorkflow(caseId, variables);

        // 5. 更新流程实例ID
        caseEntity.setCaseNumber(processInstanceId);  // 临时使用caseNumber保存processInstanceId
        updateById(caseEntity);

        // 6. 记录审计
        auditProvider.auditCaseCreation(caseId, createDTO.getCreatorId(), createDTO);

        // 7. 索引案件数据
        Map<String, Object> caseData = new HashMap<>();
        BeanUtils.copyProperties(caseEntity, caseData);
        searchManager.indexCaseData(caseId, caseData);

        // 8. 发送案件创建消息
        messageManager.sendCaseStatusChangeMessage(
                caseId,
                null,
                "1", // 初始状态：待处理
                createDTO.getCreatorId(),
                "案件创建"
        );

        log.info("案件创建成功, ID: {}", caseId);
        return caseId;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateCase(CaseUpdateDTO updateDTO) {
        log.info("更新案件: {}", updateDTO.getId());

        // 1. 获取原案件数据
        Case oldCase = getById(updateDTO.getId());
        if (oldCase == null) {
            throw new RuntimeException("案件不存在: " + updateDTO.getId());
        }

        // 2. 更新案件
        Case caseEntity = new Case();
        BeanUtils.copyProperties(updateDTO, caseEntity);
        caseEntity.setUpdateTime(LocalDateTime.now());
        caseEntity.setUpdateBy(updateDTO.getUpdaterId().toString());

        boolean result = updateById(caseEntity);

        // 3. 记录审计
        Map<String, Object> changedFields = new HashMap<>();
        // 在实际实现中，应比较oldCase和updateDTO，提取变更字段
        auditProvider.auditCaseUpdate(
                updateDTO.getId(),
                updateDTO.getUpdaterId(),
                oldCase,
                caseEntity,
                changedFields
        );

        // 4. 更新索引
        Map<String, Object> updateData = new HashMap<>();
        BeanUtils.copyProperties(caseEntity, updateData);
        searchManager.updateCaseIndex(updateDTO.getId(), updateData);

        log.info("案件更新成功, ID: {}", updateDTO.getId());
        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteCase(Long caseId) {
        log.info("删除案件: {}", caseId);

        // 在实际应用中，通常不会物理删除案件，而是逻辑删除
        Case caseEntity = getById(caseId);
        if (caseEntity == null) {
            throw new RuntimeException("案件不存在: " + caseId);
        }

        caseEntity.setDeleted(1); // 逻辑删除
        caseEntity.setUpdateTime(LocalDateTime.now());
        boolean result = updateById(caseEntity);

        // 删除索引
        searchManager.deleteCaseIndex(caseId);

        log.info("案件删除成功, ID: {}", caseId);
        return result;
    }

    @Override
    public CaseDetailVO getCaseDetail(Long caseId) {
        log.info("获取案件详情: {}", caseId);

        Case caseEntity = getById(caseId);
        if (caseEntity == null) {
            throw new RuntimeException("案件不存在: " + caseId);
        }

        CaseDetailVO detailVO = new CaseDetailVO();
        BeanUtils.copyProperties(caseEntity, detailVO);

        // 获取工作流状态
        if (caseEntity.getCaseNumber() != null) {  // 临时使用caseNumber作为processInstanceId
            detailVO.setProcessStatus(
                    workflowManager.getProcessStatus(caseEntity.getCaseNumber())
            );
        }

        return detailVO;
    }

    @Override
    public IPage<CaseQueryVO> pageCases(CaseQueryDTO queryDTO) {
        log.info("分页查询案件: page={}, size={}", queryDTO.getPageNum(), queryDTO.getPageSize());

        Page<Case> page = new Page<>(queryDTO.getPageNum(), queryDTO.getPageSize());
        LambdaQueryWrapper<Case> wrapper = new LambdaQueryWrapper<>();

        // 构建查询条件
        if (queryDTO.getCaseName() != null && !queryDTO.getCaseName().isEmpty()) {
            wrapper.like(Case::getCaseName, queryDTO.getCaseName());
        }
        if (queryDTO.getCaseNumber() != null && !queryDTO.getCaseNumber().isEmpty()) {
            wrapper.eq(Case::getCaseNumber, queryDTO.getCaseNumber());
        }
        if (queryDTO.getCaseStatus() != null) {
            wrapper.eq(Case::getCaseStatus, queryDTO.getCaseStatus().getValue());
        }
        if (queryDTO.getCaseType() != null) {
            wrapper.eq(Case::getCaseType, queryDTO.getCaseType().getValue());
        }
        if (queryDTO.getFilingTimeStart() != null) {
            wrapper.ge(Case::getCreateTime, queryDTO.getFilingTimeStart());
        }
        if (queryDTO.getFilingTimeEnd() != null) {
            wrapper.le(Case::getCreateTime, queryDTO.getFilingTimeEnd());
        }
        
        // 只查询未删除的记录
        wrapper.eq(Case::getDeleted, 0);
        
        // 排序
        wrapper.orderByDesc(Case::getUpdateTime);

        // 执行查询
        IPage<Case> resultPage = page(page, wrapper);

        // 转换为VO
        return resultPage.convert(entity -> {
            CaseQueryVO vo = new CaseQueryVO();
            BeanUtils.copyProperties(entity, vo);
            return vo;
        });
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean changeStatus(Long caseId, Integer targetStatus, String reason) {
        log.info("变更案件状态: caseId={}, targetStatus={}", caseId, targetStatus);

        Case caseEntity = getById(caseId);
        if (caseEntity == null) {
            throw new RuntimeException("案件不存在: " + caseId);
        }

        Integer oldStatus = caseEntity.getCaseStatus();
        
        // 更新状态
        caseEntity.setCaseStatus(targetStatus);
        caseEntity.setUpdateTime(LocalDateTime.now());
        boolean result = updateById(caseEntity);

        // 更新工作流状态
        if (caseEntity.getCaseNumber() != null) {  // 临时使用caseNumber作为processInstanceId
            Map<String, Object> variables = new HashMap<>();
            variables.put("status", targetStatus);
            variables.put("reason", reason);
            workflowManager.updateCaseStatus(
                    caseId,
                    caseEntity.getCaseNumber(),
                    targetStatus.toString(),
                    variables
            );
        }

        // 记录审计
        auditProvider.auditCaseStatusChange(
                caseId,
                Long.parseLong(caseEntity.getUpdateBy()),
                oldStatus.toString(),
                targetStatus.toString(),
                reason
        );

        // 更新索引
        Map<String, Object> updateData = new HashMap<>();
        updateData.put("status", targetStatus);
        searchManager.updateCaseIndex(caseId, updateData);

        // 发送状态变更消息
        messageManager.sendCaseStatusChangeMessage(
                caseId,
                oldStatus.toString(),
                targetStatus.toString(),
                Long.parseLong(caseEntity.getUpdateBy()),
                reason
        );

        log.info("案件状态变更成功, ID: {}", caseId);
        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean approveStatusChange(Long caseId, boolean approved, String opinion) {
        log.info("审批案件状态变更: caseId={}, approved={}", caseId, approved);

        Case caseEntity = getById(caseId);
        if (caseEntity == null) {
            throw new RuntimeException("案件不存在: " + caseId);
        }

        // 根据审批结果更新状态
        // 此处逻辑应根据具体业务需求实现
        
        // 记录审计
        // 发送消息

        log.info("案件状态变更审批完成, ID: {}", caseId);
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean archiveCase(Long caseId) {
        log.info("归档案件: {}", caseId);

        Case caseEntity = getById(caseId);
        if (caseEntity == null) {
            throw new RuntimeException("案件不存在: " + caseId);
        }

        // 更新状态为归档
        Integer oldStatus = caseEntity.getCaseStatus();
        caseEntity.setCaseStatus(9); // 归档状态
        caseEntity.setClosingTime(LocalDateTime.now().toLocalDate()); // 使用closingTime替代archiveTime
        caseEntity.setUpdateTime(LocalDateTime.now());
        boolean result = updateById(caseEntity);

        // 终止工作流
        if (caseEntity.getCaseNumber() != null) {  // 临时使用caseNumber作为processInstanceId
            workflowManager.terminateCaseProcess(
                    caseEntity.getCaseNumber(),
                    "案件归档"
            );
        }

        // 记录审计
        auditProvider.auditCaseStatusChange(
                caseId,
                Long.parseLong(caseEntity.getUpdateBy()),
                oldStatus.toString(),
                "9", // 归档状态
                "案件归档"
        );

        // 更新索引
        Map<String, Object> updateData = new HashMap<>();
        updateData.put("status", 9);
        updateData.put("closingTime", caseEntity.getClosingTime());
        searchManager.updateCaseIndex(caseId, updateData);

        // 发送状态变更消息
        messageManager.sendCaseStatusChangeMessage(
                caseId,
                oldStatus.toString(),
                "9", // 归档状态
                Long.parseLong(caseEntity.getUpdateBy()),
                "案件归档"
        );

        // ====== 新增：同步到档案管理模块 ======
        try {
            CaseArchiveDTO archiveDTO = new CaseArchiveDTO();
            archiveDTO.setCaseId(caseEntity.getId());
            archiveDTO.setCaseTitle(caseEntity.getCaseName());
            archiveDTO.setCaseNo(caseEntity.getCaseNumber());
            archiveDTO.setCaseType(caseEntity.getCaseType() != null ? caseEntity.getCaseType().toString() : null);
            archiveDTO.setLawyerId(caseEntity.getLawyerId());
            archiveDTO.setLawyerName(caseEntity.getLawyerName());
            archiveDTO.setDepartmentId(caseEntity.getDepartmentId());
            archiveDTO.setDepartmentName(caseEntity.getDepartmentName());
            archiveDTO.setClientId(caseEntity.getClientId());
            archiveDTO.setClientName(caseEntity.getClientName());
            archiveDTO.setStartTime(caseEntity.getFilingTime() != null ? caseEntity.getFilingTime().toString() : null);
            archiveDTO.setEndTime(caseEntity.getClosingTime() != null ? caseEntity.getClosingTime().toString() : null);
            archiveDTO.setHandlerId(caseEntity.getLastOperatorId());
            archiveDTO.setHandlerName(caseEntity.getLastOperatorName());
            archiveDTO.setCaseStatus(caseEntity.getCaseStatus() != null ? caseEntity.getCaseStatus().toString() : null);
            archiveDTO.setCaseAmount(caseEntity.getActualAmount() != null ? caseEntity.getActualAmount().doubleValue() : null);
            archiveDTO.setRemark(caseEntity.getRemarks());
            // archiveDTO.setFileList(assembleArchiveFileList(caseEntity)); // 如有归档文件可补充
            archiveService.createCaseArchive(archiveDTO);
        } catch (Exception e) {
            log.error("同步案件归档到档案管理模块失败，caseId={}", caseId, e);
        }
        // ====== 新增结束 ======

        log.info("案件归档成功, ID: {}", caseId);
        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean reactivateCase(Long caseId, String reason) {
        log.info("重新激活案件: {}", caseId);

        Case caseEntity = getById(caseId);
        if (caseEntity == null) {
            throw new RuntimeException("案件不存在: " + caseId);
        }

        // 检查是否归档状态
        if (caseEntity.getCaseStatus() != 9) {
            throw new RuntimeException("只有归档状态的案件才能重新激活");
        }

        // 更新状态为进行中
        Integer oldStatus = caseEntity.getCaseStatus();
        caseEntity.setCaseStatus(2); // 进行中状态
        caseEntity.setClosingTime(null); // 清除归档时间
        caseEntity.setUpdateTime(LocalDateTime.now());
        boolean result = updateById(caseEntity);

        // 恢复工作流
        if (caseEntity.getCaseNumber() != null) {  // 临时使用caseNumber作为processInstanceId
            workflowManager.resumeCaseProcess(caseEntity.getCaseNumber());
        }

        // 记录审计
        auditProvider.auditCaseStatusChange(
                caseId,
                Long.parseLong(caseEntity.getUpdateBy()),
                oldStatus.toString(),
                "2", // 进行中状态
                reason
        );

        // 更新索引
        Map<String, Object> updateData = new HashMap<>();
        updateData.put("status", 2);
        updateData.put("closingTime", null);
        searchManager.updateCaseIndex(caseId, updateData);

        // 发送状态变更消息
        messageManager.sendCaseStatusChangeMessage(
                caseId,
                oldStatus.toString(),
                "2", // 进行中状态
                Long.parseLong(caseEntity.getUpdateBy()),
                reason
        );

        log.info("案件重新激活成功, ID: {}", caseId);
        return result;
    }

    @Override
    public boolean checkCaseNumberExists(String caseNumber) {
        log.info("检查案件编号是否存在: {}", caseNumber);

        LambdaQueryWrapper<Case> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Case::getCaseNumber, caseNumber);
        wrapper.eq(Case::getDeleted, 0);
        return count(wrapper) > 0;
    }

    @Override
    public String generateCaseNumber(Integer caseType) {
        log.info("生成案件编号, 类型: {}", caseType);

        // 案件编号生成规则: 年月+类型+4位序号
        // 实际应用中可能需要更复杂的生成规则和序号管理
        LocalDateTime now = LocalDateTime.now();
        String yearMonth = String.format("%d%02d", now.getYear(), now.getMonthValue());
        String typeCode = String.format("%02d", caseType);
        String randomPart = UUID.randomUUID().toString().substring(0, 4).toUpperCase();

        return "C" + yearMonth + typeCode + randomPart;
    }

    @Override
    public boolean checkConflict(CaseBaseDTO baseDTO) {
        log.info("检查利益冲突: {}", baseDTO.getCaseName());
        
        // 在实际应用中，需要调用冲突检查服务进行检查
        // 此处仅为示例，返回无冲突
        
        return false;
    }

    @Override
    public long countCases(CaseQueryDTO queryDTO) {
        log.info("统计案件数量");

        LambdaQueryWrapper<Case> wrapper = new LambdaQueryWrapper<>();

        // 构建查询条件
        if (queryDTO.getCaseName() != null && !queryDTO.getCaseName().isEmpty()) {
            wrapper.like(Case::getCaseName, queryDTO.getCaseName());
        }
        if (queryDTO.getCaseNumber() != null && !queryDTO.getCaseNumber().isEmpty()) {
            wrapper.eq(Case::getCaseNumber, queryDTO.getCaseNumber());
        }
        if (queryDTO.getCaseStatus() != null) {
            wrapper.eq(Case::getCaseStatus, queryDTO.getCaseStatus().getValue());
        }
        if (queryDTO.getCaseType() != null) {
            wrapper.eq(Case::getCaseType, queryDTO.getCaseType().getValue());
        }
        if (queryDTO.getFilingTimeStart() != null) {
            wrapper.ge(Case::getCreateTime, queryDTO.getFilingTimeStart());
        }
        if (queryDTO.getFilingTimeEnd() != null) {
            wrapper.le(Case::getCreateTime, queryDTO.getFilingTimeEnd());
        }
        
        // 只统计未删除的记录
        wrapper.eq(Case::getDeleted, 0);

        return count(wrapper);
    }

    /**
     * 更新案件中的客户状态
     *
     * @param clientId 客户ID
     * @param newStatus 新状态
     */
    @Override
    public void updateClientStatusInCases(Long clientId, String newStatus) {
        log.info("更新客户{}的案件状态为{}", clientId, newStatus);
        
        // 查询该客户相关的所有案件
        List<Case> cases = lambdaQuery()
                .eq(Case::getClientId, clientId)
                .list();
                
        if (cases.isEmpty()) {
            return;
        }
        
        // 更新案件中的客户状态
        cases.forEach(caseInfo -> {
            caseInfo.setClientStatus(newStatus);
            updateById(caseInfo);
            
            // 记录状态变更
            log.info("案件{}的客户状态已更新为{}", caseInfo.getId(), newStatus);
        });
    }

    /**
     * 同步客户信息到相关案件
     *
     * @param clientId 客户ID
     */
    @Override
    public void syncClientInfo(Long clientId) {
        log.info("同步客户{}的信息到相关案件", clientId);
        
        // 获取客户信息
        ClientDTO client = clientComponent.getClientDetail(clientId);
        if (client == null) {
            log.warn("客户{}不存在", clientId);
            return;
        }
        
        // 查询该客户相关的所有案件
        List<Case> cases = lambdaQuery()
                .eq(Case::getClientId, clientId)
                .list();
                
        if (cases.isEmpty()) {
            return;
        }
        
        // 更新案件中的客户信息
        cases.forEach(caseInfo -> {
            caseInfo.setClientName(client.getClientName());
            updateById(caseInfo);
            
            // 记录信息同步
            log.info("案件{}的客户信息已同步", caseInfo.getId());
        });
    }

    /**
     * 标记客户相关案件为风险状态
     *
     * @param clientId 客户ID
     * @param reason 风险原因
     */
    @Override
    public void markCasesWithRisk(Long clientId, String reason) {
        log.info("标记客户{}的案件为风险状态，原因：{}", clientId, reason);
        
        // 查询该客户相关的所有案件
        List<Case> cases = lambdaQuery()
                .eq(Case::getClientId, clientId)
                .list();
                
        if (cases.isEmpty()) {
            return;
        }
        
        // 更新案件状态为风险状态
        cases.forEach(caseInfo -> {
            caseInfo.setRiskStatus(1);
            caseInfo.setRiskReason(reason);
            updateById(caseInfo);
            
            // 记录状态变更
            log.info("案件{}已标记为风险状态", caseInfo.getId());
        });
    }

    /**
     * 获取用户相关的全部案件
     * 
     * @param userId 用户ID
     * @return 案件列表
     */
    @Override
    public List<Case> getUserCases(Long userId) {
        log.info("获取用户{}相关的案件列表", userId);
        
        // 先查询用户参与的所有案件ID
        List<Long> teamCaseIds = caseTeamMemberMapper.selectCaseIdsByMemberId(userId);
        
        // 再使用ID列表查询完整案件信息
        return lambdaQuery()
                .eq(Case::getLeaderId, userId)
                .or()
                .in(teamCaseIds != null && !teamCaseIds.isEmpty(), Case::getId, teamCaseIds)
                .list();
    }

    /**
     * 评估案件风险
     *
     * @param caseId 案件ID
     * @return 风险评估结果
     */
    @Override
    public Map<String, Object> assessCaseRisk(Long caseId) {
        log.info("评估案件风险: {}", caseId);

        // 获取案件信息
        Case caseEntity = getById(caseId);
        if (caseEntity == null) {
            throw new RuntimeException("案件不存在: " + caseId);
        }

        // 转换为数据Map
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("caseName", caseEntity.getCaseName());
        caseData.put("caseType", caseEntity.getCaseType());
        caseData.put("caseStatus", caseEntity.getCaseStatus());
        caseData.put("clientId", caseEntity.getClientId());
        caseData.put("clientName", caseEntity.getClientName());
        caseData.put("lawyer", caseEntity.getLawyerId());
        caseData.put("filingDate", caseEntity.getFilingTime());
        
        // 调用AI服务进行风险评估
        Map<String, Object> riskResult = aiManager.getCaseRiskAssessment(caseId, caseData);
        
        // 记录审计
        auditProvider.auditCaseUpdate(
                caseId,
                Long.valueOf(caseEntity.getCreateBy()),
                null,
                null,
                Map.of("action", "风险评估")
        );
        
        log.info("案件风险评估完成, ID: {}", caseId);
        return riskResult;
    }
}
