package com.lawfirm.model.finance.vo.fee;

import com.lawfirm.model.base.vo.BaseVO;
import com.lawfirm.model.finance.enums.FeeTypeEnum;
import com.lawfirm.model.finance.enums.CurrencyEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 费用列表VO
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class FeeListVO extends BaseVO {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    private Long id;

    /**
     * 费用编号
     */
    private String feeNumber;

    /**
     * 费用类型
     */
    private FeeTypeEnum feeType;

    /**
     * 费用名称
     */
    private String feeName;

    /**
     * 费用金额
     */
    private BigDecimal amount;

    /**
     * 币种
     */
    private CurrencyEnum currency;

    /**
     * 费用发生时间
     */
    private LocalDateTime feeTime;

    /**
     * 关联案件编号
     */
    private String caseNumber;

    /**
     * 关联客户名称
     */
    private String clientName;

    /**
     * 关联律师名称
     */
    private String lawyerName;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 创建人
     */
    private String createBy;
} 