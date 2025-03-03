package com.lawfirm.model.base.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 * VO基类
 */
@Data
@Accessors(chain = true)
public class BaseVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    private Long id;

    /**
     * 租户ID
     */
    private Long tenantId;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private transient LocalDateTime createTime;

    /**
     * 创建人
     */
    private String createBy;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private transient LocalDateTime updateTime;

    /**
     * 更新人
     */
    private String updateBy;

    /**
     * 状态（0正常 1停用）
     */
    private Integer status;

    /**
     * 备注
     */
    private String remark;
    
    /**
     * 自定义序列化逻辑
     */
    private void writeObject(ObjectOutputStream out) throws IOException {
        // 保存原始时间对象的时间戳
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