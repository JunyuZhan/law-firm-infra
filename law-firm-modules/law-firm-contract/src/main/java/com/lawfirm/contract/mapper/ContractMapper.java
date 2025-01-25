package com.lawfirm.contract.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lawfirm.contract.entity.Contract;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * 合同数据访问层
 */
public interface ContractMapper extends BaseMapper<Contract> {

    /**
     * 按客户统计合同数量
     */
    @Select("SELECT client_id, COUNT(*) as count FROM contract " +
            "WHERE create_time BETWEEN #{startTime} AND #{endTime} " +
            "AND deleted = 0 " +
            "GROUP BY client_id")
    List<Map<String, Object>> countContractsByClient(@Param("startTime") LocalDateTime startTime,
                                                   @Param("endTime") LocalDateTime endTime);

    /**
     * 按客户统计合同金额
     */
    @Select("SELECT client_id, SUM(amount) as total_amount FROM contract " +
            "WHERE create_time BETWEEN #{startTime} AND #{endTime} " +
            "AND deleted = 0 " +
            "GROUP BY client_id")
    List<Map<String, Object>> sumContractAmountByClient(@Param("startTime") LocalDateTime startTime,
                                                       @Param("endTime") LocalDateTime endTime);

    /**
     * 按签订时间统计合同数量
     */
    @Select("SELECT DATE(sign_date) as date, COUNT(*) as count FROM contract " +
            "WHERE sign_date BETWEEN #{startTime} AND #{endTime} " +
            "AND deleted = 0 " +
            "GROUP BY DATE(sign_date)")
    List<Map<String, Object>> countContractsBySignDate(@Param("startTime") LocalDateTime startTime,
                                                      @Param("endTime") LocalDateTime endTime);

    /**
     * 按签订时间统计合同金额
     */
    @Select("SELECT DATE(sign_date) as date, SUM(amount) as total_amount FROM contract " +
            "WHERE sign_date BETWEEN #{startTime} AND #{endTime} " +
            "AND deleted = 0 " +
            "GROUP BY DATE(sign_date)")
    List<Map<String, Object>> sumContractAmountBySignDate(@Param("startTime") LocalDateTime startTime,
                                                         @Param("endTime") LocalDateTime endTime);

    /**
     * 按部门统计合同数量和金额
     */
    @Select("SELECT department_id, COUNT(*) as count, SUM(amount) as total_amount FROM contract " +
            "WHERE create_time BETWEEN #{startTime} AND #{endTime} " +
            "AND deleted = 0 " +
            "GROUP BY department_id")
    List<Map<String, Object>> statContractsByDepartment(@Param("startTime") LocalDateTime startTime,
                                                       @Param("endTime") LocalDateTime endTime);

    /**
     * 按分支机构统计合同数量和金额
     */
    @Select("SELECT branch_id, COUNT(*) as count, SUM(amount) as total_amount FROM contract " +
            "WHERE create_time BETWEEN #{startTime} AND #{endTime} " +
            "AND deleted = 0 " +
            "GROUP BY branch_id")
    List<Map<String, Object>> statContractsByBranch(@Param("startTime") LocalDateTime startTime,
                                                   @Param("endTime") LocalDateTime endTime);
} 