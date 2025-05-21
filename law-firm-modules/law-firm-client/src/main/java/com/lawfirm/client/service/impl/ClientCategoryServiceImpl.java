package com.lawfirm.client.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lawfirm.client.constant.CacheConstant;
import com.lawfirm.model.client.entity.common.ClientCategory;
import com.lawfirm.model.client.mapper.CategoryMapper;
import com.lawfirm.model.client.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import com.lawfirm.model.log.service.AuditService;
import com.lawfirm.core.message.service.MessageSender;
import com.lawfirm.model.storage.service.FileService;
import com.lawfirm.model.storage.service.BucketService;
import com.lawfirm.model.search.service.SearchService;
import com.lawfirm.model.workflow.service.ProcessService;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 客户分类服务实现
 */
@Slf4j
@Service("clientCategoryServiceImpl")
@RequiredArgsConstructor
public class ClientCategoryServiceImpl extends ServiceImpl<CategoryMapper, ClientCategory> implements CategoryService {

    private final CategoryMapper categoryMapper;

    /**
     * 注入core层审计服务，便于后续记录分类操作日志
     */
    @Autowired(required = false)
    @Qualifier("clientAuditService")
    private AuditService auditService;

    /**
     * 注入core层消息发送服务，便于后续分类相关通知等
     */
    @Autowired(required = false)
    @Qualifier("clientMessageSender")
    private MessageSender messageSender;

    /**
     * 注入core层文件存储服务，便于后续分类相关附件上传等
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
    @CacheEvict(value = CacheConstant.CATEGORY, allEntries = true)
    public Long createCategory(ClientCategory category) {
        // 设置父分类路径
        if (category.getParentId() != null && category.getParentId() > 0) {
            ClientCategory parent = getById(category.getParentId());
            if (parent != null) {
                category.setCategoryPath(parent.getCategoryPath() + parent.getId() + ",");
                category.setLevel(parent.getLevel() + 1);
            } else {
                category.setCategoryPath(",");
                category.setLevel(1);
            }
        } else {
            category.setParentId(0L);
            category.setCategoryPath(",");
            category.setLevel(1);
        }

        // 保存分类
        save(category);
        return category.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = CacheConstant.CATEGORY, allEntries = true)
    public void updateCategory(ClientCategory category) {
        // 禁止修改父分类，避免形成环路
        category.setParentId(null);
        category.setCategoryPath(null);
        category.setLevel(null);
        
        // 更新分类
        updateById(category);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = CacheConstant.CATEGORY, allEntries = true)
    public boolean deleteCategory(Long id) {
        // 查询是否有子分类
        Long count = count(new LambdaQueryWrapper<ClientCategory>()
                .eq(ClientCategory::getParentId, id));
        if (count > 0) {
            // 存在子分类，不允许删除
            return false;
        }
        
        // 查询是否有关联的客户
        Long clientCount = baseMapper.countClientsByCategoryId(id);
        if (clientCount != null && clientCount > 0) {
            // 存在客户关联此分类，不允许删除
            return false;
        }
        
        // 删除分类
        removeById(id);
        return true;
    }

    @Override
    @Cacheable(value = CacheConstant.CATEGORY, key = "'id:'+#id")
    public ClientCategory getCategory(Long id) {
        return getById(id);
    }

    @Override
    @Cacheable(value = CacheConstant.CATEGORY, key = "'list:parent:'+#parentId")
    public List<ClientCategory> listByParentId(Long parentId) {
        return list(new LambdaQueryWrapper<ClientCategory>()
                .eq(ClientCategory::getParentId, parentId)
                .orderByAsc(ClientCategory::getSortWeight));
    }

    @Override
    @Cacheable(value = CacheConstant.CATEGORY, key = "'tree'")
    public List<ClientCategory> getCategoryTree() {
        // 获取所有分类
        List<ClientCategory> allCategories = list(new LambdaQueryWrapper<ClientCategory>()
                .orderByAsc(ClientCategory::getSortWeight));
        
        // 构建树形结构
        List<ClientCategory> rootCategories = allCategories.stream()
                .filter(category -> Objects.equals(category.getParentId(), 0L))
                .collect(Collectors.toList());
        
        // 递归构建子树
        rootCategories.forEach(root -> buildChildrenTree(root, allCategories));
        
        return rootCategories;
    }

    @Override
    @Cacheable(value = CacheConstant.CATEGORY, key = "'children:'+#id")
    public List<Long> getAllChildrenIds(Long id) {
        List<Long> childrenIds = new ArrayList<>();
        
        // 获取直接子分类
        List<ClientCategory> children = list(new LambdaQueryWrapper<ClientCategory>()
                .eq(ClientCategory::getParentId, id));
        
        // 添加子分类ID
        children.forEach(child -> {
            childrenIds.add(child.getId());
            childrenIds.addAll(getAllChildrenIds(child.getId()));
        });
        
        return childrenIds;
    }
    
    /**
     * 递归构建子树
     * @param parent 父节点
     * @param allCategories 所有分类
     */
    private void buildChildrenTree(ClientCategory parent, List<ClientCategory> allCategories) {
        List<ClientCategory> children = allCategories.stream()
                .filter(category -> Objects.equals(category.getParentId(), parent.getId()))
                .collect(Collectors.toList());
        
        if (!children.isEmpty()) {
            // 暂存子分类列表，ClientCategory可能没有children字段
            // parent.setChildren(children);
            children.forEach(child -> buildChildrenTree(child, allCategories));
        }
    }
}
