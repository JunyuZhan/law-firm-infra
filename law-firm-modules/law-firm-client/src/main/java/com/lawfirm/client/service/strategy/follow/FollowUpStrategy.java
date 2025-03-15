package com.lawfirm.client.service.strategy.follow;

import com.lawfirm.model.client.entity.base.Client;
import com.lawfirm.model.client.entity.follow.ClientFollowUp;

import java.time.LocalDateTime;

/**
 * 客户跟进策略接口
 * 定义不同客户跟进场景的策略
 */
public interface FollowUpStrategy {
    
    /**
     * 策略类型
     */
    String getType();
    
    /**
     * 安排跟进计划
     * @param client 客户信息
     * @param assignee 负责人ID
     * @return 跟进时间
     */
    LocalDateTime scheduleFollowUp(Client client, Long assignee);
    
    /**
     * 处理跟进结果
     * @param followUp 跟进记录
     * @param content 跟进内容
     * @param result 跟进结果
     * @return 下次跟进时间
     */
    LocalDateTime processFollowUpResult(ClientFollowUp followUp, String content, String result);
    
    /**
     * 判断是否适用该策略
     * @param client 客户信息
     * @return 是否适用
     */
    boolean isApplicable(Client client);
}
