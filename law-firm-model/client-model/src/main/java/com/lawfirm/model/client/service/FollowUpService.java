package com.lawfirm.model.client.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lawfirm.model.client.entity.follow.ClientFollowUp;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 客户跟进服务接口
 */
public interface FollowUpService extends IService<ClientFollowUp> {
    
    /**
     * 添加跟进记录
     * @param followUp 跟进记录
     * @return 跟进ID
     */
    Long addFollowUp(ClientFollowUp followUp);
    
    /**
     * 更新跟进记录
     * @param followUp 跟进记录
     */
    void updateFollowUp(ClientFollowUp followUp);
    
    /**
     * 删除跟进记录
     * @param id 跟进ID
     */
    void deleteFollowUp(Long id);
    
    /**
     * 获取跟进记录详情
     * @param id 跟进ID
     * @return 跟进记录
     */
    ClientFollowUp getFollowUp(Long id);
    
    /**
     * 获取客户的跟进记录列表
     * @param clientId 客户ID
     * @return 跟进记录列表
     */
    List<ClientFollowUp> listByClientId(Long clientId);
    
    /**
     * 获取待处理的跟进任务列表
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 跟进任务列表
     */
    List<ClientFollowUp> listPendingFollowUps(LocalDateTime startTime, LocalDateTime endTime);
    
    /**
     * 获取用户的跟进任务列表
     * @param userId 用户ID
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 跟进任务列表
     */
    List<ClientFollowUp> listUserFollowUps(Long userId, LocalDateTime startTime, LocalDateTime endTime);
    
    /**
     * 完成跟进任务
     * @param id 跟进ID
     * @param result 跟进结果
     */
    void completeFollowUp(Long id, String result);
} 