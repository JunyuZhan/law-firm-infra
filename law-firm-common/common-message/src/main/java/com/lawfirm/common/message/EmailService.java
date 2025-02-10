package com.lawfirm.common.message;

/**
 * 邮件发送基础服务接口
 */
public interface EmailService {
    
    /**
     * 发送邮件
     *
     * @param to 收件人
     * @param subject 主题
     * @param content 内容
     * @param isHtml 是否HTML格式
     */
    void send(String to, String subject, String content, boolean isHtml);
} 