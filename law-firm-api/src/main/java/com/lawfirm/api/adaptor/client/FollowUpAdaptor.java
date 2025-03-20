package com.lawfirm.api.adaptor.client;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lawfirm.api.adaptor.BaseAdaptor;
import com.lawfirm.model.client.service.FollowUpService;
import com.lawfirm.model.client.entity.follow.ClientFollowUp;
import com.lawfirm.model.client.vo.follow.ClientFollowUpVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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
    public List<ClientFollowUpVO> getFollowUpList(Long clientId) {
        List<ClientFollowUp> followUps = followUpService.listByClientId(clientId);
        return followUps.stream()
                .map(followUp -> convert(followUp, ClientFollowUpVO.class))
                .collect(Collectors.toList());
    }

    /**
     * 分页查询跟进记录
     */
    public IPage<ClientFollowUpVO> getFollowUpPage(Long clientId, Integer status, Integer pageNum, Integer pageSize) {
        Page<ClientFollowUp> page = new Page<>(pageNum, pageSize);
        IPage<ClientFollowUp> followUpPage = followUpService.page(page);
        return followUpPage.convert(followUp -> convert(followUp, ClientFollowUpVO.class));
    }

    /**
     * 获取跟进记录详情
     */
    public ClientFollowUpVO getFollowUpDetail(Long id) {
        ClientFollowUp followUp = followUpService.getFollowUp(id);
        return convert(followUp, ClientFollowUpVO.class);
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
     * 获取待处理的跟进任务
     */
    public List<ClientFollowUpVO> getPendingFollowUps(LocalDateTime startTime, LocalDateTime endTime) {
        List<ClientFollowUp> followUps = followUpService.listPendingFollowUps(startTime, endTime);
        return followUps.stream()
                .map(followUp -> convert(followUp, ClientFollowUpVO.class))
                .collect(Collectors.toList());
    }

    /**
     * 获取用户的跟进任务列表
     */
    public List<ClientFollowUpVO> getUserFollowUps(Long userId, LocalDateTime startTime, LocalDateTime endTime) {
        List<ClientFollowUp> followUps = followUpService.listUserFollowUps(userId, startTime, endTime);
        return followUps.stream()
                .map(followUp -> convert(followUp, ClientFollowUpVO.class))
                .collect(Collectors.toList());
    }
} 