package com.lawfirm.client.service.strategy.follow;

import com.lawfirm.model.client.constant.ClientConstant;
import com.lawfirm.model.client.entity.base.Client;
import com.lawfirm.model.client.entity.follow.ClientFollowUp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * 事件触发跟进策略
 * 根据特定事件触发的跟进策略，如客户信息变更、业务进展等
 */
@Slf4j
@Component
public class EventFollowStrategy implements FollowUpStrategy {
    
    /**
     * 策略类型：事件触发
     */
    private static final String TYPE = "event";
    
    @Override
    public String getType() {
        return TYPE;
    }
    
    @Override
    public LocalDateTime scheduleFollowUp(Client client, Long assignee) {
        log.debug("为客户 {} 安排事件触发跟进计划", client.getClientName());
        
        // 事件触发的跟进一般较为紧急，设置为较短的周期
        return LocalDateTime.now().plusDays(3);
    }
    
    @Override
    public LocalDateTime processFollowUpResult(ClientFollowUp followUp, String content, String result) {
        log.debug("处理客户ID {} 的事件触发跟进结果", followUp.getClientId());
        
        // 基于跟进结果确定下次跟进时间
        if (result != null) {
            if (result.contains("已解决")) {
                // 如果问题已解决，转为常规跟进
                return null; // 返回null表示不需要安排新的事件跟进
            } else if (result.contains("需继续跟进")) {
                // 需要继续短期跟进
                return LocalDateTime.now().plusDays(2);
            } else if (result.contains("紧急")) {
                // 紧急情况，第二天跟进
                return LocalDateTime.now().plusDays(1);
            }
        }
        
        // 默认一周后再次跟进
        return LocalDateTime.now().plusDays(7);
    }
    
    @Override
    public boolean isApplicable(Client client) {
        // 判断是否满足事件触发的条件
        if (client == null) {
            return false;
        }
        
        // 高价值客户或特定状态的客户适用
        return client.getClientLevel() == ClientConstant.ClientLevel.CORE || 
               client.getClientLevel() == ClientConstant.ClientLevel.VIP;
    }
}
