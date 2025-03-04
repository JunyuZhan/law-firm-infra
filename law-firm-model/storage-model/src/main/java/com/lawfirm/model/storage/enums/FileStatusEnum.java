package com.lawfirm.model.storage.enums;

import com.lawfirm.model.base.enums.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 文件状态枚�? * 
 * @author JunyuZhan
 */
@Getter
@AllArgsConstructor
public enum FileStatusEnum implements BaseEnum<Integer> {
    /**
     * 上传�?     */
    UPLOADING(0, "上传�?),
    
    /**
     * 上传完成
     */
    UPLOADED(1, "上传完成"),
    
    /**
     * 上传失败
     */
    FAILED(2, "上传失败"),
    
    /**
     * 已删�?     */
    DELETED(3, "已删�?);
    
    /**
     * 状态码
     */
    private final Integer code;
    
    /**
     * 描述
     */
    private final String desc;
    
    @Override
    public Integer getValue() {
        return this.code;
    }
    
    @Override
    public String getDescription() {
        return this.desc;
    }
    
    /**
     * 根据值获取枚�?     */
    public static FileStatusEnum getByValue(Integer value) {
        if (value == null) {
            return null;
        }
        for (FileStatusEnum status : values()) {
            if (status.getValue().equals(value)) {
                return status;
            }
        }
        return null;
    }
} 
