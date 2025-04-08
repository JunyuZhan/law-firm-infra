package com.lawfirm.task.exception;

import lombok.Getter;

/**
 * 任务模块错误码
 */
@Getter
public enum TaskErrorCode {
    
    PARAM_ERROR("TASK_400", "参数错误"),
    DATA_NOT_FOUND("TASK_404", "数据不存在"),
    DATA_ALREADY_EXISTS("TASK_409", "数据已存在"),
    
    TASK_NOT_FOUND("TASK_001", "任务不存在"),
    TASK_STATUS_ERROR("TASK_002", "任务状态错误"),
    TASK_TIME_CONFLICT("TASK_003", "任务时间冲突"),
    TASK_ASSIGNEE_ERROR("TASK_004", "任务负责人错误"),
    TASK_PARENT_ERROR("TASK_005", "父任务错误"),
    TASK_TAG_NOT_FOUND("TASK_006", "任务标签不存在"),
    TASK_COMMENT_NOT_FOUND("TASK_007", "任务评论不存在"),
    TASK_ATTACHMENT_NOT_FOUND("TASK_008", "任务附件不存在"),
    TASK_ATTACHMENT_UPLOAD_ERROR("TASK_009", "任务附件上传失败"),
    TASK_ATTACHMENT_DOWNLOAD_ERROR("TASK_010", "任务附件下载失败"),
    TASK_REMIND_ERROR("TASK_011", "任务提醒设置失败"),
    TASK_TEMPLATE_NOT_FOUND("TASK_012", "任务模板不存在"),
    TASK_TEMPLATE_ERROR("TASK_013", "任务模板错误");

    private final String code;
    private final String message;

    TaskErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }
} 