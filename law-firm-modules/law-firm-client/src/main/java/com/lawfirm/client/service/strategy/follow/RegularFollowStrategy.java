package com.lawfirm.client.service.strategy.follow;

import com.lawfirm.client.constant.ClientModuleConstant;
import com.lawfirm.model.client.constant.ClientConstant;
import com.lawfirm.model.client.entity.base.Client;
import com.lawfirm.model.client.entity.follow.ClientFollowUp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * 常规跟进策略
 * 根据客户等级设置不同的跟进周期
 */
@Slf4j
@Component
public class RegularFollowStrategy implements FollowUpStrategy {
    
    /**
     * 策略类型：常规跟进
     */
    private static final String TYPE = "regular";
    
    @Override
    public String getType() {
        return TYPE;
    }
    
    @Override
    public LocalDateTime scheduleFollowUp(Client client, Long assignee) {
        log.debug("为客户 {} 安排常规跟进计划", client.getClientName());
        
        // 根据客户等级确定跟进周期
        int followCycleDays = determineFollowCycle(client);
        
        // 计算下次跟进时间
        return LocalDateTime.now().plusDays(followCycleDays);
    }
    
    @Override
    public LocalDateTime processFollowUpResult(ClientFollowUp followUp, String content, String result) {
        log.debug("处理客户ID {} 的跟进结果", followUp.getClientId());
        
        // 获取客户信息 (应该注入ClientService，这里简化处理)
        Client client = new Client();
        client.setId(followUp.getClientId());
        client.setClientLevel(ClientConstant.ClientLevel.NORMAL); // 默认设为普通客户
        
        // 根据跟进结果调整跟进周期
        int adjustedCycle = determineFollowCycle(client);
        
        // 根据跟进结果，可能调整周期
        if (result != null && result.contains("重要") || result.contains("紧急")) {
            adjustedCycle = Math.max(adjustedCycle / 2, 3); // 至少3天，最多减半
        }
        
        // 计算下次跟进时间
        return LocalDateTime.now().plusDays(adjustedCycle);
    }
    
    @Override
    public boolean isApplicable(Client client) {
        // 所有客户都适用常规跟进策略
        return true;
    }
    
    /**
     * 确定跟进周期
     * @param client 客户信息
     * @return 跟进周期（天）
     */
    private int determineFollowCycle(Client client) {
        // 根据客户等级确定跟进周期
        if (client.getClientLevel() == ClientConstant.ClientLevel.CORE) {
            return ClientModuleConstant.Process.CORE_FOLLOW_CYCLE_DAYS;
        } else if (client.getClientLevel() == ClientConstant.ClientLevel.VIP) {
            return ClientModuleConstant.Process.VIP_FOLLOW_CYCLE_DAYS;
        } else {
            return ClientModuleConstant.Process.DEFAULT_FOLLOW_CYCLE_DAYS;
        }
    }
}
