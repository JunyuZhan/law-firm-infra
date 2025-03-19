package com.lawfirm.model.client.entity.base;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lawfirm.model.client.enums.ClientLevelEnum;
import com.lawfirm.model.client.enums.ClientSourceEnum;
import com.lawfirm.model.client.enums.ClientTypeEnum;
import com.lawfirm.model.base.entity.ModelBaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 客户基类
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@TableName("client_info")
public class Client extends ModelBaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 客户编号
     */
    @TableField("client_no")
    private String clientNo;

    /**
     * 客户名称
     */
    @TableField("client_name")
    private String clientName;

    /**
     * 客户类型
     * @see ClientTypeEnum
     */
    @TableField("client_type")
    private Integer clientType;

    /**
     * 客户等级
     * @see ClientLevelEnum
     */
    @TableField("client_level")
    private Integer clientLevel;

    /**
     * 客户来源
     * @see ClientSourceEnum
     */
    @TableField("client_source")
    private Integer clientSource;

    /**
     * 客户行业
     */
    @TableField("industry")
    private String industry;

    /**
     * 客户规模
     */
    @TableField("scale")
    private String scale;

    /**
     * 联系电话
     */
    @TableField("phone")
    private String phone;

    /**
     * 电子邮箱
     */
    @TableField("email")
    private String email;

    /**
     * 负责人ID
     */
    @TableField("manager_id")
    private Long managerId;

    /**
     * 状态（0正常 1停用）
     */
    @TableField("status")
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

    /**
     * 案件总数
     */
    @TableField("case_count")
    private Integer caseCount;

    /**
     * 活跃案件数
     */
    @TableField("active_case_count")
    private Integer activeCaseCount;
} 
