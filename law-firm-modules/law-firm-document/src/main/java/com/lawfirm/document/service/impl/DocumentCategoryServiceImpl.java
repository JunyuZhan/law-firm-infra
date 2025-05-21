package com.lawfirm.document.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lawfirm.document.manager.security.SecurityManager;
import com.lawfirm.model.base.service.impl.BaseServiceImpl;
import com.lawfirm.model.document.dto.category.CategoryCreateDTO;
import com.lawfirm.model.document.dto.category.CategoryQueryDTO;
import com.lawfirm.model.document.dto.category.CategoryUpdateDTO;
import com.lawfirm.model.document.entity.base.DocumentCategory;
import com.lawfirm.model.document.mapper.DocumentCategoryMapper;
import com.lawfirm.model.document.service.DocumentCategoryService;
import com.lawfirm.model.document.vo.CategoryVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.lawfirm.document.exception.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import com.lawfirm.model.log.service.AuditService;
import com.lawfirm.core.message.service.MessageSender;

import java.util.List;

/**
 * 文档分类服务实现类
 */
@Slf4j
@Service("documentCategoryServiceImpl")
@ConditionalOnProperty(name = "law-firm.storage.enabled", havingValue = "true", matchIfMissing = true)
public class DocumentCategoryServiceImpl extends BaseServiceImpl<DocumentCategoryMapper, DocumentCategory> implements DocumentCategoryService {

    private final SecurityManager securityManager;
    
    /**
     * 注入core层审计服务，便于后续记录分类操作日志
     */
    @Autowired(required = false)
    @Qualifier("documentAuditService")
    private AuditService auditService;

    /**
     * 注入core层消息发送服务，便于后续分类相关通知等
     */
    @Autowired(required = false)
    @Qualifier("documentMessageSender")
    private MessageSender messageSender;

    public DocumentCategoryServiceImpl(SecurityManager securityManager) {
        this.securityManager = securityManager;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createCategory(CategoryCreateDTO createDTO) {
        if (!securityManager.checkDocumentManagementPermission()) {
            throw DocumentException.noPermission("创建分类");
        }
        DocumentCategory category = new DocumentCategory();
        category.setName(createDTO.getName());
        category.setDescription(createDTO.getDescription());
        category.setParentId(createDTO.getParentId());
        baseMapper.insert(category);
        return category.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateCategory(CategoryUpdateDTO updateDTO) {
        if (!securityManager.checkDocumentManagementPermission()) {
            throw DocumentException.noPermission("更新分类");
        }
        DocumentCategory category = getById(updateDTO.getId());
        if (category == null) {
            throw DocumentException.notFound("分类(ID:" + updateDTO.getId() + ")");
        }
        category.setName(updateDTO.getName());
        category.setDescription(updateDTO.getDescription());
        category.setParentId(updateDTO.getParentId());
        updateById(category);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteCategory(Long id) {
        if (!securityManager.checkDocumentManagementPermission()) {
            throw DocumentException.noPermission("删除分类");
        }
        removeById(id);
    }

    @Override
    public CategoryVO getCategory(Long id) {
        if (!securityManager.checkDocumentManagementPermission()) {
            throw DocumentException.noPermission("查看分类");
        }
        DocumentCategory category = getById(id);
        if (category == null) {
            throw DocumentException.notFound("分类(ID:" + id + ")");
        }
        CategoryVO vo = new CategoryVO();
        vo.setId(category.getId());
        vo.setName(category.getName());
        vo.setDescription(category.getDescription());
        vo.setParentId(category.getParentId());
        return vo;
    }

    @Override
    public List<CategoryVO> listCategories(CategoryQueryDTO queryDTO) {
        if (!securityManager.checkDocumentManagementPermission()) {
            throw DocumentException.noPermission("查询分类列表");
        }
        List<DocumentCategory> categories = baseMapper.selectList(null);
        List<CategoryVO> voList = new java.util.ArrayList<>();
        for (DocumentCategory category : categories) {
            CategoryVO vo = new CategoryVO();
            vo.setId(category.getId());
            vo.setName(category.getName());
            vo.setDescription(category.getDescription());
            vo.setParentId(category.getParentId());
            voList.add(vo);
        }
        return voList;
    }

    @Override
    public Page<CategoryVO> pageCategories(CategoryQueryDTO queryDTO) {
        if (!securityManager.checkDocumentManagementPermission()) {
            throw DocumentException.noPermission("查询分类列表");
        }
        Page<DocumentCategory> page = new Page<>(queryDTO.getPageNum(), queryDTO.getPageSize());
        Page<DocumentCategory> categoryPage = baseMapper.selectPage(page, null);
        Page<CategoryVO> voPage = new Page<>(categoryPage.getCurrent(), categoryPage.getSize(), categoryPage.getTotal());
        List<CategoryVO> voList = new java.util.ArrayList<>();
        for (DocumentCategory category : categoryPage.getRecords()) {
            CategoryVO vo = new CategoryVO();
            vo.setId(category.getId());
            vo.setName(category.getName());
            vo.setDescription(category.getDescription());
            vo.setParentId(category.getParentId());
            voList.add(vo);
        }
        voPage.setRecords(voList);
        return voPage;
    }
}
