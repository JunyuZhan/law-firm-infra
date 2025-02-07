package com.lawfirm.finance.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("fin_invoice")
public class Invoice {

    @TableId(type = IdType.AUTO)
    private Long id;

    private String title;

    private Integer type;

    private BigDecimal amount;

    private BigDecimal taxRate;

    private BigDecimal taxAmount;

    private LocalDateTime issueDate;

    private String invoiceNo;

    private Long matterId;

    private String remark;

    private Integer status;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private String creator;

    private String updater;
} 