package com.lawfirm.contract.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * 合同统计服务接口
 */
public interface ContractStatisticsService {

    /**
     * 统计合同数量
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @param type     合同类型
     * @param status   合同状态
     * @return 合同数量
     */
    Long countContracts(LocalDateTime startTime, LocalDateTime endTime, Integer type, Integer status);

    /**
     * 统计合同金额
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @param type     合同类型
     * @param status   合同状态
     * @return 合同金额
     */
    BigDecimal sumContractAmount(LocalDateTime startTime, LocalDateTime endTime, Integer type, Integer status);

    /**
     * 按类型统计合同数量
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return 各类型合同数量
     */
    Map<Integer, Long> countContractsByType(LocalDateTime startTime, LocalDateTime endTime);

    /**
     * 按状态统计合同数量
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return 各状态合同数量
     */
    Map<Integer, Long> countContractsByStatus(LocalDateTime startTime, LocalDateTime endTime);

    /**
     * 统计律师合同数量
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return 律师及其合同数量
     */
    Map<Long, Long> countContractsByLawyer(LocalDateTime startTime, LocalDateTime endTime);

    /**
     * 统计律师合同金额
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return 律师及其合同金额
     */
    Map<Long, BigDecimal> sumContractAmountByLawyer(LocalDateTime startTime, LocalDateTime endTime);

    /**
     * 统计部门合同数量
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return 部门及其合同数量
     */
    Map<Long, Long> countContractsByDepartment(LocalDateTime startTime, LocalDateTime endTime);

    /**
     * 统计部门合同金额
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return 部门及其合同金额
     */
    Map<Long, BigDecimal> sumContractAmountByDepartment(LocalDateTime startTime, LocalDateTime endTime);

    /**
     * 统计分支机构合同数量
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return 分支机构及其合同数量
     */
    Map<Long, Long> countContractsByBranch(LocalDateTime startTime, LocalDateTime endTime);

    /**
     * 统计分支机构合同金额
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return 分支机构及其合同金额
     */
    Map<Long, BigDecimal> sumContractAmountByBranch(LocalDateTime startTime, LocalDateTime endTime);

    /**
     * 统计每月合同数量
     *
     * @param year  年份
     * @param type  合同类型
     * @return 每月合同数量
     */
    Map<Integer, Long> countContractsByMonth(Integer year, Integer type);

    /**
     * 统计每月合同金额
     *
     * @param year  年份
     * @param type  合同类型
     * @return 每月合同金额
     */
    Map<Integer, BigDecimal> sumContractAmountByMonth(Integer year, Integer type);

    /**
     * 按客户统计合同数量
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return 客户及其合同数量
     */
    Map<Long, Long> countContractsByClient(LocalDateTime startTime, LocalDateTime endTime);

    /**
     * 按客户统计合同金额
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return 客户及其合同金额
     */
    Map<Long, BigDecimal> sumContractAmountByClient(LocalDateTime startTime, LocalDateTime endTime);

    /**
     * 按签订时间统计合同数量
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @param interval  时间间隔(天)
     * @return 时间段及其合同数量
     */
    Map<LocalDateTime, Long> countContractsBySignDate(LocalDateTime startTime, LocalDateTime endTime, Integer interval);

    /**
     * 按签订时间统计合同金额
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @param interval  时间间隔(天)
     * @return 时间段及其合同金额
     */
    Map<LocalDateTime, BigDecimal> sumContractAmountBySignDate(LocalDateTime startTime, LocalDateTime endTime, Integer interval);
} 