package com.lawfirm.model.cases.service.base;

import com.lawfirm.model.cases.dto.base.CaseBaseDTO;
import com.lawfirm.model.cases.dto.base.CaseCreateDTO;
import com.lawfirm.model.cases.dto.base.CaseQueryDTO;
import com.lawfirm.model.cases.dto.base.CaseUpdateDTO;
import com.lawfirm.model.cases.entity.base.Case;
import com.lawfirm.model.cases.vo.base.CaseDetailVO;
import com.lawfirm.model.cases.vo.base.CaseQueryVO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lawfirm.model.base.service.BaseService;

import java.util.List;
import java.util.Map;

/**
 * 案件基础服务接口
 */
public interface CaseService extends BaseService<Case> {

    /**
     * 创建案件
     *
     * @param createDTO 创建参数
     * @return 案件ID
     */
    Long createCase(CaseCreateDTO createDTO);

    /**
     * 更新案件
     *
     * @param updateDTO 更新参数
     * @return 是否成功
     */
    boolean updateCase(CaseUpdateDTO updateDTO);

    /**
     * 删除案件
     *
     * @param caseId 案件ID
     * @return 是否成功
     */
    boolean deleteCase(Long caseId);

    /**
     * 获取案件详情
     *
     * @param caseId 案件ID
     * @return 案件详情
     */
    CaseDetailVO getCaseDetail(Long caseId);

    /**
     * 分页查询案件列表
     *
     * @param queryDTO 查询参数
     * @return 分页结果
     */
    IPage<CaseQueryVO> pageCases(CaseQueryDTO queryDTO);

    /**
     * 变更案件状态
     *
     * @param caseId 案件ID
     * @param targetStatus 目标状态
     * @param reason 变更原因
     * @return 是否成功
     */
    boolean changeStatus(Long caseId, Integer targetStatus, String reason);

    /**
     * 审批案件状态变更
     *
     * @param caseId 案件ID
     * @param approved 是否通过
     * @param opinion 审批意见
     * @return 是否成功
     */
    boolean approveStatusChange(Long caseId, boolean approved, String opinion);

    /**
     * 归档案件
     *
     * @param caseId 案件ID
     * @return 是否成功
     */
    boolean archiveCase(Long caseId);

    /**
     * 重新激活案件
     *
     * @param caseId 案件ID
     * @param reason 激活原因
     * @return 是否成功
     */
    boolean reactivateCase(Long caseId, String reason);

    /**
     * 检查案件编号是否存在
     *
     * @param caseNumber 案件编号
     * @return 是否存在
     */
    boolean checkCaseNumberExists(String caseNumber);

    /**
     * 生成案件编号
     *
     * @param caseType 案件类型
     * @return 案件编号
     */
    String generateCaseNumber(Integer caseType);

    /**
     * 检查利益冲突
     *
     * @param baseDTO 案件基本信息
     * @return 是否存在冲突
     */
    boolean checkConflict(CaseBaseDTO baseDTO);

    /**
     * 统计案件数量
     *
     * @param queryDTO 查询参数
     * @return 数量
     */
    long countCases(CaseQueryDTO queryDTO);

    /**
     * 同步客户信息到相关案件
     *
     * @param clientId 客户ID
     */
    void syncClientInfo(Long clientId);

    /**
     * 标记客户相关案件为风险状态
     *
     * @param clientId 客户ID
     * @param reason 风险原因
     */
    void markCasesWithRisk(Long clientId, String reason);

    /**
     * 更新案件中的客户状态
     *
     * @param clientId 客户ID
     * @param newStatus 新状态
     */
    void updateClientStatusInCases(Long clientId, String newStatus);

    /**
     * 获取用户相关的案件列表
     *
     * @param userId 用户ID
     * @return 案件列表
     */
    List<Case> getUserCases(Long userId);
    
    /**
     * 评估案件风险
     *
     * @param caseId 案件ID
     * @return 风险评估结果
     */
    Map<String, Object> assessCaseRisk(Long caseId);
} 