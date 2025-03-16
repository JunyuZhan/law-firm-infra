package com.lawfirm.model.contract.dto;

import com.lawfirm.model.base.dto.BaseDTO;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.EqualsAndHashCode;

/**
 * 合同审核的请求数据传输对象
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper=false)
public class ContractReviewDTO extends BaseDTO {
    private static final long serialVersionUID = 1L;

    private Long contractId;       // 合同ID
    private Long reviewerId;       // 审核人ID
    private String reviewer;       // 审核人姓名
    private Integer reviewLevel;   // 审核级别
    private String reviewStatus;   // 审核状态
    private String reviewComments; // 审核意见
} 