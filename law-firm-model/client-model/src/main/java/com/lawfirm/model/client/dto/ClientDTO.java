package com.lawfirm.model.client.dto;

import com.lawfirm.model.client.enums.ClientLevelEnum;
import com.lawfirm.model.client.enums.ClientSourceEnum;
import com.lawfirm.model.client.enums.ClientTypeEnum;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 客户数据传输对象
 */
@Data
@Accessors(chain = true)
public class ClientDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    private Long id;

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
     * @see ClientTypeEnum
     */
    private Integer clientType;

    /**
     * 客户等级
     * @see ClientLevelEnum
     */
    private Integer clientLevel;

    /**
     * 客户来源
     * @see ClientSourceEnum
     */
    private Integer clientSource;

    /**
     * 客户行业
     */
    private String industry;

    /**
     * 客户规模
     */
    private String scale;

    /**
     * 联系电话
     */
    private String phone;

    /**
     * 电子邮箱
     */
    private String email;

    /**
     * 负责人ID
     */
    private Long managerId;

    /**
     * 状态（0正常 1停用）
     */
    private Integer status;

    /**
     * 证件类型
     */
    private String idType;

    /**
     * 证件号码
     */
    private String idNumber;

    /**
     * 信用等级 A/B/C/D
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