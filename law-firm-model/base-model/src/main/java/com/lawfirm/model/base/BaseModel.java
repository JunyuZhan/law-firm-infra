package com.lawfirm.model.base;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 * 基础模型类
 */
@Data
@Accessors(chain = true)
public class BaseModel implements Serializable {
    
    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    private Long id;

    /**
     * 创建时间
     */
    private transient LocalDateTime createTime;

    /**
     * 创建人ID
     */
    private Long createBy;

    /**
     * 更新时间
     */
    private transient LocalDateTime updateTime;

    /**
     * 更新人ID
     */
    private Long updateBy;

    /**
     * 是否删除 0-否 1-是
     */
    private Integer isDeleted;

    /**
     * 备注
     */
    private String remark;
    
    /**
     * 自定义序列化逻辑
     */
    private void writeObject(ObjectOutputStream out) throws IOException {
        // 保存时间戳
        long createTimeEpoch = createTime != null ? createTime.toEpochSecond(ZoneOffset.UTC) : 0;
        long updateTimeEpoch = updateTime != null ? updateTime.toEpochSecond(ZoneOffset.UTC) : 0;
        
        // 执行默认序列化
        out.defaultWriteObject();
        
        // 写入时间戳
        out.writeLong(createTimeEpoch);
        out.writeLong(updateTimeEpoch);
    }
    
    /**
     * 自定义反序列化逻辑
     */
    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        // 执行默认反序列化
        in.defaultReadObject();
        
        // 读取时间戳并转换回LocalDateTime
        long createTimeEpoch = in.readLong();
        long updateTimeEpoch = in.readLong();
        
        if (createTimeEpoch > 0) {
            this.createTime = LocalDateTime.ofEpochSecond(createTimeEpoch, 0, ZoneOffset.UTC);
        }
        
        if (updateTimeEpoch > 0) {
            this.updateTime = LocalDateTime.ofEpochSecond(updateTimeEpoch, 0, ZoneOffset.UTC);
        }
    }
} 