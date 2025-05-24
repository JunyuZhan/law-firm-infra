package com.lawfirm.model.cases.mapper.business;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lawfirm.model.cases.entity.business.CaseFee;
import com.lawfirm.model.cases.constants.CaseSqlConstants;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 案件费用Mapper接口
 */
@Mapper
public interface CaseFeeMapper extends BaseMapper<CaseFee> {
    
    /**
     * 根据案件ID查询费用列表
     *
     * @param caseId 案件ID
     * @return 费用列表
     */
    @Select("SELECT * FROM case_fee WHERE case_id = #{caseId} AND deleted = 0")
    List<CaseFee> selectByCaseId(@Param("caseId") Long caseId);
    
    /**
     * 根据费用类型查询
     *
     * @param caseId 案件ID
     * @param feeType 费用类型
     * @return 费用列表
     */
    @Select("SELECT * FROM case_fee WHERE case_id = #{caseId} AND fee_type = #{feeType} AND deleted = 0")
    List<CaseFee> selectByType(@Param("caseId") Long caseId, @Param("feeType") Integer feeType);
    
    /**
     * 根据支付状态查询
     *
     * @param caseId 案件ID
     * @param paymentStatus 支付状态
     * @return 费用列表
     */
    @Select("SELECT * FROM case_fee WHERE case_id = #{caseId} AND payment_status = #{paymentStatus} AND deleted = 0")
    List<CaseFee> selectByPaymentStatus(@Param("caseId") Long caseId, @Param("paymentStatus") Integer paymentStatus);
    
    /**
     * 统计案件费用总额
     *
     * @param caseId 案件ID
     * @return 费用总额
     */
    @Select(CaseSqlConstants.Fee.SUM_AMOUNT_BY_CASE_ID)
    BigDecimal sumAmountByCaseId(@Param("caseId") Long caseId);

    /**
     * 统计所有案件总收入（fee_amount）
     */
    @Select("SELECT SUM(fee_amount) FROM case_fee WHERE deleted = 0")
    BigDecimal sumAllFeeAmount();

    /**
     * 统计所有案件总已回款（paid_amount）
     */
    @Select("SELECT SUM(paid_amount) FROM case_fee WHERE deleted = 0")
    BigDecimal sumAllPaidAmount();

    /**
     * 统计所有案件总未回款（fee_amount - paid_amount）
     */
    @Select("SELECT SUM(fee_amount - IFNULL(paid_amount,0)) FROM case_fee WHERE deleted = 0")
    BigDecimal sumAllUnpaidAmount();

    /**
     * 按月统计收入（fee_amount）
     */
    @Select({
        "<script>",
        "SELECT DATE_FORMAT(payment_time, '%Y-%m') AS month, SUM(fee_amount) AS amount",
        "FROM case_fee",
        "WHERE deleted = 0",
        "  <if test='start != null'>AND payment_time &gt;= #{start}</if>",
        "  <if test='end != null'>AND payment_time &lt;= #{end}</if>",
        "GROUP BY month",
        "ORDER BY month ASC",
        "</script>"
    })
    List<Map<String, Object>> sumFeeAmountByMonth(@Param("start") java.util.Date start, @Param("end") java.util.Date end);

    /**
     * 按月统计已回款（paid_amount）
     */
    @Select({
        "<script>",
        "SELECT DATE_FORMAT(payment_time, '%Y-%m') AS month, SUM(paid_amount) AS amount",
        "FROM case_fee",
        "WHERE deleted = 0",
        "  <if test='start != null'>AND payment_time &gt;= #{start}</if>",
        "  <if test='end != null'>AND payment_time &lt;= #{end}</if>",
        "GROUP BY month",
        "ORDER BY month ASC",
        "</script>"
    })
    List<Map<String, Object>> sumPaidAmountByMonth(@Param("start") java.util.Date start, @Param("end") java.util.Date end);
} 