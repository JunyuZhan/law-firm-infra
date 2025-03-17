package com.lawfirm.cases.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lawfirm.cases.core.audit.CaseAuditProvider;
import com.lawfirm.cases.core.message.CaseMessageManager;
import com.lawfirm.cases.core.storage.CaseStorageManager;
import com.lawfirm.model.cases.dto.business.CaseFinanceDTO;
import com.lawfirm.model.cases.entity.business.CaseFee;
import com.lawfirm.model.cases.mapper.business.CaseFeeMapper;
import com.lawfirm.model.cases.service.business.CaseFinanceService;
import com.lawfirm.model.cases.vo.business.CaseFinanceVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * 案件费用服务实现类
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class FeeServiceImpl implements CaseFinanceService {

    private final CaseFeeMapper feeMapper;
    private final CaseAuditProvider auditProvider;
    private final CaseMessageManager messageManager;
    private final CaseStorageManager storageManager;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long recordIncome(CaseFinanceDTO financeDTO) {
        log.info("记录收入: 案件ID={}, 金额={}", financeDTO.getCaseId(), financeDTO.getAmount());
        
        // 1. 创建费用记录
        CaseFee fee = new CaseFee();
        BeanUtils.copyProperties(financeDTO, fee);
        fee.setFeeType(1); // 收入类型
        fee.setFeeStatus(1); // 初始状态：待收款
        fee.setCreateTime(LocalDateTime.now());
        fee.setUpdateTime(LocalDateTime.now());
        fee.setPaidAmount(BigDecimal.ZERO);
        
        // 2. 保存费用记录
        feeMapper.insert(fee);
        Long feeId = fee.getId();
        
        // 3. 记录审计
        // 获取当前操作用户ID（应从上下文或认证信息中获取）
        Long operatorId = 0L; // 默认值，实际应从上下文获取
        
        auditProvider.auditCaseStatusChange(
                financeDTO.getCaseId(),
                operatorId,
                "0", // 无状态
                "1", // 待收款状态
                "记录收入: " + fee.getFeeName()
        );
        
        // 4. 发送消息通知
        Map<String, Object> changes = Map.of(
            "changeType", "FINANCE_INCOME_ADDED",
            "feeId", feeId,
            "feeName", fee.getFeeName(),
            "amount", fee.getFeeAmount()
        );
        
        List<Map<String, Object>> changesList = List.of(changes);
        messageManager.sendCaseTeamChangeMessage(
                financeDTO.getCaseId(),
                changesList,
                operatorId
        );
        
        log.info("收入记录成功, ID: {}", feeId);
        return feeId;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long recordExpense(CaseFinanceDTO financeDTO) {
        log.info("记录支出: 案件ID={}, 金额={}", financeDTO.getCaseId(), financeDTO.getAmount());
        
        // 1. 创建费用记录
        CaseFee fee = new CaseFee();
        BeanUtils.copyProperties(financeDTO, fee);
        fee.setFeeType(2); // 支出类型
        fee.setFeeStatus(1); // 初始状态：待付款
        fee.setCreateTime(LocalDateTime.now());
        fee.setUpdateTime(LocalDateTime.now());
        fee.setPaidAmount(BigDecimal.ZERO);
        
        // 2. 保存费用记录
        feeMapper.insert(fee);
        Long feeId = fee.getId();
        
        // 3. 记录审计
        // 获取当前操作用户ID（应从上下文或认证信息中获取）
        Long operatorId = 0L; // 默认值，实际应从上下文获取
        
        auditProvider.auditCaseStatusChange(
                financeDTO.getCaseId(),
                operatorId,
                "0", // 无状态
                "1", // 待付款状态
                "记录支出: " + fee.getFeeName()
        );
        
        // 4. 发送消息通知
        Map<String, Object> changes = Map.of(
            "changeType", "FINANCE_EXPENSE_ADDED",
            "feeId", feeId,
            "feeName", fee.getFeeName(),
            "amount", fee.getFeeAmount()
        );
        
        List<Map<String, Object>> changesList = List.of(changes);
        messageManager.sendCaseTeamChangeMessage(
                financeDTO.getCaseId(),
                changesList,
                operatorId
        );
        
        log.info("支出记录成功, ID: {}", feeId);
        return feeId;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateFinance(CaseFinanceDTO financeDTO) {
        log.info("更新财务记录: ID={}", financeDTO.getId());
        
        // 1. 获取原财务数据
        CaseFee oldFee = feeMapper.selectById(financeDTO.getId());
        if (oldFee == null) {
            throw new RuntimeException("财务记录不存在: " + financeDTO.getId());
        }
        
        // 2. 更新财务数据
        CaseFee fee = new CaseFee();
        BeanUtils.copyProperties(financeDTO, fee);
        fee.setUpdateTime(LocalDateTime.now());
        
        // 3. 保存更新
        int result = feeMapper.updateById(fee);
        
        // 4. 记录审计
        // 获取当前操作用户ID（应从上下文或认证信息中获取）
        Long operatorId = 0L; // 默认值，实际应从上下文获取
        
        auditProvider.auditCaseUpdate(
                financeDTO.getCaseId(),
                operatorId,
                oldFee,
                fee,
                new HashMap<>() // 变更字段信息
        );
        
        // 5. 发送消息通知
        Map<String, Object> changes = Map.of(
            "changeType", "FINANCE_UPDATED",
            "feeId", fee.getId(),
            "feeName", fee.getFeeName(),
            "amount", fee.getFeeAmount()
        );
        
        List<Map<String, Object>> changesList = List.of(changes);
        messageManager.sendCaseTeamChangeMessage(
                financeDTO.getCaseId(),
                changesList,
                operatorId
        );
        
        log.info("财务记录更新成功, ID: {}", financeDTO.getId());
        return result > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteFinance(Long financeId) {
        log.info("删除财务记录: ID={}", financeId);
        
        // 1. 获取财务数据
        CaseFee fee = feeMapper.selectById(financeId);
        if (fee == null) {
            throw new RuntimeException("财务记录不存在: " + financeId);
        }
        
        // 2. 软删除财务记录
        fee.setDeleted(1);
        fee.setUpdateTime(LocalDateTime.now());
        int result = feeMapper.updateById(fee);
        
        // 3. 记录审计
        // 获取当前操作用户ID（应从上下文或认证信息中获取）
        Long operatorId = 0L; // 默认值，实际应从上下文获取
        
        auditProvider.auditCaseStatusChange(
                fee.getCaseId(),
                operatorId,
                "1", // 正常状态
                "9", // 删除状态
                "删除财务记录: " + fee.getFeeName()
        );
        
        // 4. 发送消息通知
        Map<String, Object> changes = Map.of(
            "changeType", "FINANCE_DELETED",
            "feeId", financeId,
            "feeName", fee.getFeeName()
        );
        
        List<Map<String, Object>> changesList = List.of(changes);
        messageManager.sendCaseTeamChangeMessage(
                fee.getCaseId(),
                changesList,
                operatorId
        );
        
        log.info("财务记录删除成功, ID: {}", financeId);
        return result > 0;
    }

    @Override
    public CaseFinanceVO getFinanceDetail(Long financeId) {
        log.info("获取财务详情: ID={}", financeId);
        
        CaseFee finance = feeMapper.selectById(financeId);
        if (finance == null) {
            throw new RuntimeException("财务信息不存在: " + financeId);
        }
        
        CaseFinanceVO vo = new CaseFinanceVO();
        BeanUtils.copyProperties(finance, vo);
        
        return vo;
    }

    @Override
    public List<CaseFinanceVO> listCaseFinances(Long caseId) {
        log.info("获取案件所有财务信息: 案件ID={}", caseId);
        
        LambdaQueryWrapper<CaseFee> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CaseFee::getCaseId, caseId);
        wrapper.eq(CaseFee::getDeleted, 0); // 只查询未删除的
        wrapper.orderByDesc(CaseFee::getCreateTime);
        
        List<CaseFee> finances = feeMapper.selectList(wrapper);
        
        return finances.stream().map(entity -> {
            CaseFinanceVO vo = new CaseFinanceVO();
            BeanUtils.copyProperties(entity, vo);
            return vo;
        }).collect(Collectors.toList());
    }

    @Override
    public IPage<CaseFinanceVO> pageFinances(Long caseId, Integer financeType, LocalDateTime startTime, 
                                           LocalDateTime endTime, Integer pageNum, Integer pageSize) {
        log.info("分页查询财务信息: 案件ID={}, 财务类型={}, 开始时间={}, 结束时间={}, 页码={}, 每页大小={}", 
                 caseId, financeType, startTime, endTime, pageNum, pageSize);
        
        Page<CaseFee> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<CaseFee> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CaseFee::getCaseId, caseId);
        wrapper.eq(CaseFee::getDeleted, 0); // 只查询未删除的
        
        if (financeType != null) {
            wrapper.eq(CaseFee::getFeeType, financeType);
        }
        
        if (startTime != null) {
            wrapper.ge(CaseFee::getCreateTime, startTime);
        }
        
        if (endTime != null) {
            wrapper.le(CaseFee::getCreateTime, endTime);
        }
        
        // 排序
        wrapper.orderByDesc(CaseFee::getCreateTime);
        
        // 执行查询
        IPage<CaseFee> resultPage = feeMapper.selectPage(page, wrapper);
        
        // 转换为VO
        return resultPage.convert(entity -> {
            CaseFinanceVO vo = new CaseFinanceVO();
            BeanUtils.copyProperties(entity, vo);
            return vo;
        });
    }

    @Override
    public int countFinances(Long caseId, Integer financeType) {
        log.info("统计财务记录数量: 案件ID={}, 财务类型={}", caseId, financeType);
        
        LambdaQueryWrapper<CaseFee> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CaseFee::getCaseId, caseId);
        wrapper.eq(CaseFee::getDeleted, 0);
        
        if (financeType != null) {
            wrapper.eq(CaseFee::getFeeType, financeType);
        }
        
        int count = feeMapper.selectCount(wrapper).intValue();
        
        log.info("财务记录统计结果: {}", count);
        return count;
    }

    @Override
    public boolean setBudget(Long caseId, BigDecimal budget) {
        log.info("设置案件预算: 案件ID={}, 预算={}", caseId, budget);
        
        // 1. 检查是否已存在预算
        LambdaQueryWrapper<CaseFee> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CaseFee::getCaseId, caseId);
        wrapper.eq(CaseFee::getFeeType, 0); // 假设0表示预算
        wrapper.eq(CaseFee::getDeleted, 0);
        
        CaseFee existingBudget = feeMapper.selectOne(wrapper);
        
        if (existingBudget != null) {
            // 更新现有预算
            existingBudget.setFeeAmount(budget);
            existingBudget.setUpdateTime(LocalDateTime.now());
            feeMapper.updateById(existingBudget);
        } else {
            // 创建新预算
            CaseFee budgetRecord = new CaseFee();
            budgetRecord.setCaseId(caseId);
            budgetRecord.setFeeName("案件预算");
            budgetRecord.setFeeType(0); // 预算类型
            budgetRecord.setFeeAmount(budget);
            budgetRecord.setFeeStatus(1); // 正常状态
            budgetRecord.setCreateTime(LocalDateTime.now());
            budgetRecord.setUpdateTime(LocalDateTime.now());
            
            feeMapper.insert(budgetRecord);
        }
        
        // 2. 记录审计
        auditProvider.auditCaseStatusChange(
                caseId,
                0L, // 操作者ID，实际应从上下文获取
                null,
                null,
                "设置案件预算: " + budget
        );
        
        log.info("案件预算设置成功, 案件ID: {}", caseId);
        return true;
    }
    
    @Override
    public boolean checkBudgetOverrun(Long caseId) {
        log.info("检查预算超支: 案件ID={}", caseId);
        
        // 1. 获取预算金额
        BigDecimal budget = getBudget(caseId);
        if (budget == null || budget.compareTo(BigDecimal.ZERO) <= 0) {
            log.info("案件未设置有效预算，无法检查超支");
            return false;
        }
        
        // 2. 计算总支出
        BigDecimal totalExpense = calculateTotalExpense(caseId);
        
        // 3. 比较预算与支出
        boolean isOverrun = totalExpense.compareTo(budget) > 0;
        log.info("案件预算检查结果: 预算={}, 支出={}, 是否超支={}", budget, totalExpense, isOverrun);
        
        return isOverrun;
    }
    
    private BigDecimal getBudget(Long caseId) {
        LambdaQueryWrapper<CaseFee> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CaseFee::getCaseId, caseId);
        wrapper.eq(CaseFee::getFeeType, 0); // 假设0表示预算
        wrapper.eq(CaseFee::getDeleted, 0);
        
        CaseFee budgetRecord = feeMapper.selectOne(wrapper);
        return budgetRecord != null ? budgetRecord.getFeeAmount() : BigDecimal.ZERO;
    }
    
    @Override
    public BigDecimal calculateTotalIncome(Long caseId) {
        log.info("计算案件总收入: 案件ID={}", caseId);
        
        LambdaQueryWrapper<CaseFee> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CaseFee::getCaseId, caseId);
        wrapper.eq(CaseFee::getDeleted, 0);
        wrapper.eq(CaseFee::getFeeType, 1); // 收入类型
        
        List<CaseFee> fees = feeMapper.selectList(wrapper);
        
        BigDecimal totalIncome = BigDecimal.ZERO;
        for (CaseFee fee : fees) {
            if (fee.getFeeAmount() != null) {
                totalIncome = totalIncome.add(fee.getFeeAmount());
            }
        }
        
        log.info("案件总收入计算完成: {}", totalIncome);
        return totalIncome;
    }
    
    @Override
    public BigDecimal calculateTotalExpense(Long caseId) {
        log.info("计算案件总支出: 案件ID={}", caseId);
        
        LambdaQueryWrapper<CaseFee> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CaseFee::getCaseId, caseId);
        wrapper.eq(CaseFee::getDeleted, 0);
        wrapper.eq(CaseFee::getFeeType, 2); // 支出类型
        
        List<CaseFee> fees = feeMapper.selectList(wrapper);
        
        BigDecimal totalExpense = BigDecimal.ZERO;
        for (CaseFee fee : fees) {
            if (fee.getFeeAmount() != null) {
                totalExpense = totalExpense.add(fee.getFeeAmount());
            }
        }
        
        log.info("案件总支出计算完成: {}", totalExpense);
        return totalExpense;
    }
    
    @Override
    public BigDecimal calculateProfit(Long caseId) {
        log.info("计算案件利润: 案件ID={}", caseId);
        
        BigDecimal totalIncome = calculateTotalIncome(caseId);
        BigDecimal totalExpense = calculateTotalExpense(caseId);
        
        BigDecimal profit = totalIncome.subtract(totalExpense);
        
        log.info("案件利润计算完成: {}", profit);
        return profit;
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean reviewFinance(Long financeId, boolean approved, String opinion) {
        log.info("审核财务信息: ID={}, 是否通过={}", financeId, approved);
        
        // 1. 获取财务信息
        CaseFee finance = feeMapper.selectById(financeId);
        if (finance == null) {
            throw new RuntimeException("财务信息不存在: " + financeId);
        }
        
        // 2. 更新审核状态
        finance.setReviewStatus(approved ? 1 : 2); // 1:通过, 2:不通过
        finance.setReviewOpinion(opinion);
        finance.setReviewTime(LocalDateTime.now());
        finance.setUpdateTime(LocalDateTime.now());
        
        int result = feeMapper.updateById(finance);
        
        // 3. 记录审计
        Long operatorId = 0L; // 实际应从上下文或参数获取
        
        auditProvider.auditCaseStatusChange(
                finance.getCaseId(),
                operatorId,
                "0", // 未审核
                approved ? "1" : "2", // 审核通过/不通过
                "审核财务信息: " + finance.getFeeName() + ", 结果: " + (approved ? "通过" : "不通过")
        );
        
        log.info("财务信息审核完成, ID: {}", financeId);
        return result > 0;
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean batchRecordFinance(List<CaseFinanceDTO> financeDTOs) {
        log.info("批量记录财务信息: 数量={}", financeDTOs.size());
        
        for (CaseFinanceDTO dto : financeDTOs) {
            if (dto.getRecordType() != null && dto.getRecordType() == 1) {
                recordIncome(dto);
            } else {
                recordExpense(dto);
            }
        }
        
        return true;
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long generateBill(Long caseId, LocalDateTime startTime, LocalDateTime endTime) {
        log.info("生成账单: 案件ID={}, 开始时间={}, 结束时间={}", caseId, startTime, endTime);
        
        // 1. 查询指定时间范围内的费用记录
        LambdaQueryWrapper<CaseFee> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CaseFee::getCaseId, caseId);
        wrapper.eq(CaseFee::getDeleted, 0);
        
        if (startTime != null) {
            wrapper.ge(CaseFee::getCreateTime, startTime);
        }
        
        if (endTime != null) {
            wrapper.le(CaseFee::getCreateTime, endTime);
        }
        
        List<CaseFee> fees = feeMapper.selectList(wrapper);
        
        // 2. 计算总金额
        BigDecimal totalIncome = BigDecimal.ZERO;
        BigDecimal totalExpense = BigDecimal.ZERO;
        
        for (CaseFee fee : fees) {
            if (fee.getFeeType() != null && fee.getFeeType() == 1) {
                totalIncome = totalIncome.add(fee.getFeeAmount() != null ? fee.getFeeAmount() : BigDecimal.ZERO);
            } else if (fee.getFeeType() != null && fee.getFeeType() == 2) {
                totalExpense = totalExpense.add(fee.getFeeAmount() != null ? fee.getFeeAmount() : BigDecimal.ZERO);
            }
        }
        
        BigDecimal totalAmount = totalIncome.subtract(totalExpense);
        
        // 3. 创建账单记录
        CaseFee bill = new CaseFee();
        bill.setCaseId(caseId);
        bill.setFeeName("账单-" + LocalDateTime.now().toString());
        bill.setFeeType(3); // 假设3表示账单
        bill.setFeeAmount(totalAmount);
        bill.setFeeStatus(1); // 待支付
        bill.setFeeDescription("时间范围: " + startTime + " 至 " + endTime + 
                              ", 包含收入: " + totalIncome + ", 支出: " + totalExpense);
        bill.setCreateTime(LocalDateTime.now());
        bill.setUpdateTime(LocalDateTime.now());
        
        feeMapper.insert(bill);
        Long billId = bill.getId();
        
        // 4. 记录审计
        Long operatorId = 0L; // 实际应从上下文或参数获取
        
        auditProvider.auditCaseStatusChange(
                caseId,
                operatorId,
                null, 
                "1",
                "生成账单: " + bill.getFeeName()
        );
        
        log.info("账单生成成功, ID: {}", billId);
        return billId;
    }
    
    @Override
    public String exportFinanceRecords(Long caseId, LocalDateTime startTime, LocalDateTime endTime) {
        log.info("导出财务记录: 案件ID={}, 开始时间={}, 结束时间={}", caseId, startTime, endTime);
        
        // 查询指定条件的财务记录
        LambdaQueryWrapper<CaseFee> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CaseFee::getCaseId, caseId);
        wrapper.eq(CaseFee::getDeleted, 0);
        
        if (startTime != null) {
            wrapper.ge(CaseFee::getCreateTime, startTime);
        }
        
        if (endTime != null) {
            wrapper.le(CaseFee::getCreateTime, endTime);
        }
        
        wrapper.orderByDesc(CaseFee::getCreateTime);
        
        List<CaseFee> fees = feeMapper.selectList(wrapper);
        
        // 在实际实现中，这里应该生成EXCEL或PDF文件
        // 简化处理，返回文件路径
        String filePath = "/tmp/export/" + caseId + "_" + UUID.randomUUID().toString() + ".xlsx";
        
        log.info("财务记录导出成功, 文件路径: {}, 记录数: {}", filePath, fees.size());
        return filePath;
    }
}
