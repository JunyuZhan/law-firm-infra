package com.lawfirm.model.cases.service.business;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lawfirm.model.cases.dto.business.CaseApprovalDTO;
import com.lawfirm.model.cases.vo.business.CaseApprovalVO;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 案件审批服务接口
 */
public interface CaseApprovalService {

    /**
     * 发起审批
     *
     * @param approvalDTO 审批信息
     * @return 审批ID
     */
    Long initiateApproval(CaseApprovalDTO approvalDTO);

    /**
     * 更新审批信息
     *
     * @param approvalDTO 审批信息
     * @return 是否成功
     */
    boolean updateApproval(CaseApprovalDTO approvalDTO);

    /**
     * 取消审批
     *
     * @param approvalId 审批ID
     * @param reason 取消原因
     * @return 是否成功
     */
    boolean cancelApproval(Long approvalId, String reason);

    /**
     * 获取审批详情
     *
     * @param approvalId 审批ID
     * @return 审批详情
     */
    CaseApprovalVO getApprovalDetail(Long approvalId);

    /**
     * 获取案件的所有审批
     *
     * @param caseId 案件ID
     * @return 审批列表
     */
    List<CaseApprovalVO> listCaseApprovals(Long caseId);

    /**
     * 分页查询审批
     *
     * @param caseId 案件ID
     * @param approvalType 审批类型
     * @param approvalStatus 审批状态
     * @param pageNum 页码
     * @param pageSize 每页大小
     * @return 分页结果
     */
    IPage<CaseApprovalVO> pageApprovals(Long caseId, Integer approvalType, Integer approvalStatus, 
                                      Integer pageNum, Integer pageSize);

    /**
     * 审批操作
     *
     * @param approvalId 审批ID
     * @param approved 是否通过
     * @param opinion 审批意见
     * @return 是否成功
     */
    boolean approve(Long approvalId, boolean approved, String opinion);

    /**
     * 转交审批
     *
     * @param approvalId 审批ID
     * @param targetUserId 目标用户ID
     * @param reason 转交原因
     * @return 是否成功
     */
    boolean transferApproval(Long approvalId, Long targetUserId, String reason);

    /**
     * 加签
     *
     * @param approvalId 审批ID
     * @param addedUserId 加签用户ID
     * @param type 加签类型（前加签/后加签）
     * @return 是否成功
     */
    boolean addApprover(Long approvalId, Long addedUserId, Integer type);

    /**
     * 减签
     *
     * @param approvalId 审批ID
     * @param removedUserId 减签用户ID
     * @param reason 减签原因
     * @return 是否成功
     */
    boolean removeApprover(Long approvalId, Long removedUserId, String reason);

    /**
     * 催办审批
     *
     * @param approvalId 审批ID
     * @return 是否成功
     */
    boolean urgeApproval(Long approvalId);

    /**
     * 获取待办审批
     *
     * @param userId 用户ID
     * @return 审批列表
     */
    List<CaseApprovalVO> listPendingApprovals(Long userId);

    /**
     * 获取已办审批
     *
     * @param userId 用户ID
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 审批列表
     */
    List<CaseApprovalVO> listHandledApprovals(Long userId, LocalDateTime startTime, LocalDateTime endTime);

    /**
     * 获取我发起的审批
     *
     * @param userId 用户ID
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 审批列表
     */
    List<CaseApprovalVO> listInitiatedApprovals(Long userId, LocalDateTime startTime, LocalDateTime endTime);

    /**
     * 获取审批流程记录
     *
     * @param approvalId 审批ID
     * @return 流程记录列表
     */
    List<CaseApprovalVO> getApprovalFlowRecords(Long approvalId);

    /**
     * 检查审批是否存在
     *
     * @param approvalId 审批ID
     * @return 是否存在
     */
    boolean checkApprovalExists(Long approvalId);

    /**
     * 统计案件审批数量
     *
     * @param caseId 案件ID
     * @param approvalType 审批类型
     * @param approvalStatus 审批状态
     * @return 数量
     */
    int countApprovals(Long caseId, Integer approvalType, Integer approvalStatus);
} 