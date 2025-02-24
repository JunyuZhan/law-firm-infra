package com.lawfirm.model.base.geo;

import com.lawfirm.model.base.entity.TreeEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * 区域实体
 */
@Getter
@Setter
@Accessors(chain = true)
public class Region extends TreeEntity {

    /**
     * 区域编码
     */
    @TableField("code")
    private String code;

    /**
     * 区域名称
     */
    @TableField("name")
    private String name;

    /**
     * 区域类型（1-国家，2-省份，3-城市，4-区县，5-街道）
     */
    @TableField("type")
    private Integer type;

    /**
     * 经度
     */
    @TableField("longitude")
    private Double longitude;

    /**
     * 纬度
     */
    @TableField("latitude")
    private Double latitude;
} 