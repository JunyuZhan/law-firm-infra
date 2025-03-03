package com.lawfirm.model.message.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;

/**
 * 通知请求DTO
 */
@Data
public class NotifyRequest {

    /**
     * 通知标题
     */
    @NotBlank(message = "标题不能为空")
    private String title;

    /**
     * 通知内容
     */
    @NotBlank(message = "内容不能为空")
    private String content;

    /**
     * 接收者列表
     */
    @NotEmpty(message = "接收者不能为空")
    private List<String> receivers;

    /**
     * 通知渠道
     */
    @NotBlank(message = "通知渠道不能为空")
    private String channel;
} 