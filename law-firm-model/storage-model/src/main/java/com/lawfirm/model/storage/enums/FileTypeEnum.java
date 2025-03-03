package com.lawfirm.model.storage.enums;

import com.lawfirm.model.base.enums.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 文件类型枚举
 */
@Getter
@AllArgsConstructor
public enum FileTypeEnum implements BaseEnum<String> {

    IMAGE("IMAGE", "图片"),
    DOCUMENT("DOCUMENT", "文档"),
    VIDEO("VIDEO", "视频"),
    AUDIO("AUDIO", "音频"),
    ARCHIVE("ARCHIVE", "压缩包"),
    OTHER("OTHER", "其他");

    /**
     * 编码
     */
    private final String code;

    /**
     * 描述
     */
    private final String desc;
    
    @Override
    public String getValue() {
        return this.code;
    }
    
    @Override
    public String getDescription() {
        return this.desc;
    }
    
    /**
     * 根据值获取枚举
     */
    public static FileTypeEnum getByValue(String value) {
        if (value == null) {
            return null;
        }
        for (FileTypeEnum type : values()) {
            if (type.getValue().equals(value)) {
                return type;
            }
        }
        return null;
    }
} 