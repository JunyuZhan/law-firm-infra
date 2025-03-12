package com.lawfirm.model.document.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import com.lawfirm.model.base.enums.BaseEnum;
import lombok.Getter;

/**
 * 文档操作类型枚举
 */
@Getter
public enum DocumentOperationEnum implements BaseEnum<String> {

    /**
     * 创建文档
     */
    CREATE("CREATE", "创建文档"),

    /**
     * 更新文档
     */
    UPDATE("UPDATE", "更新文档"),

    /**
     * 查看文档
     */
    VIEW("VIEW", "查看文档"),

    /**
     * 预览文档
     */
    PREVIEW("PREVIEW", "预览文档"),

    /**
     * 编辑文档
     */
    EDIT("EDIT", "编辑文档"),

    /**
     * 删除文档
     */
    DELETE("DELETE", "删除文档"),

    /**
     * 下载文档
     */
    DOWNLOAD("DOWNLOAD", "下载文档"),

    /**
     * 打印文档
     */
    PRINT("PRINT", "打印文档"),

    /**
     * 分享文档
     */
    SHARE("SHARE", "分享文档"),

    /**
     * 移动文档
     */
    MOVE("MOVE", "移动文档"),

    /**
     * 复制文档
     */
    COPY("COPY", "复制文档"),

    /**
     * 审核文档
     */
    REVIEW("REVIEW", "审核文档"),

    /**
     * 审批文档
     */
    APPROVE("APPROVE", "审批文档"),

    /**
     * 驳回文档
     */
    REJECT("REJECT", "驳回文档"),

    /**
     * 发布文档
     */
    PUBLISH("PUBLISH", "发布文档"),

    /**
     * 撤回文档
     */
    UNPUBLISH("UNPUBLISH", "撤回文档"),

    /**
     * 归档文档
     */
    ARCHIVE("ARCHIVE", "归档文档"),

    /**
     * 恢复文档
     */
    RESTORE("RESTORE", "恢复文档"),

    /**
     * 锁定文档
     */
    LOCK("LOCK", "锁定文档"),

    /**
     * 解锁文档
     */
    UNLOCK("UNLOCK", "解锁文档"),

    /**
     * 加密文档
     */
    ENCRYPT("ENCRYPT", "加密文档"),

    /**
     * 解密文档
     */
    DECRYPT("DECRYPT", "解密文档"),

    /**
     * 标签操作
     */
    TAG("TAG", "标签操作"),

    /**
     * 评论操作
     */
    COMMENT("COMMENT", "评论操作");

    /**
     * 操作编码
     */
    @EnumValue
    @JsonValue
    private final String code;

    /**
     * 操作名称
     */
    private final String name;

    DocumentOperationEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    @Override
    public String getValue() {
        return this.code;
    }

    @Override
    public String getDescription() {
        return this.name;
    }

    /**
     * 根据编码获取枚举
     */
    public static DocumentOperationEnum getByCode(String code) {
        if (code == null) {
            return null;
        }
        for (DocumentOperationEnum operation : values()) {
            if (operation.getCode().equals(code)) {
                return operation;
            }
        }
        return null;
    }
} 