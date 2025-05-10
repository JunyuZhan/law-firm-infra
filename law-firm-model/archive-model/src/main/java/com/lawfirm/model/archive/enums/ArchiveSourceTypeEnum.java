package com.lawfirm.model.archive.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 档案来源类型枚举
 */
@Getter
@AllArgsConstructor
@Schema(description = "档案来源类型枚举")
public enum ArchiveSourceTypeEnum {

    /**
     * 案件档案
     */
    CASE(1, "案件档案"),

    /**
     * 合同档案
     */
    CONTRACT(2, "合同档案"),

    /**
     * 文书档案
     */
    DOCUMENT(3, "文书档案"),

    /**
     * 证据档案
     */
    EVIDENCE(4, "证据档案"),

    /**
     * 其他档案
     */
    OTHER(9, "其他档案");

    /**
     * 编码
     */
    @EnumValue
    @JsonValue
    private final Integer code;

    /**
     * 描述
     */
    private final String desc;
}