package com.lawfirm.model.cases.enums;

import com.lawfirm.common.core.enums.BaseEnum;
import lombok.Getter;

/**
 * 案件收费类型枚举
 */
@Getter
public enum CaseFeeTypeEnum implements BaseEnum<String> {
    
    FIXED_FEE("固定收费", FeeGroup.FIXED),
    HOURLY_RATE("计时收费", FeeGroup.TIME_BASED),
    CONTINGENCY_FEE("风险代理", FeeGroup.RESULT_BASED),
    STAGE_FEE("分阶段收费", FeeGroup.STAGE_BASED),
    PERCENTAGE_FEE("比例收费", FeeGroup.RESULT_BASED),
    RETAINER_FEE("聘请费", FeeGroup.FIXED),
    CONSULTATION_FEE("咨询费", FeeGroup.FIXED),
    MIXED_FEE("混合收费", FeeGroup.MIXED),
    FREE("免费", FeeGroup.FREE),
    OTHER("其他收费", FeeGroup.OTHER);

    private final String description;
    private final FeeGroup feeGroup;

    CaseFeeTypeEnum(String description, FeeGroup feeGroup) {
        this.description = description;
        this.feeGroup = feeGroup;
    }

    @Override
    public String getValue() {
        return this.name();
    }

    @Override
    public String getDescription() {
        return this.description;
    }
    
    /**
     * 获取收费类型分组
     * @return 收费类型分组
     */
    public FeeGroup getFeeGroup() {
        return this.feeGroup;
    }
    
    /**
     * 判断是否为固定收费
     * @return 是否固定收费
     */
    public boolean isFixed() {
        return this.feeGroup == FeeGroup.FIXED;
    }
    
    /**
     * 判断是否为计时收费
     * @return 是否计时收费
     */
    public boolean isHourly() {
        return this == HOURLY_RATE;
    }
    
    /**
     * 判断是否为风险代理
     * @return 是否风险代理
     */
    public boolean isContingency() {
        return this == CONTINGENCY_FEE;
    }
    
    /**
     * 判断是否为分阶段收费
     * @return 是否分阶段收费
     */
    public boolean isStage() {
        return this == STAGE_FEE;
    }
    
    /**
     * 判断是否为比例收费
     * @return 是否比例收费
     */
    public boolean isPercentage() {
        return this == PERCENTAGE_FEE;
    }
    
    /**
     * 判断是否为免费
     * @return 是否免费
     */
    public boolean isFree() {
        return this == FREE;
    }

    /**
     * 判断是否为结果导向的收费类型
     * @return 是否结果导向
     */
    public boolean isResultBased() {
        return this.feeGroup == FeeGroup.RESULT_BASED;
    }

    /**
     * 判断是否需要计时
     * @return 是否需要计时
     */
    public boolean needsTimeTracking() {
        return this == HOURLY_RATE || this == MIXED_FEE;
    }

    /**
     * 判断是否需要阶段性付款
     * @return 是否需要阶段性付款
     */
    public boolean needsStagedPayment() {
        return this == STAGE_FEE || this == MIXED_FEE;
    }

    /**
     * 计算基础收费金额
     * @param amount 基础金额
     * @param difficulty 案件难度
     * @return 计算后的收费金额
     */
    public Double calculateFee(Double amount, CaseDifficultyEnum difficulty) {
        if (this.isFree()) {
            return 0.0;
        }
        return amount * difficulty.getCoefficient();
    }

    /**
     * 收费类型分组
     */
    public enum FeeGroup {
        FIXED("固定类"),
        TIME_BASED("计时类"),
        RESULT_BASED("结果类"),
        STAGE_BASED("阶段类"),
        MIXED("混合类"),
        FREE("免费类"),
        OTHER("其他类");

        private final String description;

        FeeGroup(String description) {
            this.description = description;
        }

        public String getDescription() {
            return this.description;
        }
    }
} 