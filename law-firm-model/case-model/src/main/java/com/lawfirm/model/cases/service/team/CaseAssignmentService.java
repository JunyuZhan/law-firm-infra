package com.lawfirm.model.cases.service.team;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lawfirm.model.cases.dto.team.CaseAssignmentDTO;
import com.lawfirm.model.cases.vo.team.CaseAssignmentVO;

import java.util.List;

/**
 * 案件分配服务接口
 */
public interface CaseAssignmentService {

    /**
     * 分配案件
     *
     * @param assignmentDTO 分配信息
     * @return 分配ID
     */
    Long assignCase(CaseAssignmentDTO assignmentDTO);

    /**
     * 批量分配案件
     *
     * @param assignmentDTOs 分配信息列表
     * @return 是否成功
     */
    boolean batchAssignCases(List<CaseAssignmentDTO> assignmentDTOs);

    /**
     * 更新分配信息
     *
     * @param assignmentDTO 分配信息
     * @return 是否成功
     */
    boolean updateAssignment(CaseAssignmentDTO assignmentDTO);

    /**
     * 取消分配
     *
     * @param assignmentId 分配ID
     * @return 是否成功
     */
    boolean cancelAssignment(Long assignmentId);

    /**
     * 批量取消分配
     *
     * @param assignmentIds 分配ID列表
     * @return 是否成功
     */
    boolean batchCancelAssignments(List<Long> assignmentIds);

    /**
     * 获取分配详情
     *
     * @param assignmentId 分配ID
     * @return 分配详情
     */
    CaseAssignmentVO getAssignmentDetail(Long assignmentId);

    /**
     * 获取案件的所有分配记录
     *
     * @param caseId 案件ID
     * @return 分配记录列表
     */
    List<CaseAssignmentVO> listCaseAssignments(Long caseId);

    /**
     * 分页查询分配记录
     *
     * @param caseId 案件ID
     * @param assignmentType 分配类型
     * @param pageNum 页码
     * @param pageSize 每页大小
     * @return 分页结果
     */
    IPage<CaseAssignmentVO> pageAssignments(Long caseId, Integer assignmentType, Integer pageNum, Integer pageSize);

    /**
     * 接受分配
     *
     * @param assignmentId 分配ID
     * @return 是否成功
     */
    boolean acceptAssignment(Long assignmentId);

    /**
     * 拒绝分配
     *
     * @param assignmentId 分配ID
     * @param reason 拒绝原因
     * @return 是否成功
     */
    boolean rejectAssignment(Long assignmentId, String reason);

    /**
     * 完成分配
     *
     * @param assignmentId 分配ID
     * @return 是否成功
     */
    boolean completeAssignment(Long assignmentId);

    /**
     * 检查律师是否可以接受分配
     *
     * @param lawyerId 律师ID
     * @param caseId 案件ID
     * @return 是否可以接受
     */
    boolean checkLawyerAvailable(Long lawyerId, Long caseId);

    /**
     * 统计分配记录数量
     *
     * @param caseId 案件ID
     * @param assignmentType 分配类型
     * @return 数量
     */
    int countAssignments(Long caseId, Integer assignmentType);
} 