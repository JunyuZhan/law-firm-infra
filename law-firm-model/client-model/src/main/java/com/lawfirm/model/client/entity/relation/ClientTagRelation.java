package com.lawfirm.model.client.entity.relation;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * 客户标签关联实体
 */
@Data
@Accessors(chain = true)
@TableName("client_tag_relation")
public class ClientTagRelation implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 客户ID
     */
    @TableField("client_id")
    private Long clientId;

    /**
     * 标签ID
     */
    @TableField("tag_id")
    private Long tagId;
    
    /**
     * 创建人
     */
    @TableField("create_by")
    private Long createBy;
    
    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;
} 