package com.lawfirm.model.cases.service.business;

import com.lawfirm.model.base.dto.PageDTO;
import com.lawfirm.model.cases.entity.business.CaseWorkload;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * 案件工作量服务接口
 */
public interface CaseWorkloadService {

    /**
     * 记录工作量
     *
     * @param caseId 案件ID
     * @param lawyerId 律师ID
     * @param workType 工作类型
     * @param hours 工时
     * @param description 工作描述
     * @return 工作量记录ID
     */
    Long recordWorkload(Long caseId, Long lawyerId, String workType, BigDecimal hours, String description);

    /**
     * 批量记录工作量
     *
     * @param workloads 工作量记录列表
     * @return 是否成功
     */
    Boolean batchRecordWorkload(List<CaseWorkload> workloads);

    /**
     * 更新工作量记录
     *
     * @param workloadId 工作量记录ID
     * @param hours 工时
     * @param description 工作描述
     * @return 是否成功
     */
    Boolean updateWorkload(Long workloadId, BigDecimal hours, String description);

    /**
     * 删除工作量记录
     *
     * @param workloadId 工作量记录ID
     * @return 是否成功
     */
    Boolean deleteWorkload(Long workloadId);

    /**
     * 获取案件工作量统计
     *
     * @param caseId 案件ID
     * @return 工作量统计（按律师分组）
     */
    Map<Long, BigDecimal> getCaseWorkloadStats(Long caseId);

    /**
     * 获取律师工作量统计
     *
     * @param lawyerId 律师ID
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 工作量统计（按案件分组）
     */
    Map<Long, BigDecimal> getLawyerWorkloadStats(Long lawyerId, LocalDateTime startTime, LocalDateTime endTime);

    /**
     * 分页查询工作量记录
     *
     * @param caseId 案件ID
     * @param lawyerId 律师ID
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @param pageNum 页码
     * @param pageSize 每页大小
     * @return 分页结果
     */
    PageDTO<CaseWorkload> pageWorkloads(Long caseId, Long lawyerId, 
            LocalDateTime startTime, LocalDateTime endTime, Integer pageNum, Integer pageSize);

    /**
     * 获取部门工作量统计
     *
     * @param departmentId 部门ID
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 工作量统计（按律师分组）
     */
    Map<Long, BigDecimal> getDepartmentWorkloadStats(Long departmentId, LocalDateTime startTime, LocalDateTime endTime);

    /**
     * 导出工作量报表
     *
     * @param caseId 案件ID
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 文件路径
     */
    String exportWorkloadReport(Long caseId, LocalDateTime startTime, LocalDateTime endTime);

    /**
     * 审核工作量记录
     *
     * @param workloadId 工作量记录ID
     * @param approved 是否通过
     * @param comment 审核意见
     * @return 是否成功
     */
    Boolean reviewWorkload(Long workloadId, Boolean approved, String comment);

    /**
     * 计算工作量费用
     *
     * @param workloadId 工作量记录ID
     * @param hourlyRate 小时费率
     * @return 费用金额
     */
    BigDecimal calculateWorkloadFee(Long workloadId, BigDecimal hourlyRate);
} 