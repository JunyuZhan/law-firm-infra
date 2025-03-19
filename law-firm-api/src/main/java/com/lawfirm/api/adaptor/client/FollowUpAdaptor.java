package com.lawfirm.api.adaptor.client;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lawfirm.api.adaptor.BaseAdaptor;
import com.lawfirm.client.service.FollowUpService;
import com.lawfirm.model.client.entity.follow.ClientFollowUp;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 客户跟进记录适配器
 */
@Component
@RequiredArgsConstructor
public class FollowUpAdaptor extends BaseAdaptor {

    private final FollowUpService followUpService;

    /**
     * 获取客户的跟进记录列表
     */
    public List<ClientFollowUp> getFollowUpList(Long clientId) {
        return followUpService.listByClientId(clientId);
    }

    /**
     * 分页查询跟进记录
     */
    public IPage<ClientFollowUp> getFollowUpPage(Long clientId, Integer status, Integer pageNum, Integer pageSize) {
        Page<ClientFollowUp> page = new Page<>(pageNum, pageSize);
        return followUpService.pageFollowUpRecords(clientId, status, page);
    }

    /**
     * 获取跟进记录详情
     */
    public ClientFollowUp getFollowUpDetail(Long id) {
        return followUpService.getFollowUp(id);
    }

    /**
     * 新增跟进记录
     */
    public Long addFollowUp(ClientFollowUp followUp) {
        return followUpService.addFollowUp(followUp);
    }

    /**
     * 修改跟进记录
     */
    public void updateFollowUp(ClientFollowUp followUp) {
        followUpService.updateFollowUp(followUp);
    }

    /**
     * 删除跟进记录
     */
    public void deleteFollowUp(Long id) {
        followUpService.deleteFollowUp(id);
    }

    /**
     * 完成跟进任务
     */
    public void completeFollowUp(Long id, String result) {
        followUpService.completeFollowUp(id, result);
    }

    /**
     * 取消跟进任务
     */
    public void cancelFollowUp(Long id, String reason) {
        followUpService.cancelFollowUp(id, reason);
    }

    /**
     * 获取待处理的跟进任务
     */
    public List<ClientFollowUp> getPendingFollowUps(LocalDateTime startTime, LocalDateTime endTime) {
        return followUpService.listPendingFollowUps(startTime, endTime);
    }
} 