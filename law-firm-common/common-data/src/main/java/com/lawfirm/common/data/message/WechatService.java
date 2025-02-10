package com.lawfirm.common.data.message;

/**
 * 微信消息发送基础服务接口
 */
public interface WechatService {
    
    /**
     * 发送微信消息
     *
     * @param openId 微信OpenID
     * @param templateId 模板ID
     * @param url 跳转链接
     * @param data 模板数据
     */
    void send(String openId, String templateId, String url, Object data);
} 