package com.lawfirm.model.finance.dto;

import com.lawfirm.common.data.dto.BaseDTO;
import com.lawfirm.model.finance.enums.PaymentMethodEnum;
import com.lawfirm.model.finance.enums.TransactionTypeEnum;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class FinanceCreateDTO extends BaseDTO {

    @NotNull(message = "金额不能为空")
    private BigDecimal amount;

    @NotNull(message = "交易类型不能为空")
    private TransactionTypeEnum transactionType;

    private Long lawFirmId;
    private Long caseId;
    private Long clientId;

    @Size(max = 200, message = "交易描述长度不能超过200个字符")
    private String description;

    private LocalDateTime transactionTime;

    @NotNull(message = "支付方式不能为空")
    private PaymentMethodEnum paymentMethod;

    @Size(max = 100, message = "交易编号长度不能超过100个字符")
    private String transactionNumber;

    @Size(max = 500, message = "备注长度不能超过500个字符")
    private String remark;

    @Override
    public BaseDTO setRemark(String remark) {
        super.setRemark(remark);
        return this;
    }
} 