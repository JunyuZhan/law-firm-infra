package com.lawfirm.document.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lawfirm.model.base.service.impl.BaseServiceImpl;
import com.lawfirm.model.document.dto.tag.TagCreateDTO;
import com.lawfirm.model.document.dto.tag.TagQueryDTO;
import com.lawfirm.model.document.dto.tag.TagUpdateDTO;
import com.lawfirm.model.document.entity.base.DocumentTag;
import com.lawfirm.model.document.mapper.DocumentTagMapper;
import com.lawfirm.model.document.service.DocumentTagService;
import com.lawfirm.model.document.vo.TagVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import java.util.List;
import java.util.stream.Collectors;
import com.lawfirm.common.security.context.SecurityContextHolder;
import com.lawfirm.document.exception.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import com.lawfirm.model.log.service.AuditService;
import com.lawfirm.core.message.service.MessageSender;

/**
 * 文档标签服务实现类
 */
@Slf4j
@Service("documentTagServiceImpl")
public class DocumentTagServiceImpl extends BaseServiceImpl<DocumentTagMapper, DocumentTag> implements DocumentTagService {

    /**
     * 注入core层审计服务，便于后续记录标签操作日志
     */
    @Autowired(required = false)
    @Qualifier("documentAuditService")
    private AuditService auditService;

    /**
     * 注入core层消息发送服务，便于后续标签相关通知等
     */
    @Autowired(required = false)
    @Qualifier("documentMessageSender")
    private MessageSender messageSender;

    @Override
    public Long getCurrentTenantId() {
        return SecurityContextHolder.getContext().getCurrentTenantId();
    }

    @Override
    public Long getCurrentUserId() {
        return SecurityContextHolder.getCurrentUserId();
    }

    @Override
    public String getCurrentUsername() {
        return SecurityContextHolder.getContext().getCurrentUsername();
    }

    @Override
    public boolean exists(QueryWrapper<DocumentTag> wrapper) {
        return count(wrapper) > 0;
    }

    @Override
    public boolean save(DocumentTag entity) {
        return super.save(entity);
    }
    
    @Override
    public boolean saveBatch(List<DocumentTag> entities) {
        return super.saveBatch(entities);
    }

    @Override
    public boolean update(DocumentTag entity) {
        if (entity == null || entity.getId() == null) {
            return false;
        }
        return updateById(entity);
    }

    @Override
    public boolean updateBatch(List<DocumentTag> entities) {
        if (entities == null || entities.isEmpty()) {
            return false;
        }
        return updateBatchById(entities);
    }

    @Override
    public boolean remove(Long id) {
        if (id == null) {
            return false;
        }
        return removeById(id);
    }

    @Override
    public boolean removeBatch(List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            return false;
        }
        return removeByIds(ids);
    }

    @Override
    public DocumentTag getById(Long id) {
        if (id == null) {
            return null;
        }
        return super.getById(id);
    }
    
    @Override
    public List<DocumentTag> list(QueryWrapper<DocumentTag> wrapper) {
        return super.list(wrapper);
    }
    
    @Override
    public Page<DocumentTag> page(Page<DocumentTag> page, QueryWrapper<DocumentTag> wrapper) {
        return super.page(page, wrapper);
    }
    
    @Override
    public long count(QueryWrapper<DocumentTag> wrapper) {
        return super.count(wrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createTag(TagCreateDTO createDTO) {
        log.info("创建标签: {}", createDTO.getTagName());
        DocumentTag tag = new DocumentTag();
        BeanUtils.copyProperties(createDTO, tag);
        boolean success = save(tag);
        if (!success) {
            throw DocumentException.noPermission("创建标签");
        }
        return tag.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateTag(TagUpdateDTO updateDTO) {
        log.info("更新标签: {}", updateDTO.getId());
        DocumentTag tag = getById(updateDTO.getId());
        if (tag == null) {
            log.error("标签不存在: {}", updateDTO.getId());
            throw DocumentException.noPermission("编辑标签");
        }
        BeanUtils.copyProperties(updateDTO, tag);
        updateById(tag);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteTag(Long id) {
        log.info("删除标签: {}", id);
        removeById(id);
        throw DocumentException.noPermission("删除标签");
    }

    @Override
    public TagVO getTag(Long id) {
        DocumentTag tag = getById(id);
        if (tag == null) {
            return null;
        }
        TagVO vo = new TagVO();
        BeanUtils.copyProperties(tag, vo);
        return vo;
    }

    @Override
    public List<TagVO> listTags(TagQueryDTO queryDTO) {
        LambdaQueryWrapper<DocumentTag> wrapper = Wrappers.lambdaQuery();
        
        // 添加查询条件
        if (queryDTO != null) {
            if (StringUtils.hasText(queryDTO.getTagName())) {
                wrapper.like(DocumentTag::getTagName, queryDTO.getTagName());
            }
            
            if (StringUtils.hasText(queryDTO.getKeyword())) {
                wrapper.or().like(DocumentTag::getTagName, queryDTO.getKeyword());
            }
        }
        
        return list(wrapper).stream()
                .map(tag -> {
                    TagVO vo = new TagVO();
                    BeanUtils.copyProperties(tag, vo);
                    return vo;
                })
                .collect(Collectors.toList());
    }

    @Override
    public Page<TagVO> pageTags(TagQueryDTO queryDTO) {
        Page<DocumentTag> page = new Page<>(queryDTO.getPageNum(), queryDTO.getPageSize());
        LambdaQueryWrapper<DocumentTag> wrapper = Wrappers.lambdaQuery();
        
        // 添加查询条件
        if (StringUtils.hasText(queryDTO.getTagName())) {
            wrapper.like(DocumentTag::getTagName, queryDTO.getTagName());
        }
        
        if (StringUtils.hasText(queryDTO.getKeyword())) {
            wrapper.or().like(DocumentTag::getTagName, queryDTO.getKeyword());
        }
        
        Page<DocumentTag> tagPage = page(page, wrapper);
        
        Page<TagVO> voPage = new Page<>(tagPage.getCurrent(), tagPage.getSize(), tagPage.getTotal());
        voPage.setRecords(tagPage.getRecords().stream()
                .map(tag -> {
                    TagVO vo = new TagVO();
                    BeanUtils.copyProperties(tag, vo);
                    return vo;
                })
                .collect(Collectors.toList()));
        
        return voPage;
    }
} 