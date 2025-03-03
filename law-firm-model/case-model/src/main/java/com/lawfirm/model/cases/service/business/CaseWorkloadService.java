package com.lawfirm.model.cases.service.business;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lawfirm.model.cases.dto.business.CaseWorkloadDTO;
import com.lawfirm.model.cases.vo.business.CaseWorkloadVO;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 案件工作量服务接口
 */
public interface CaseWorkloadService {

    /**
     * 记录工作量
     *
     * @param workloadDTO 工作量信息
     * @return 工作量ID
     */
    Long recordWorkload(CaseWorkloadDTO workloadDTO);

    /**
     * 批量记录工作量
     *
     * @param workloadDTOs 工作量信息列表
     * @return 是否成功
     */
    boolean batchRecordWorkloads(List<CaseWorkloadDTO> workloadDTOs);

    /**
     * 更新工作量
     *
     * @param workloadDTO 工作量信息
     * @return 是否成功
     */
    boolean updateWorkload(CaseWorkloadDTO workloadDTO);

    /**
     * 删除工作量
     *
     * @param workloadId 工作量ID
     * @return 是否成功
     */
    boolean deleteWorkload(Long workloadId);

    /**
     * 批量删除工作量
     *
     * @param workloadIds 工作量ID列表
     * @return 是否成功
     */
    boolean batchDeleteWorkloads(List<Long> workloadIds);

    /**
     * 获取工作量详情
     *
     * @param workloadId 工作量ID
     * @return 工作量详情
     */
    CaseWorkloadVO getWorkloadDetail(Long workloadId);

    /**
     * 获取案件的所有工作量
     *
     * @param caseId 案件ID
     * @return 工作量列表
     */
    List<CaseWorkloadVO> listCaseWorkloads(Long caseId);

    /**
     * 分页查询工作量
     *
     * @param caseId 案件ID
     * @param workType 工作类型
     * @param lawyerId 律师ID
     * @param pageNum 页码
     * @param pageSize 每页大小
     * @return 分页结果
     */
    IPage<CaseWorkloadVO> pageWorkloads(Long caseId, Integer workType, Long lawyerId, Integer pageNum, Integer pageSize);

    /**
     * 审核工作量
     *
     * @param workloadId 工作量ID
     * @param approved 是否通过
     * @param opinion 审核意见
     * @return 是否成功
     */
    boolean reviewWorkload(Long workloadId, boolean approved, String opinion);

    /**
     * 计算工作量费用
     *
     * @param workloadId 工作量ID
     * @param hourlyRate 小时费率
     * @return 费用金额
     */
    BigDecimal calculateWorkloadFee(Long workloadId, BigDecimal hourlyRate);

    /**
     * 统计律师工作时长
     *
     * @param lawyerId 律师ID
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 工作时长（小时）
     */
    BigDecimal calculateLawyerWorkHours(Long lawyerId, LocalDateTime startTime, LocalDateTime endTime);

    /**
     * 统计案件工作时长
     *
     * @param caseId 案件ID
     * @return 工作时长（小时）
     */
    BigDecimal calculateCaseWorkHours(Long caseId);

    /**
     * 获取律师工作量统计
     *
     * @param lawyerId 律师ID
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 工作量列表
     */
    List<CaseWorkloadVO> getLawyerWorkloadStats(Long lawyerId, LocalDateTime startTime, LocalDateTime endTime);

    /**
     * 获取案件工作量统计
     *
     * @param caseId 案件ID
     * @param workType 工作类型
     * @return 工作量列表
     */
    List<CaseWorkloadVO> getCaseWorkloadStats(Long caseId, Integer workType);

    /**
     * 导出工作量记录
     *
     * @param caseId 案件ID
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 导出文件路径
     */
    String exportWorkloadRecords(Long caseId, LocalDateTime startTime, LocalDateTime endTime);

    /**
     * 检查工作量是否存在
     *
     * @param workloadId 工作量ID
     * @return 是否存在
     */
    boolean checkWorkloadExists(Long workloadId);

    /**
     * 统计案件工作量数量
     *
     * @param caseId 案件ID
     * @param workType 工作类型
     * @param lawyerId 律师ID
     * @return 数量
     */
    int countWorkloads(Long caseId, Integer workType, Long lawyerId);
} 