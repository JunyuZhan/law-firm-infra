package com.lawfirm.finance.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.lawfirm.model.base.entity.BaseEntity;
import com.lawfirm.model.base.enums.StatusEnum;
import com.lawfirm.model.base.status.StatusAware;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;

/**
 * 支出记录实体
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "expense_record")
@EqualsAndHashCode(callSuper = true)
public class Expense extends BaseEntity implements StatusAware {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "支出金额不能为空")
    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal amount;  // 支出金额

    @Column(name = "expense_status", length = 20)
    private String expenseStatus;  // 支出状态：PENDING/APPROVED/REJECTED/PAID

    @Column(name = "expense_type", length = 50)
    private String expenseType;  // 支出类型：日常运营/人员工资/办公设备/其他支出

    @Column(name = "law_firm_id")
    private Long lawFirmId;    // 关联律所ID

    @Column(name = "department_id")
    private Long departmentId; // 关联部门ID

    @Column(name = "applicant_id")
    private Long applicantId;  // 申请人ID

    @Column(name = "approver_id")
    private Long approverId;   // 审批人ID

    @Column(name = "expense_time")
    private LocalDateTime expenseTime;  // 支出时间

    @Column(name = "payment_method", length = 50)
    private String paymentMethod;       // 支付方式

    @Column(length = 500)
    private String description;  // 支出说明

    @Column(length = 500)
    private String remark;      // 备注

    @Column(name = "created_time", nullable = false, updatable = false)
    private LocalDateTime createdTime;

    @Column(name = "updated_time", nullable = false)
    private LocalDateTime updatedTime;

    @Column(name = "created_by", nullable = false, length = 50)
    private String createdBy;

    @Column(name = "updated_by", nullable = false, length = 50)
    private String updatedBy;

    @PrePersist
    protected void onCreate() {
        createdTime = LocalDateTime.now();
        updatedTime = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedTime = LocalDateTime.now();
    }

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