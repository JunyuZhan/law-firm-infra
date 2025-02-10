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
 * 发票实体
 */
@Data
@NoArgsConstructor
@TableName("fin_invoice")
@EqualsAndHashCode(callSuper = true)
public class Invoice extends BaseEntity implements StatusAware {

    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 发票抬头
     */
    private String title;

    /**
     * 发票类型（1:增值税普通发票、2:增值税专用发票）
     */
    private Integer type;

    /**
     * 发票金额
     */
    private BigDecimal amount;

    /**
     * 税率
     */
    private BigDecimal taxRate;

    /**
     * 税额
     */
    private BigDecimal taxAmount;

    /**
     * 开票日期
     */
    private LocalDateTime issueDate;

    /**
     * 发票号码
     */
    private String invoiceNo;

    /**
     * 关联事项ID
     */
    private Long matterId;

    /**
     * 备注
     */
    private String remark;

    /**
     * 状态（1:正常、2:作废）
     */
    private Integer status;

    @Override
    public StatusEnum getStatus() {
        return status != null && status == 1 ? StatusEnum.ENABLED : StatusEnum.DISABLED;
    }

    @Override
    public void setStatus(StatusEnum status) {
        this.status = status == StatusEnum.ENABLED ? 1 : 2;
    }
} 