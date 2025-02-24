package com.lawfirm.model.client.entity.base;

import com.lawfirm.model.base.BaseModel;
import com.lawfirm.model.client.enums.ClientLevelEnum;
import com.lawfirm.model.client.enums.ClientSourceEnum;
import com.lawfirm.model.client.enums.ClientTypeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 客户基类
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class Client extends BaseModel {

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
     * 客户状态 0-待审核 1-正常 2-禁用
     */
    private Integer status;

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