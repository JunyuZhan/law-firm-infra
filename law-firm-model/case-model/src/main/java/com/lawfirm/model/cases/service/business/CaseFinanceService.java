package com.lawfirm.model.cases.service.business;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lawfirm.model.cases.dto.business.CaseFinanceDTO;
import com.lawfirm.model.cases.vo.business.CaseFinanceVO;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 案件财务服务接口
 */
public interface CaseFinanceService {

    /**
     * 记录收入
     *
     * @param financeDTO 财务信息
     * @return 记录ID
     */
    Long recordIncome(CaseFinanceDTO financeDTO);

    /**
     * 记录支出
     *
     * @param financeDTO 财务信息
     * @return 记录ID
     */
    Long recordExpense(CaseFinanceDTO financeDTO);

    /**
     * 批量记录财务信息
     *
     * @param financeDTOs 财务信息列表
     * @return 是否成功
     */
    boolean batchRecordFinance(List<CaseFinanceDTO> financeDTOs);

    /**
     * 更新财务记录
     *
     * @param financeDTO 财务信息
     * @return 是否成功
     */
    boolean updateFinance(CaseFinanceDTO financeDTO);

    /**
     * 删除财务记录
     *
     * @param financeId 财务记录ID
     * @return 是否成功
     */
    boolean deleteFinance(Long financeId);

    /**
     * 获取财务记录详情
     *
     * @param financeId 财务记录ID
     * @return 财务记录详情
     */
    CaseFinanceVO getFinanceDetail(Long financeId);

    /**
     * 获取案件的所有财务记录
     *
     * @param caseId 案件ID
     * @return 财务记录列表
     */
    List<CaseFinanceVO> listCaseFinances(Long caseId);

    /**
     * 分页查询财务记录
     *
     * @param caseId 案件ID
     * @param financeType 财务类型
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @param pageNum 页码
     * @param pageSize 每页大小
     * @return 分页结果
     */
    IPage<CaseFinanceVO> pageFinances(Long caseId, Integer financeType, LocalDateTime startTime, 
                                    LocalDateTime endTime, Integer pageNum, Integer pageSize);

    /**
     * 生成账单
     *
     * @param caseId 案件ID
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 账单ID
     */
    Long generateBill(Long caseId, LocalDateTime startTime, LocalDateTime endTime);

    /**
     * 审核财务记录
     *
     * @param financeId 财务记录ID
     * @param approved 是否通过
     * @param opinion 审核意见
     * @return 是否成功
     */
    boolean reviewFinance(Long financeId, boolean approved, String opinion);

    /**
     * 计算案件总收入
     *
     * @param caseId 案件ID
     * @return 总收入金额
     */
    BigDecimal calculateTotalIncome(Long caseId);

    /**
     * 计算案件总支出
     *
     * @param caseId 案件ID
     * @return 总支出金额
     */
    BigDecimal calculateTotalExpense(Long caseId);

    /**
     * 计算案件利润
     *
     * @param caseId 案件ID
     * @return 利润金额
     */
    BigDecimal calculateProfit(Long caseId);

    /**
     * 设置预算
     *
     * @param caseId 案件ID
     * @param budget 预算金额
     * @return 是否成功
     */
    boolean setBudget(Long caseId, BigDecimal budget);

    /**
     * 检查预算是否超支
     *
     * @param caseId 案件ID
     * @return 是否超支
     */
    boolean checkBudgetOverrun(Long caseId);

    /**
     * 导出财务记录
     *
     * @param caseId 案件ID
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 导出文件路径
     */
    String exportFinanceRecords(Long caseId, LocalDateTime startTime, LocalDateTime endTime);

    /**
     * 统计财务记录数量
     *
     * @param caseId 案件ID
     * @param financeType 财务类型
     * @return 数量
     */
    int countFinances(Long caseId, Integer financeType);
} 