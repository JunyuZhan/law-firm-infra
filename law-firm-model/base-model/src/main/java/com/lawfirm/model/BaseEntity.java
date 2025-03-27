package com.lawfirm.model;

import com.baomidou.mybatisplus.annotation.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@TableName("base_entity")
public class BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @TableId(type = IdType.AUTO)
    private Long id;
    
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
    
    @Version
    @TableField(fill = FieldFill.INSERT)
    private Integer version;
    
    @TableLogic
    private Integer deleted;
    
    // getters and setters
}
