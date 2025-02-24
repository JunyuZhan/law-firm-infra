package com.lawfirm.model.base.geo;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 地址实体
 */
@Data
@Accessors(chain = true)
public class Address {

    /**
     * 国家
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
     * 街道
     */
    private String street;

    /**
     * 详细地址
     */
    private String detail;

    /**
     * 邮政编码
     */
    private String zipCode;

    /**
     * 经度
     */
    private Double longitude;

    /**
     * 纬度
     */
    private Double latitude;
} 