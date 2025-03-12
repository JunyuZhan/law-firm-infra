package com.lawfirm.model.document.enums;

import com.lawfirm.model.base.enums.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 文档格式枚举
 * 定义律师事务所常用的文档格式类型
 */
@Getter
@AllArgsConstructor
public enum DocumentFormatEnum implements BaseEnum<String> {

    PDF("PDF", "PDF文档", "application/pdf"),
    DOCX("DOCX", "Word文档(2007+)", "application/vnd.openxmlformats-officedocument.wordprocessingml.document"),
    DOC("DOC", "Word文档(97-2003)", "application/msword"),
    XLSX("XLSX", "Excel表格(2007+)", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"),
    XLS("XLS", "Excel表格(97-2003)", "application/vnd.ms-excel"),
    PPTX("PPTX", "PPT演示(2007+)", "application/vnd.openxmlformats-officedocument.presentationml.presentation"),
    PPT("PPT", "PPT演示(97-2003)", "application/vnd.ms-powerpoint"),
    TXT("TXT", "文本文档", "text/plain"),
    RTF("RTF", "富文本格式", "application/rtf"),
    HTML("HTML", "网页文档", "text/html"),
    XML("XML", "XML文档", "text/xml"),
    ODT("ODT", "OpenDocument文本", "application/vnd.oasis.opendocument.text"),
    ODS("ODS", "OpenDocument表格", "application/vnd.oasis.opendocument.spreadsheet"),
    ODP("ODP", "OpenDocument演示", "application/vnd.oasis.opendocument.presentation"),
    MARKDOWN("MD", "Markdown文档", "text/markdown"),
    JSON("JSON", "JSON文档", "application/json");

    /**
     * 编码
     */
    private final String code;

    /**
     * 描述
     */
    private final String desc;

    /**
     * MIME类型
     */
    private final String mimeType;

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
    public static DocumentFormatEnum getByValue(String value) {
        if (value == null) {
            return null;
        }
        for (DocumentFormatEnum format : values()) {
            if (format.getValue().equals(value)) {
                return format;
            }
        }
        return null;
    }

    /**
     * 根据MIME类型获取枚举
     */
    public static DocumentFormatEnum getByMimeType(String mimeType) {
        if (mimeType == null) {
            return null;
        }
        for (DocumentFormatEnum format : values()) {
            if (format.getMimeType().equals(mimeType)) {
                return format;
            }
        }
        return null;
    }
} 