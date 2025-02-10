package com.lawfirm.finance.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lawfirm.common.core.base.BaseEntity;
import com.lawfirm.common.data.enums.StatusEnum;
import com.lawfirm.common.data.status.StatusAware;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 支出记录实体
 */
@Data
@NoArgsConstructor
@TableName("expense_record")
@EqualsAndHashCode(callSuper = true)
public class ExpenseRecord extends BaseEntity implements StatusAware {
    
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 支出金额
     */
    private BigDecimal amount;
    
    /**
     * 支出状态
     */
    private String expenseStatus;
    
    /**
     * 支出类型
     */
    private String expenseType;
    
    /**
     * 关联律所ID
     */
    private Long lawFirmId;
    
    /**
     * 关联部门ID
     */
    private Long departmentId;
    
    /**
     * 申请人ID
     */
    private Long applicantId;
    
    /**
     * 审批人ID
     */
    private Long approverId;
    
    /**
     * 支出时间
     */
    private LocalDateTime expenseTime;
    
    /**
     * 支付方式
     */
    private String paymentMethod;
    
    /**
     * 支出说明
     */
    private String description;
    
    /**
     * 备注
     */
    private String remark;

    @Override
    public StatusEnum getStatus() {
        if ("PAID".equals(expenseStatus)) {
            return StatusEnum.ENABLED;
        } else if ("REJECTED".equals(expenseStatus)) {
            return StatusEnum.DISABLED;
        }
        return StatusEnum.ENABLED;
    }

    @Override
    public void setStatus(StatusEnum status) {
        if (status == StatusEnum.DISABLED) {
            this.expenseStatus = "REJECTED";
        }
    }
} 