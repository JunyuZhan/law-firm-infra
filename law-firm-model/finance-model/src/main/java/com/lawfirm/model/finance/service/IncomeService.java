package com.lawfirm.model.finance.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lawfirm.model.finance.entity.Income;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 收入服务接口
 */
public interface IncomeService {
    
    /**
     * 记录收入
     *
     * @param income 收入信息
     * @return 收入ID
     */
    Long recordIncome(Income income);
    
    /**
     * 更新收入信息
     *
     * @param income 收入信息
     * @return 是否更新成功
     */
    boolean updateIncome(Income income);
    
    /**
     * 删除收入记录
     *
     * @param incomeId 收入ID
     * @return 是否删除成功
     */
    boolean deleteIncome(Long incomeId);
    
    /**
     * 获取收入详情
     *
     * @param incomeId 收入ID
     * @return 收入信息
     */
    Income getIncomeById(Long incomeId);
    
    /**
     * 确认收入
     *
     * @param incomeId 收入ID
     * @param confirmerId 确认人ID
     * @param remark 确认备注
     * @return 是否确认成功
     */
    boolean confirmIncome(Long incomeId, Long confirmerId, String remark);
    
    /**
     * 取消收入
     *
     * @param incomeId 收入ID
     * @param reason 取消原因
     * @return 是否取消成功
     */
    boolean cancelIncome(Long incomeId, String reason);
    
    /**
     * 查询收入列表
     *
     * @param incomeType 收入类型，可为null
     * @param contractId 合同ID，可为null
     * @param startTime 开始时间，可为null
     * @param endTime 结束时间，可为null
     * @return 收入列表
     */
    List<Income> listIncomes(Integer incomeType, Long contractId, LocalDateTime startTime, LocalDateTime endTime);
    
    /**
     * 分页查询收入
     *
     * @param page 分页参数
     * @param incomeType 收入类型，可为null
     * @param contractId 合同ID，可为null
     * @param startTime 开始时间，可为null
     * @param endTime 结束时间，可为null
     * @return 分页收入信息
     */
    IPage<Income> pageIncomes(IPage<Income> page, Integer incomeType, Long contractId, 
                             LocalDateTime startTime, LocalDateTime endTime);
    
    /**
     * 按合同查询收入
     *
     * @param contractId 合同ID
     * @return 收入列表
     */
    List<Income> listIncomesByContract(Long contractId);
    
    /**
     * 按客户查询收入
     *
     * @param clientId 客户ID
     * @param startTime 开始时间，可为null
     * @param endTime 结束时间，可为null
     * @return 收入列表
     */
    List<Income> listIncomesByClient(Long clientId, LocalDateTime startTime, LocalDateTime endTime);
    
    /**
     * 统计收入总额
     *
     * @param incomeType 收入类型，可为null
     * @param startTime 开始时间，可为null
     * @param endTime 结束时间，可为null
     * @return 收入总额
     */
    BigDecimal sumIncomeAmount(Integer incomeType, LocalDateTime startTime, LocalDateTime endTime);
    
    /**
     * 统计合同收入总额
     *
     * @param contractId 合同ID
     * @return 收入总额
     */
    BigDecimal sumContractIncomeAmount(Long contractId);
    
    /**
     * 按月统计收入
     *
     * @param year 年份
     * @param month 月份，可为null表示全年
     * @return 收入统计列表
     */
    List<IncomeStat> statisticIncomeByMonth(Integer year, Integer month);
    
    /**
     * 按合同统计收入
     *
     * @return 合同收入统计列表
     */
    List<ContractIncomeStat> statisticIncomeByContract();
    
    /**
     * 收入统计数据
     */
    interface IncomeStat {
        /**
         * 获取统计维度（如月份、收入类型等）
         */
        String getDimension();
        
        /**
         * 获取收入金额
         */
        BigDecimal getAmount();
        
        /**
         * 获取收入笔数
         */
        Integer getCount();
    }
} 