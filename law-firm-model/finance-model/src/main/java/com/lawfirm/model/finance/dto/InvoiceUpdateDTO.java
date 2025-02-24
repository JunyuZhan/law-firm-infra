package com.lawfirm.model.finance.dto;

import com.lawfirm.model.base.dto.BaseDTO;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Getter
@Setter
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class InvoiceUpdateDTO extends BaseDTO {

    @NotNull(message = "发票ID不能为空")
    private Long id;

    private BigDecimal amount;

    @Size(max = 200, message = "发票抬头长度不能超过200个字符")
    private String title;

    @Size(max = 500, message = "发票内容长度不能超过500个字符")
    private String content;

    @Size(max = 50, message = "纳税人识别号长度不能超过50个字符")
    private String taxpayerNumber;

    @Size(max = 200, message = "注册地址长度不能超过200个字符")
    private String registeredAddress;

    @Size(max = 50, message = "注册电话长度不能超过50个字符")
    private String registeredPhone;

    @Size(max = 100, message = "开户银行长度不能超过100个字符")
    private String bankName;

    @Size(max = 50, message = "银行账号长度不能超过50个字符")
    private String bankAccount;

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