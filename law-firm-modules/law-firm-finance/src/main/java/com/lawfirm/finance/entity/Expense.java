package com.lawfirm.finance.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lawfirm.common.data.entity.BaseEntity;
import com.lawfirm.common.core.enums.StatusEnum;
import com.lawfirm.common.data.status.StatusAware;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 支出记录实体
 */
@Data
@NoArgsConstructor
@TableName("expense_record")
@EqualsAndHashCode(callSuper = true)
public class Expense extends BaseEntity implements StatusAware {

    @TableId(type = IdType.AUTO)
    private Long id;

    private BigDecimal amount;  // 支出金额

    private String expenseStatus;  // 支出状态：PENDING/APPROVED/REJECTED/PAID

    private String expenseType;  // 支出类型：日常运营/人员工资/办公设备/其他支出

    private Long lawFirmId;    // 关联律所ID

    private Long departmentId; // 关联部门ID

    private Long applicantId;  // 申请人ID

    private Long approverId;   // 审批人ID

    private LocalDateTime expenseTime;  // 支出时间

    private String paymentMethod;       // 支付方式

    private String description;  // 支出说明

    private String remark;      // 备注

    @Override
    public StatusEnum getStatus() {
        if ("PAID".equals(expenseStatus) || "APPROVED".equals(expenseStatus)) {
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