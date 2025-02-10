package com.lawfirm.model.finance.dto;

import com.lawfirm.common.data.dto.BaseDTO;
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
public class FinanceUpdateDTO extends BaseDTO {

    @NotNull(message = "ID不能为空")
    private Long id;

    private BigDecimal amount;
    private String transactionType;

    @Size(max = 200, message = "交易描述长度不能超过200个字符")
    private String description;

    private LocalDateTime transactionTime;

    @Size(max = 50, message = "支付方式长度不能超过50个字符")
    private String paymentMethod;

    @Size(max = 500, message = "备注长度不能超过500个字符")
    private String remark;

    @Size(max = 500, message = "变更说明长度不能超过500个字符")
    private String changeLog;

    @Override
    public BaseDTO setId(Long id) {
        super.setId(id);
        return this;
    }

    @Override
    public BaseDTO setRemark(String remark) {
        super.setRemark(remark);
        return this;
    }
} 