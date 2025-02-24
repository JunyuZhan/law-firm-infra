package com.lawfirm.model.client.vo;

import com.lawfirm.model.base.vo.BaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 客户视图对象
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class ClientVO extends BaseVO {

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
     * 客户类型名称
     */
    private String clientTypeName;

    /**
     * 客户等级
     */
    private Integer clientLevel;

    /**
     * 客户等级名称
     */
    private String clientLevelName;

    /**
     * 客户来源
     */
    private Integer clientSource;

    /**
     * 客户来源名称
     */
    private String clientSourceName;

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

    /**
     * 默认联系人
     */
    private String defaultContactName;

    /**
     * 默认联系人电话
     */
    private String defaultContactPhone;

    /**
     * 默认地址
     */
    private String defaultAddress;
} 