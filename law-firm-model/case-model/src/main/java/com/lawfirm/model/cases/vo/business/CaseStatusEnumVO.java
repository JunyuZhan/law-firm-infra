package com.lawfirm.model.cases.vo.business;

import com.lawfirm.model.base.vo.BaseVO;
import com.lawfirm.model.cases.enums.base.CaseStatusEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 案件状态枚举视图对象
 * 
 * 包含状态枚举的基本信息，如状态编码、名称、描述等
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class CaseStatusEnumVO extends BaseVO {

    private static final long serialVersionUID = 1L;

    /**
     * 状态编码
     */
    private Integer statusCode;

    /**
     * 状态名称
     */
    private String statusName;

    /**
     * 状态描述
     */
    private String statusDescription;

    /**
     * 状态枚举
     */
    private CaseStatusEnum statusEnum;

    /**
     * 是否为初始状态
     */
    private Boolean isInitialStatus;

    /**
     * 是否为终止状态
     */
    private Boolean isTerminalStatus;

    /**
     * 是否为活动状态
     */
    private Boolean isActiveStatus;

    /**
     * 是否为暂停状态
     */
    private Boolean isPausedStatus;

    /**
     * 是否为关闭状态
     */
    private Boolean isClosedStatus;

    /**
     * 是否为异常状态
     */
    private Boolean isAbnormalStatus;

    /**
     * 是否需要审批
     */
    private Boolean needApproval;

    /**
     * 是否允许回退
     */
    private Boolean allowRollback;

    /**
     * 是否允许跳转
     */
    private Boolean allowSkip;

    /**
     * 是否允许编辑
     */
    private Boolean allowEdit;

    /**
     * 是否允许删除
     */
    private Boolean allowDelete;

    /**
     * 状态顺序
     */
    private Integer statusOrder;

    /**
     * 状态组
     */
    private String statusGroup;

    /**
     * 状态颜色
     */
    private String statusColor;

    /**
     * 状态图标
     */
    private String statusIcon;

    /**
     * 备注
     */
    private String remarks;

    /**
     * 判断是否为初始状态
     */
    public boolean isInitialStatus() {
        return Boolean.TRUE.equals(this.isInitialStatus);
    }

    /**
     * 判断是否为终止状态
     */
    public boolean isTerminalStatus() {
        return Boolean.TRUE.equals(this.isTerminalStatus);
    }

    /**
     * 判断是否为活动状态
     */
    public boolean isActiveStatus() {
        return Boolean.TRUE.equals(this.isActiveStatus);
    }

    /**
     * 判断是否为暂停状态
     */
    public boolean isPausedStatus() {
        return Boolean.TRUE.equals(this.isPausedStatus);
    }

    /**
     * 判断是否为关闭状态
     */
    public boolean isClosedStatus() {
        return Boolean.TRUE.equals(this.isClosedStatus);
    }

    /**
     * 判断是否为异常状态
     */
    public boolean isAbnormalStatus() {
        return Boolean.TRUE.equals(this.isAbnormalStatus);
    }

    /**
     * 判断是否需要审批
     */
    public boolean needApproval() {
        return Boolean.TRUE.equals(this.needApproval);
    }

    /**
     * 判断是否允许回退
     */
    public boolean allowRollback() {
        return Boolean.TRUE.equals(this.allowRollback);
    }

    /**
     * 判断是否允许跳转
     */
    public boolean allowSkip() {
        return Boolean.TRUE.equals(this.allowSkip);
    }

    /**
     * 判断是否允许编辑
     */
    public boolean allowEdit() {
        return Boolean.TRUE.equals(this.allowEdit);
    }

    /**
     * 判断是否允许删除
     */
    public boolean allowDelete() {
        return Boolean.TRUE.equals(this.allowDelete);
    }
} 