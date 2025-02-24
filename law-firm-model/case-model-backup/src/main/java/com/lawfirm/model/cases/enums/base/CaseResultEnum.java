package com.lawfirm.model.cases.enums.base;

import com.lawfirm.model.base.enums.BaseEnum;
import lombok.Getter;

/**
 * 案件结果枚举
 */
@Getter
public enum CaseResultEnum implements BaseEnum<String> {
    
    WIN("胜诉", ResultGroup.FAVORABLE, 1.0),
    PARTIAL_WIN("部分胜诉", ResultGroup.FAVORABLE, 0.7),
    SETTLEMENT("调解结案", ResultGroup.NEUTRAL, 0.6),
    WITHDRAWAL_AFTER_SETTLEMENT("调解撤诉", ResultGroup.NEUTRAL, 0.6),
    WITHDRAWAL("撤诉", ResultGroup.UNFAVORABLE, 0.3),
    LOSE("败诉", ResultGroup.UNFAVORABLE, 0.0),
    REJECTION("驳回起诉", ResultGroup.UNFAVORABLE, 0.1),
    DISMISSAL("不予受理", ResultGroup.UNFAVORABLE, 0.0),
    REVOCATION("撤销案件", ResultGroup.NEUTRAL, 0.4),
    JURISDICTION_TRANSFER("移送管辖", ResultGroup.NEUTRAL, 0.5),
    MEDIATION_IN_PROGRESS("调解中", ResultGroup.IN_PROGRESS, null),
    TRIAL_IN_PROGRESS("审理中", ResultGroup.IN_PROGRESS, null),
    PENDING_JUDGMENT("待判决", ResultGroup.IN_PROGRESS, null);

    private final String description;
    private final ResultGroup resultGroup;
    private final Double successRate;

    CaseResultEnum(String description, ResultGroup resultGroup, Double successRate) {
        this.description = description;
        this.resultGroup = resultGroup;
        this.successRate = successRate;
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
     * 获取结果分组
     * @return 结果分组
     */
    public ResultGroup getResultGroup() {
        return this.resultGroup;
    }

    /**
     * 获取成功率
     * @return 成功率
     */
    public Double getSuccessRate() {
        return this.successRate;
    }

    /**
     * 判断是否为有利结果
     * @return 是否有利
     */
    public boolean isFavorable() {
        return this.resultGroup == ResultGroup.FAVORABLE;
    }

    /**
     * 判断是否为不利结果
     * @return 是否不利
     */
    public boolean isUnfavorable() {
        return this.resultGroup == ResultGroup.UNFAVORABLE;
    }

    /**
     * 判断是否为中立结果
     * @return 是否中立
     */
    public boolean isNeutral() {
        return this.resultGroup == ResultGroup.NEUTRAL;
    }

    /**
     * 判断是否在进行中
     * @return 是否进行中
     */
    public boolean isInProgress() {
        return this.resultGroup == ResultGroup.IN_PROGRESS;
    }

    /**
     * 判断是否已结案
     * @return 是否结案
     */
    public boolean isConcluded() {
        return !isInProgress();
    }

    /**
     * 判断是否可以申请执行
     * @return 是否可执行
     */
    public boolean isEnforceable() {
        return this == WIN || this == PARTIAL_WIN || this == SETTLEMENT;
    }

    /**
     * 判断是否需要退费
     * @return 是否需要退费
     */
    public boolean needsRefund() {
        return this == DISMISSAL || this == REJECTION;
    }

    /**
     * 计算风险代理收费比例
     * @param baseFee 基础收费
     * @return 实际收费金额
     */
    public Double calculateContingencyFee(Double baseFee) {
        if (this.successRate == null) {
            return null;
        }
        return baseFee * this.successRate;
    }

    /**
     * 获取结果说明
     * @return 完整的结果说明
     */
    public String getResultDescription() {
        StringBuilder desc = new StringBuilder(this.description);
        if (this.successRate != null) {
            desc.append(String.format("(成功率: %.1f%%)", this.successRate * 100));
        }
        if (this.isEnforceable()) {
            desc.append("[可申请执行]");
        }
        return desc.toString();
    }

    /**
     * 案件结果分组
     */
    public enum ResultGroup {
        FAVORABLE("有利结果"),
        UNFAVORABLE("不利结果"),
        NEUTRAL("中立结果"),
        IN_PROGRESS("进行中");

        private final String description;

        ResultGroup(String description) {
            this.description = description;
        }

        public String getDescription() {
            return this.description;
        }
    }
} 