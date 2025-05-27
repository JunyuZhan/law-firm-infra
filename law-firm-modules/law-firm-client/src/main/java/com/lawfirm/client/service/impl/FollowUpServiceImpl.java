package com.lawfirm.client.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lawfirm.client.constant.CacheConstant;
import com.lawfirm.client.constant.ClientModuleConstant;
import com.lawfirm.model.client.constant.ClientConstant;
import com.lawfirm.model.client.entity.base.Client;
import com.lawfirm.model.client.entity.follow.ClientFollowUp;
import com.lawfirm.model.client.mapper.ClientMapper;
import com.lawfirm.model.client.mapper.FollowUpMapper;
import com.lawfirm.model.client.service.FollowUpService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import com.lawfirm.model.log.service.AuditService;
import com.lawfirm.core.message.service.MessageSender;
import com.lawfirm.model.storage.service.FileService;
import com.lawfirm.model.storage.service.BucketService;
import com.lawfirm.model.search.service.SearchService;
import com.lawfirm.model.workflow.service.ProcessService;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

/**
 * 客户跟进服务实现类
 */
@Slf4j
@Service("clientFollowUpServiceImpl")
@RequiredArgsConstructor
public class FollowUpServiceImpl extends ServiceImpl<FollowUpMapper, ClientFollowUp> implements FollowUpService {

    private final FollowUpMapper followUpMapper;
    private final ClientMapper clientMapper;

    /**
     * 注入core层审计服务，便于后续记录跟进操作日志
     */
    @Autowired(required = false)
    @Qualifier("clientAuditService")
    private AuditService auditService;

    /**
     * 注入core层消息发送服务，便于后续跟进通知等
     */
    @Autowired(required = false)
    @Qualifier("clientMessageSender")
    private MessageSender messageSender;

    /**
     * 注入core层文件存储服务，便于后续跟进附件上传等
     */
    @Autowired(required = false)
    @Qualifier("clientFileService")
    private FileService fileService;

    /**
     * 注入core层存储桶服务
     */
    @Autowired(required = false)
    @Qualifier("clientBucketService")
    private BucketService bucketService;

    /**
     * 注入core层搜索服务
     */
    @Autowired(required = false)
    @Qualifier("clientSearchService")
    private SearchService searchService;

