package com.lawfirm.model.client.entity.common;

import com.lawfirm.model.base.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 客户地址
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class ClientAddress extends BaseModel {

    /**
     * 客户ID
     */
    private Long clientId;

    /**
     * 地址类型 1-注册地址 2-办公地址 3-收件地址 4-开票地址 99-其他
     */
    private Integer addressType;

    /**
     * 国家地区
     */
    private String country;

    /**
     * 省份
     */
    private String province;

    /**
     * 城市
     */
    private String city;

    /**
     * 区县
     */
    private String district;

    /**
     * 详细地址
     */
    private String address;

    /**
     * 邮政编码
     */
    private String postcode;

    /**
     * 是否默认地址 0-否 1-是
     */
    private Integer isDefault;

    /**
     * 地址标签
     */
    private String addressTag;

    /**
     * 地址状态 0-无效 1-有效
     */
    private Integer status;
} 