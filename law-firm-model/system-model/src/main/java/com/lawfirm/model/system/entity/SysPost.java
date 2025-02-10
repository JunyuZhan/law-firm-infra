package com.lawfirm.model.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lawfirm.model.base.entity.ModelBaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 岗位实体类
 */
@Getter
@Setter
@TableName("sys_post")
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class SysPost extends ModelBaseEntity<SysPost> {

    /**
     * 岗位编码
     */
    private String code;

    /**
     * 岗位名称
     */
    private String name;

    /**
     * 备注
     */
    private String remark;
} 