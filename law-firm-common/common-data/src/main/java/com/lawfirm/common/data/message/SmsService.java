package com.lawfirm.common.data.message;

/**
 * 短信发送基础服务接口
 */
public interface SmsService {
    
    /**
     * 发送短信
     *
     * @param mobile 手机号
     * @param content 内容
     * @param templateCode 模板编码
     * @param params 模板参数
     */
    void send(String mobile, String content, String templateCode, Object... params);
} 