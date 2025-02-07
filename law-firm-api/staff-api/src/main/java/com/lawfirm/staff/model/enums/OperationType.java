package com.lawfirm.staff.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OperationType {
    
    QUERY("查询"),
    CREATE("创建"),
    UPDATE("更新"),
    DELETE("删除"),
    EXPORT("导出"),
    IMPORT("导入"),
    UPLOAD("上传"),
    DOWNLOAD("下载"),
    APPROVE("审批"),
    REJECT("驳回"),
    SUBMIT("提交"),
    CANCEL("取消"),
    COMPLETE("完成");
    
    private final String description;
} 