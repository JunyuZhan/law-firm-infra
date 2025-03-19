package com.lawfirm.api.adaptor.cases;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lawfirm.api.adaptor.BaseAdaptor;
import com.lawfirm.model.cases.dto.business.CaseFinanceDTO;
import com.lawfirm.model.cases.service.business.CaseFinanceService;
import com.lawfirm.model.cases.vo.business.CaseFinanceVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 案件费用适配器
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class FeeAdaptor extends BaseAdaptor {

    private final CaseFinanceService financeService;

    /**
     * 记录收入
     */
    public Long recordIncome(CaseFinanceDTO financeDTO) {
        log.info("记录收入: {}", financeDTO);
        return financeService.recordIncome(financeDTO);
    }

    /**
     * 记录支出
     */
    public Long recordExpense(CaseFinanceDTO financeDTO) {
        log.info("记录支出: {}", financeDTO);
        return financeService.recordExpense(financeDTO);
    }

    /**
     * 批量记录财务信息
     */
    public boolean batchRecordFinance(List<CaseFinanceDTO> financeDTOs) {
        log.info("批量记录财务信息: {}", financeDTOs);
        return financeService.batchRecordFinance(financeDTOs);
    }

    /**
     * 更新财务记录
     */
    public boolean updateFinance(CaseFinanceDTO financeDTO) {
        log.info("更新财务记录: {}", financeDTO);
        return financeService.updateFinance(financeDTO);
    }

    /**
     * 删除财务记录
     */
    public boolean deleteFinance(Long financeId) {
        log.info("删除财务记录: {}", financeId);
        return financeService.deleteFinance(financeId);
    }

    /**
     * 获取财务记录详情
     */
    public CaseFinanceVO getFinanceDetail(Long financeId) {
        log.info("获取财务记录详情: {}", financeId);
        return financeService.getFinanceDetail(financeId);
    }

    /**
     * 获取案件的所有财务记录
     */
    public List<CaseFinanceVO> listCaseFinances(Long caseId) {
        log.info("获取案件的所有财务记录: caseId={}", caseId);
        return financeService.listCaseFinances(caseId);
    }

    /**
     * 分页查询财务记录
     */
    public IPage<CaseFinanceVO> pageFinances(Long caseId, Integer financeType, LocalDateTime startTime, LocalDateTime endTime, Integer pageNum, Integer pageSize) {
        log.info("分页查询财务记录: caseId={}, financeType={}, startTime={}, endTime={}, pageNum={}, pageSize={}", 
                caseId, financeType, startTime, endTime, pageNum, pageSize);
        return financeService.pageFinances(caseId, financeType, startTime, endTime, pageNum, pageSize);
    }

    /**
     * 生成账单
     */
    public Long generateBill(Long caseId, LocalDateTime startTime, LocalDateTime endTime) {
        log.info("生成账单: caseId={}, startTime={}, endTime={}", caseId, startTime, endTime);
        return financeService.generateBill(caseId, startTime, endTime);
    }

    /**
     * 审核财务记录
     */
    public boolean reviewFinance(Long financeId, boolean approved, String opinion) {
        log.info("审核财务记录: financeId={}, approved={}, opinion={}", financeId, approved, opinion);
        return financeService.reviewFinance(financeId, approved, opinion);
    }

    /**
     * 计算案件总收入
     */
    public BigDecimal calculateTotalIncome(Long caseId) {
        log.info("计算案件总收入: caseId={}", caseId);
        return financeService.calculateTotalIncome(caseId);
    }

    /**
     * 计算案件总支出
     */
    public BigDecimal calculateTotalExpense(Long caseId) {
        log.info("计算案件总支出: caseId={}", caseId);
        return financeService.calculateTotalExpense(caseId);
    }

    /**
     * 计算案件利润
     */
    public BigDecimal calculateProfit(Long caseId) {
        log.info("计算案件利润: caseId={}", caseId);
        return financeService.calculateProfit(caseId);
    }

    /**
     * 设置预算
     */
    public boolean setBudget(Long caseId, BigDecimal budget) {
        log.info("设置预算: caseId={}, budget={}", caseId, budget);
        return financeService.setBudget(caseId, budget);
    }

    /**
     * 检查预算是否超支
     */
    public boolean checkBudgetOverrun(Long caseId) {
        log.info("检查预算是否超支: caseId={}", caseId);
        return financeService.checkBudgetOverrun(caseId);
    }

    /**
     * 导出财务记录
     */
    public String exportFinanceRecords(Long caseId, LocalDateTime startTime, LocalDateTime endTime) {
        log.info("导出财务记录: caseId={}, startTime={}, endTime={}", caseId, startTime, endTime);
        return financeService.exportFinanceRecords(caseId, startTime, endTime);
    }

    /**
     * 统计财务记录数量
     */
    public int countFinances(Long caseId, Integer financeType) {
        log.info("统计财务记录数量: caseId={}, financeType={}", caseId, financeType);
        return financeService.countFinances(caseId, financeType);
    }
} 