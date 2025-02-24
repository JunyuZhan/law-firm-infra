package com.lawfirm.model.storage.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 文件类型枚举
 */
@Getter
@AllArgsConstructor
public enum FileTypeEnum {

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
} 