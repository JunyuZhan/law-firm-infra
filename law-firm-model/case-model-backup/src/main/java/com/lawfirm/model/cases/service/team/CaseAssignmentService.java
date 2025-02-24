package com.lawfirm.model.cases.service.team;

import com.lawfirm.model.base.dto.PageDTO;
import com.lawfirm.model.cases.entity.team.CaseAssignment;
import com.lawfirm.model.cases.entity.team.CaseAssignment.AssignmentStatus;
import com.lawfirm.model.cases.entity.team.CaseAssignment.AssignmentType;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 案件分配服务接口
 */
public interface CaseAssignmentService {

    /**
     * 分配案件
     *
     * @param caseId 案件ID
     * @param fromLawyerId 原律师ID
     * @param toLawyerId 目标律师ID
     * @param type 分配类型
     * @param reason 分配原因
     * @return 分配记录ID
     */
    Long assignCase(Long caseId, Long fromLawyerId, Long toLawyerId, AssignmentType type, String reason);

    /**
     * 批量分配案件
     *
     * @param caseIds 案件ID列表
     * @param toLawyerId 目标律师ID
     * @param type 分配类型
     * @param reason 分配原因
     * @return 是否成功
     */
    Boolean batchAssignCases(List<Long> caseIds, Long toLawyerId, AssignmentType type, String reason);

    /**
     * 接受案件分配
     *
     * @param assignmentId 分配记录ID
     * @param handoverNote 交接说明
     * @return 是否成功
     */
    Boolean acceptAssignment(Long assignmentId, String handoverNote);

    /**
     * 拒绝案件分配
     *
     * @param assignmentId 分配记录ID
     * @param rejectReason 拒绝原因
     * @return 是否成功
     */
    Boolean rejectAssignment(Long assignmentId, String rejectReason);

    /**
     * 取消案件分配
     *
     * @param assignmentId 分配记录ID
     * @param cancelReason 取消原因
     * @return 是否成功
     */
    Boolean cancelAssignment(Long assignmentId, String cancelReason);

    /**
     * 完成案件交接
     *
     * @param assignmentId 分配记录ID
     * @param handoverNote 交接说明
     * @return 是否成功
     */
    Boolean completeHandover(Long assignmentId, String handoverNote);

    /**
     * 获取案件分配记录
     *
     * @param caseId 案件ID
     * @return 分配记录列表
     */
    List<CaseAssignment> listCaseAssignments(Long caseId);

    /**
     * 分页查询分配记录
     *
     * @param caseId 案件ID
     * @param status 状态
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @param pageNum 页码
     * @param pageSize 每页大小
     * @return 分页结果
     */
    PageDTO<CaseAssignment> pageAssignments(Long caseId, AssignmentStatus status, 
            LocalDateTime startTime, LocalDateTime endTime, Integer pageNum, Integer pageSize);

    /**
     * 获取律师的待处理分配记录
     *
     * @param lawyerId 律师ID
     * @return 分配记录列表
     */
    List<CaseAssignment> listPendingAssignments(Long lawyerId);

    /**
     * 检查律师是否有权限接收案件
     *
     * @param lawyerId 律师ID
     * @param caseId 案件ID
     * @return 是否有权限
     */
    Boolean checkAssignmentPermission(Long lawyerId, Long caseId);

    /**
     * 更新分配记录审批状态
     *
     * @param assignmentId 分配记录ID
     * @param approved 是否通过
     * @param approvalComment 审批意见
     * @return 是否成功
     */
    Boolean updateApprovalStatus(Long assignmentId, Boolean approved, String approvalComment);
} 