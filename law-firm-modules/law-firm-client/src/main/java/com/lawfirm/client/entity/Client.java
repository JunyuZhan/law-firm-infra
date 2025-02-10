package com.lawfirm.client.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lawfirm.common.core.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 客户实体类
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("client")
public class Client extends BaseEntity {

    /**
     * 客户编号
     */
    private String clientNo;

    /**
     * 客户名称
     */
    private String clientName;

    /**
     * 客户类型（1:个人、2:企业）
     */
    private Integer clientType;

    /**
     * 证件类型（1:身份证、2:护照、3:营业执照）
     */
    private Integer certificateType;

    /**
     * 证件号码
     */
    private String certificateNo;

    /**
     * 联系电话
     */
    private String phone;

    /**
     * 电子邮箱
     */
    private String email;

    /**
     * 联系地址
     */
    private String address;

    /**
     * 客户状态（1:潜在、2:正式、3:流失）
     */
    private Integer status;

    /**
     * 所属律师ID
     */
    private Long lawyerId;

    /**
     * 来源渠道（1:自主开发、2:同行推荐、3:客户介绍、4:网络推广、5:其他）
     */
    private Integer sourceChannel;
} 