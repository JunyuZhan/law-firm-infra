package com.lawfirm.finance.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lawfirm.common.data.entity.BaseEntity;
import com.lawfirm.common.data.enums.StatusEnum;
import com.lawfirm.common.data.status.StatusAware;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 收费记录实体
 */
@Data
@NoArgsConstructor
@TableName("fee_record")
@EqualsAndHashCode(callSuper = true)
public class FeeRecord extends BaseEntity implements StatusAware {

    @TableId(type = IdType.AUTO)
    private Long id;

    private BigDecimal amount;  // 收费金额

    private BigDecimal paidAmount;  // 已支付金额

    private String feeStatus;  // 收费状态：UNPAID/PAID/PARTIAL/REFUNDED

    private String feeType;    // 收费类型：案件收费/咨询收费/其他收费

    private Long caseId;       // 关联案件ID

    private Long clientId;     // 关联客户ID

    private Long lawFirmId;    // 关联律所ID

    private LocalDateTime paymentTime;  // 支付时间

    private String paymentMethod;       // 支付方式

    private String description;  // 收费说明

    private String remark;      // 备注

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