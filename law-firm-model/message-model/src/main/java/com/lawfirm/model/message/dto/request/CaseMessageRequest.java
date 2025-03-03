package com.lawfirm.model.message.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

/**
 * 案件消息请求DTO
 */
@Data
public class CaseMessageRequest {

    /**
     * 案件ID
     */
    @NotNull(message = "案件ID不能为空")
    private Long caseId;

    /**
     * 消息标题
     */
    @NotBlank(message = "标题不能为空")
    private String title;

    /**
     * 消息内容
     */
    @NotBlank(message = "内容不能为空")
    private String content;

    /**
     * 接收者列表
     */
    @NotEmpty(message = "接收者不能为空")
    private List<String> receivers;
} 