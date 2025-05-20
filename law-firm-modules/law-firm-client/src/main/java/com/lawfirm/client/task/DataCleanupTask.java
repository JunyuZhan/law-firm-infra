package com.lawfirm.client.task;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lawfirm.client.service.impl.ClientServiceImpl;
import com.lawfirm.client.service.impl.ContactServiceImpl;
import com.lawfirm.client.service.impl.FollowUpServiceImpl;
import com.lawfirm.model.client.constant.ClientConstant;
import com.lawfirm.model.client.entity.base.Client;
import com.lawfirm.model.client.entity.common.ClientContact;
import com.lawfirm.model.client.entity.follow.ClientFollowUp;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 客户数据清理定时任务
 * 定期清理无效的客户数据，包括临时客户、长期不活跃客户等
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class DataCleanupTask {

    private final ClientServiceImpl clientService;
    private final ContactServiceImpl contactService;
    private final FollowUpServiceImpl followUpService;
    
    // 记录最后一次清理时间
    private final AtomicReference<LocalDateTime> lastCleanupTime = new AtomicReference<>(null);

    /**
     * 每周日凌晨2点执行清理任务
     */
    @Scheduled(cron = "0 0 2 ? * SUN")
    @Transactional(rollbackFor = Exception.class)
    public void cleanupTemporaryClients() {
        log.info("开始执行临时客户清理任务");
        
        try {
            // 获取超过30天的临时客户
            LocalDateTime threshold = LocalDateTime.now().minusDays(30);
            
            LambdaQueryWrapper<Client> queryWrapper = new LambdaQueryWrapper<Client>()
                    .eq(Client::getStatus, ClientConstant.Status.DISABLED)
                    .lt(Client::getCreateTime, threshold);
            
            List<Client> temporaryClients = clientService.list(queryWrapper);
            
            log.info("找到{}条临时客户数据需要清理", temporaryClients.size());
            
            // 清理临时客户数据
            for (Client client : temporaryClients) {
                try {
                    // 清理联系人
                    contactService.remove(new LambdaQueryWrapper<ClientContact>()
                            .eq(ClientContact::getClientId, client.getId()));
                    
                    // 清理跟进记录
                    followUpService.remove(new LambdaQueryWrapper<ClientFollowUp>()
                            .eq(ClientFollowUp::getClientId, client.getId()));
                    
                    // 删除客户
                    clientService.removeById(client.getId());
                    
                    log.info("已清理临时客户数据，客户ID: {}, 客户名称: {}", client.getId(), client.getClientName());
                } catch (Exception e) {
                    log.error("清理临时客户数据失败，客户ID: {}", client.getId(), e);
                }
            }
            
            // 更新最后清理时间
            lastCleanupTime.set(LocalDateTime.now());
        } catch (Exception e) {
            log.error("执行临时客户清理任务失败", e);
        }
        
        log.info("临时客户清理任务执行完成");
    }
    
    /**
     * 每月1号凌晨3点执行清理任务
     */
    @Scheduled(cron = "0 0 3 1 * ?")
    public void archiveInactiveClients() {
        log.info("开始执行不活跃客户归档任务");
        
        try {
            // 获取超过1年不活跃的客户
            LocalDateTime threshold = LocalDateTime.now().minusYears(1);
            
            LambdaQueryWrapper<Client> queryWrapper = new LambdaQueryWrapper<Client>()
                    .eq(Client::getStatus, ClientConstant.Status.NORMAL)
                    .lt(Client::getUpdateTime, threshold);
            
            List<Client> inactiveClients = clientService.list(queryWrapper);
            
            log.info("找到{}条不活跃客户数据需要归档", inactiveClients.size());
            
            // 归档不活跃客户
            for (Client client : inactiveClients) {
                try {
                    // 更新客户状态为归档
                    client.setStatus(ClientConstant.Status.DELETED);
                    clientService.updateById(client);
                    
                    log.info("已归档不活跃客户，客户ID: {}, 客户名称: {}", client.getId(), client.getClientName());
                } catch (Exception e) {
                    log.error("归档不活跃客户失败，客户ID: {}", client.getId(), e);
                }
            }
            
            // 更新最后清理时间
            lastCleanupTime.set(LocalDateTime.now());
        } catch (Exception e) {
            log.error("执行不活跃客户归档任务失败", e);
        }
        
        log.info("不活跃客户归档任务执行完成");
    }
    
    /**
     * 每天凌晨4点清理完成的跟进记录
     */
    @Scheduled(cron = "0 0 4 * * ?")
    public void cleanupCompletedFollowUps() {
        log.info("开始执行已完成跟进记录清理任务");
        
        try {
            // 获取超过90天的已完成跟进记录
            LocalDateTime threshold = LocalDateTime.now().minusDays(90);
            
            LambdaQueryWrapper<ClientFollowUp> queryWrapper = new LambdaQueryWrapper<ClientFollowUp>()
                    .eq(ClientFollowUp::getStatus, ClientConstant.FollowUp.STATUS_COMPLETED)
                    .lt(ClientFollowUp::getUpdateTime, threshold);
            
            long count = followUpService.count(queryWrapper);
            
            log.info("找到{}条已完成跟进记录需要清理", count);
            
            // 批量删除已完成跟进记录
            if (count > 0) {
                boolean result = followUpService.remove(queryWrapper);
                log.info("已完成跟进记录清理结果: {}", result);
            }
            
            // 更新最后清理时间
            lastCleanupTime.set(LocalDateTime.now());
        } catch (Exception e) {
            log.error("执行已完成跟进记录清理任务失败", e);
        }
        
        log.info("已完成跟进记录清理任务执行完成");
    }
    
    /**
     * 获取待清理的临时客户数量
     * 
     * @return 临时客户数量
     */
    public long getTemporaryClientCount() {
        LocalDateTime threshold = LocalDateTime.now().minusDays(30);
        
        LambdaQueryWrapper<Client> queryWrapper = new LambdaQueryWrapper<Client>()
                .eq(Client::getStatus, ClientConstant.Status.DISABLED)
                .lt(Client::getCreateTime, threshold);
        
        return clientService.count(queryWrapper);
    }
    
    /**
     * 获取待归档的不活跃客户数量
     * 
     * @return 不活跃客户数量
     */
    public long getInactiveClientCount() {
        LocalDateTime threshold = LocalDateTime.now().minusYears(1);
        
        LambdaQueryWrapper<Client> queryWrapper = new LambdaQueryWrapper<Client>()
                .eq(Client::getStatus, ClientConstant.Status.NORMAL)
                .lt(Client::getUpdateTime, threshold);
        
        return clientService.count(queryWrapper);
    }
    
    /**
     * 获取待清理的已完成跟进记录数量
     * 
     * @return 已完成跟进记录数量
     */
    public long getCompletedFollowUpCount() {
        LocalDateTime threshold = LocalDateTime.now().minusDays(90);
        
        LambdaQueryWrapper<ClientFollowUp> queryWrapper = new LambdaQueryWrapper<ClientFollowUp>()
                .eq(ClientFollowUp::getStatus, ClientConstant.FollowUp.STATUS_COMPLETED)
                .lt(ClientFollowUp::getUpdateTime, threshold);
        
        return followUpService.count(queryWrapper);
    }
    
    /**
     * 获取最后一次清理时间
     * 
     * @return 最后一次清理时间
     */
    public LocalDateTime getLastCleanupTime() {
        return lastCleanupTime.get();
    }
}