    /**
     * 注入core层流程服务
     */
    @Autowired(required = false)
    @Qualifier("clientProcessService")
    private ProcessService processService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = CacheConstant.CLIENT_FOLLOW_UP, key = "'client:'+#followUp.clientId")
    public Long addFollowUp(ClientFollowUp followUp) {
        // 检查客户是否存在
        Client client = clientMapper.selectById(followUp.getClientId());
        if (client == null) {
            throw new IllegalArgumentException("客户不存在");
        }
        
        // 设置默认值
        if (followUp.getStatus() == null) {
            followUp.setStatus(ClientConstant.FollowUp.STATUS_PENDING);
        }
        
        // 保存跟进记录
        save(followUp);
        return followUp.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = CacheConstant.CLIENT_FOLLOW_UP, key = "'client:'+#followUp.clientId")
    public void updateFollowUp(ClientFollowUp followUp) {
        // 禁止修改客户ID
        followUp.setClientId(null);
        updateById(followUp);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = CacheConstant.CLIENT_FOLLOW_UP, allEntries = true)
    public void deleteFollowUp(Long id) {
        removeById(id);
    }

    @Override
    @Cacheable(value = CacheConstant.CLIENT_FOLLOW_UP, key = "'id:'+#id")
    public ClientFollowUp getFollowUp(Long id) {
        return getById(id);
    }

    @Override
    @Cacheable(value = CacheConstant.CLIENT_FOLLOW_UP, key = "'client:'+#clientId")
    public List<ClientFollowUp> listByClientId(Long clientId) {
        return list(new LambdaQueryWrapper<ClientFollowUp>()
                .eq(ClientFollowUp::getClientId, clientId)
                .orderByDesc(ClientFollowUp::getCreateTime));
    }

    /**
     * 分页查询跟进记录
     */
    public IPage<ClientFollowUp> pageFollowUpRecords(Long clientId, Integer status, Page<ClientFollowUp> page) {
        return page(page, new LambdaQueryWrapper<ClientFollowUp>()
                .eq(clientId != null, ClientFollowUp::getClientId, clientId)
                .eq(status != null, ClientFollowUp::getStatus, status)
                .orderByDesc(ClientFollowUp::getCreateTime));
    }

    /**
     * 创建默认跟进计划
     */
    @Transactional(rollbackFor = Exception.class)
    public void createDefaultFollowUpPlan(Long clientId, Long assignee) {
        // 检查客户是否存在
        Client client = clientMapper.selectById(clientId);
        if (client == null) {
            return;
        }
        
        // 创建默认跟进计划
        ClientFollowUp followUpPlan = new ClientFollowUp();
        followUpPlan.setClientId(clientId);
        followUpPlan.setAssigneeId(assignee);
        followUpPlan.setStatus(ClientConstant.FollowUp.STATUS_PENDING);
        
        // 根据客户级别设置不同的跟进周期
        int followCycleDays = ClientModuleConstant.Process.DEFAULT_FOLLOW_CYCLE_DAYS;
        if (client.getClientLevel() != null) {
            if (client.getClientLevel() == ClientConstant.ClientLevel.VIP) {
                followCycleDays = ClientModuleConstant.Process.VIP_FOLLOW_CYCLE_DAYS;
            } else if (client.getClientLevel() == ClientConstant.ClientLevel.CORE) {
                followCycleDays = ClientModuleConstant.Process.CORE_FOLLOW_CYCLE_DAYS;
            }
        }
        
        followUpPlan.setNextFollowTime(Date.from(LocalDateTime.now().plusDays(followCycleDays).atZone(ZoneId.systemDefault()).toInstant()));
        followUpPlan.setContent("常规跟进");
        
        // 保存跟进计划
        save(followUpPlan);
    }

    @Override
    public List<ClientFollowUp> listPendingFollowUps(LocalDateTime startTime, LocalDateTime endTime) {
        return list(new LambdaQueryWrapper<ClientFollowUp>()
                .eq(ClientFollowUp::getStatus, ClientConstant.FollowUp.STATUS_PENDING)
                .ge(startTime != null, ClientFollowUp::getNextFollowTime, startTime)
                .le(endTime != null, ClientFollowUp::getNextFollowTime, endTime));
    }

    @Override
    public List<ClientFollowUp> listUserFollowUps(Long userId, LocalDateTime startTime, LocalDateTime endTime) {
        return list(new LambdaQueryWrapper<ClientFollowUp>()
                .eq(ClientFollowUp::getStatus, ClientConstant.FollowUp.STATUS_PENDING)
                .eq(ClientFollowUp::getAssigneeId, userId)
                .ge(startTime != null, ClientFollowUp::getNextFollowTime, startTime)
                .le(endTime != null, ClientFollowUp::getNextFollowTime, endTime)
                .orderByAsc(ClientFollowUp::getNextFollowTime));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void completeFollowUp(Long id, String result) {
        // 获取跟进记录
        ClientFollowUp followUp = getById(id);
        if (followUp == null || followUp.getStatus() != ClientConstant.FollowUp.STATUS_PENDING) {
            return;
        }
        
        // 更新状态为已完成
        followUp.setStatus(ClientConstant.FollowUp.STATUS_COMPLETED);
        followUp.setResult(result);
        followUp.setUpdateTime(LocalDateTime.now());
        updateById(followUp);
    }

    /**
     * 完成跟进并创建下一次跟进
     */
    @Transactional(rollbackFor = Exception.class)
    public void completeFollowUp(Long id, String content, String result, LocalDateTime nextFollowUpTime, String nextFollowUpContent) {
        // 获取跟进记录
        ClientFollowUp followUp = getById(id);
        if (followUp == null || followUp.getStatus() != ClientConstant.FollowUp.STATUS_PENDING) {
            return;
        }
        
        // 更新状态为已完成
        followUp.setStatus(ClientConstant.FollowUp.STATUS_COMPLETED);
        followUp.setContent(content);
        followUp.setResult(result);
        followUp.setUpdateTime(LocalDateTime.now());
        updateById(followUp);
        
        // 创建下一次跟进计划
        if (nextFollowUpTime != null) {
            ClientFollowUp nextFollowUp = new ClientFollowUp();
            nextFollowUp.setClientId(followUp.getClientId());
            nextFollowUp.setAssigneeId(followUp.getAssigneeId());
            nextFollowUp.setStatus(ClientConstant.FollowUp.STATUS_PENDING);
            nextFollowUp.setNextFollowTime(Date.from(nextFollowUpTime.atZone(ZoneId.systemDefault()).toInstant()));
            nextFollowUp.setContent(nextFollowUpContent);
            nextFollowUp.setBusinessId(followUp.getBusinessId());
            nextFollowUp.setBusinessType(followUp.getBusinessType());
            
            save(nextFollowUp);
        }
    }

    /**
     * 取消跟进任务
     */
    @Transactional(rollbackFor = Exception.class)
    public void cancelFollowUp(Long id, String reason) {
        // 获取跟进记录
        ClientFollowUp followUp = getById(id);
        if (followUp == null || followUp.getStatus() != ClientConstant.FollowUp.STATUS_PENDING) {
            return;
        }
        
        // 更新状态为已取消
        followUp.setStatus(ClientConstant.FollowUp.STATUS_CANCELED);
        followUp.setResult(reason);
        followUp.setUpdateTime(LocalDateTime.now());
        updateById(followUp);
    }
}
