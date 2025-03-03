package com.lawfirm.model.client.dto.client;

import com.lawfirm.model.base.dto.BaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 客户查询DTO
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class ClientQueryDTO extends BaseDTO {

    private static final long serialVersionUID = 1L;

    /**
     * 客户编号
     */
    private String clientNo;

    /**
     * 客户名称
     */
    private String clientName;

    /**
     * 客户类型
     */
    private Integer clientType;

    /**
     * 客户等级
     */
    private Integer clientLevel;

    /**
     * 客户来源
     */
    private Integer clientSource;

    /**
     * 证件号码
     */
    private String idNumber;

    /**
     * 联系电话
     */
    private String contactPhone;

    /**
     * 客户状态
     */
    private Integer status;

    /**
     * 信用等级
     */
    private String creditLevel;

    /**
     * 统一社会信用代码
     */
    private String unifiedSocialCreditCode;

    /**
     * 创建时间起
     */
    private String createTimeStart;

    /**
     * 创建时间止
     */
    private String createTimeEnd;
} 
