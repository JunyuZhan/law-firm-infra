package com.lawfirm.client.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lawfirm.client.constant.CacheConstant;
import com.lawfirm.model.client.entity.common.ClientTag;
import com.lawfirm.model.client.entity.relation.ClientTagRelation;
import com.lawfirm.model.client.mapper.TagMapper;
import com.lawfirm.model.client.mapper.TagRelationMapper;
import com.lawfirm.model.client.service.TagService;
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

import java.util.List;
import java.util.stream.Collectors;

/**
 * 客户标签服务实现类
 */
@Slf4j
@Service("clientTagServiceImpl")
@RequiredArgsConstructor
public class TagServiceImpl extends ServiceImpl<TagMapper, ClientTag> implements TagService {

    private final TagMapper tagMapper;
    private final TagRelationMapper tagRelationMapper;

    /**
     * 注入core层审计服务，便于后续记录标签操作日志
     */
    @Autowired(required = false)
    @Qualifier("clientAuditService")
    private AuditService auditService;

    /**
     * 注入core层消息发送服务，便于后续标签相关通知等
     */
    @Autowired(required = false)
    @Qualifier("clientMessageSender")
    private MessageSender messageSender;

    /**
     * 注入core层文件存储服务，便于后续标签相关附件上传等
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
    @CacheEvict(value = CacheConstant.TAG, allEntries = true)
    public Long createTag(ClientTag tag) {
        // 保存标签
        save(tag);
        return tag.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = CacheConstant.TAG, allEntries = true)
    public void updateTag(ClientTag tag) {
        // 更新标签
        updateById(tag);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = {CacheConstant.TAG, CacheConstant.CLIENT_TAG_RELATION}, allEntries = true)
    public boolean deleteTag(Long id) {
        // 删除标签关联关系
        tagRelationMapper.deleteByTagId(id);
        
        // 删除标签
        return removeById(id);
    }

    @Override
    @Cacheable(value = CacheConstant.TAG, key = "'id:'+#id")
    public ClientTag getTag(Long id) {
        return getById(id);
    }

    @Override
    @Cacheable(value = CacheConstant.TAG, key = "'list:type:'+#tagType")
    public List<ClientTag> listByType(String tagType) {
        return list(new LambdaQueryWrapper<ClientTag>()
                .eq(tagType != null, ClientTag::getTagType, tagType)
                .orderByAsc(ClientTag::getSort));
    }

    @Override
    @Cacheable(value = CacheConstant.TAG, key = "'list:all'")
    public List<ClientTag> listAllTags() {
        return list(new LambdaQueryWrapper<ClientTag>()
                .orderByAsc(ClientTag::getTagType)
                .orderByAsc(ClientTag::getSort));
    }

    @Override
    @Cacheable(value = CacheConstant.CLIENT_TAG_RELATION, key = "'client:'+#clientId")
    public List<ClientTag> getClientTags(Long clientId) {
        // 查询客户标签关联
        List<ClientTagRelation> relations = tagRelationMapper.selectByClientId(clientId);
        
        // 如果没有关联标签，返回空列表
        if (relations.isEmpty()) {
            return List.of();
        }
        
        // 获取标签ID列表
        List<Long> tagIds = relations.stream()
                .map(ClientTagRelation::getTagId)
                .collect(Collectors.toList());
        
        // 查询并返回标签列表
        return listByIds(tagIds);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = CacheConstant.CLIENT_TAG_RELATION, key = "'client:'+#clientId")
    public void addTagToClient(Long clientId, Long tagId) {
        // 检查标签是否存在
        ClientTag tag = getById(tagId);
        if (tag == null) {
            return;
        }
        
        // 检查是否已关联
        ClientTagRelation existingRelation = tagRelationMapper.selectOne(
                new LambdaQueryWrapper<ClientTagRelation>()
                .eq(ClientTagRelation::getClientId, clientId)
                .eq(ClientTagRelation::getTagId, tagId));
        
        if (existingRelation != null) {
            return;
        }
        
        // 创建关联关系
        ClientTagRelation relation = new ClientTagRelation()
                .setClientId(clientId)
                .setTagId(tagId);
        
        tagRelationMapper.insert(relation);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = CacheConstant.CLIENT_TAG_RELATION, key = "'client:'+#clientId")
    public void removeTagFromClient(Long clientId, Long tagId) {
        // 删除关联关系
        tagRelationMapper.deleteByClientIdAndTagId(clientId, tagId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = CacheConstant.CLIENT_TAG_RELATION, key = "'client:'+#clientId")
    public void setClientTags(Long clientId, List<Long> tagIds) {
        // 删除现有关联
        tagRelationMapper.deleteByClientId(clientId);
        
        // 批量添加新关联
        if (tagIds != null && !tagIds.isEmpty()) {
            // 检查标签是否存在
            List<ClientTag> existingTags = listByIds(tagIds);
            List<Long> existingTagIds = existingTags.stream()
                    .map(ClientTag::getId)
                    .collect(Collectors.toList());
            
            // 只添加存在的标签
            for (Long tagId : existingTagIds) {
                ClientTagRelation relation = new ClientTagRelation()
                        .setClientId(clientId)
                        .setTagId(tagId);
                
                tagRelationMapper.insert(relation);
            }
        }
    }
}
