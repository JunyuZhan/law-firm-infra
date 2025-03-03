package com.lawfirm.model.client.dto.client;

import com.lawfirm.model.base.dto.BaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 客户更新DTO
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class ClientUpdateDTO extends BaseDTO {

    private static final long serialVersionUID = 1L;

    /**
     * 客户ID
     */
    private Long id;

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
     * 证件类型
     */
    private String idType;

    /**
     * 证件号码
     */
    private String idNumber;

    /**
     * 联系电话
     */
    private String contactPhone;

    /**
     * 电子邮箱
     */
    private String email;

    /**
     * 客户状态
     */
    private Integer status;

    /**
     * 信用等级
     */
    private String creditLevel;

    /**
     * 法定代表人（企业客户）
     */
    private String legalRepresentative;

    /**
     * 统一社会信用代码（企业客户）
     */
    private String unifiedSocialCreditCode;
} 
