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
 * 收费记录实体
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "fee_record")
@EqualsAndHashCode(callSuper = true)
public class FeeRecord extends BaseEntity implements StatusAware {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "收费金额不能为空")
    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal amount;  // 收费金额

    @Column(name = "paid_amount", precision = 19, scale = 2)
    private BigDecimal paidAmount;  // 已支付金额

    @Column(name = "fee_status", length = 20)
    private String feeStatus;  // 收费状态：UNPAID/PAID/PARTIAL/REFUNDED

    @Column(name = "fee_type", length = 50)
    private String feeType;    // 收费类型：案件收费/咨询收费/其他收费

    @Column(name = "case_id")
    private Long caseId;       // 关联案件ID

    @Column(name = "client_id")
    private Long clientId;     // 关联客户ID

    @Column(name = "law_firm_id")
    private Long lawFirmId;    // 关联律所ID

    @Column(name = "payment_time")
    private LocalDateTime paymentTime;  // 支付时间

    @Column(name = "payment_method", length = 50)
    private String paymentMethod;       // 支付方式

    @Column(length = 500)
    private String description;  // 收费说明

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
        if ("PAID".equals(feeStatus) || "PARTIAL".equals(feeStatus)) {
            return StatusEnum.ENABLED;
        } else if ("REFUNDED".equals(feeStatus)) {
            return StatusEnum.DISABLED;
        }
        return StatusEnum.ENABLED;
    }

    @Override
    public void setStatus(StatusEnum status) {
        if (status == StatusEnum.DISABLED) {
            this.feeStatus = "REFUNDED";
        }
    }
} 