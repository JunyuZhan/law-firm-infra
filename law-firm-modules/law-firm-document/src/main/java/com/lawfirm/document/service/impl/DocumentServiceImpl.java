package com.lawfirm.document.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lawfirm.common.util.BeanUtils;
import com.lawfirm.document.manager.storage.DocumentStorageManager;
import com.lawfirm.document.manager.search.DocumentSearchManager;
import com.lawfirm.document.manager.security.DocumentSecurityManager;
import com.lawfirm.model.base.service.impl.BaseServiceImpl;
import com.lawfirm.model.document.dto.document.DocumentCreateDTO;
import com.lawfirm.model.document.dto.document.DocumentQueryDTO;
import com.lawfirm.model.document.dto.document.DocumentUpdateDTO;
import com.lawfirm.model.document.entity.base.BaseDocument;
import com.lawfirm.model.document.mapper.DocumentMapper;
import com.lawfirm.model.document.service.DocumentService;
import com.lawfirm.model.document.vo.DocumentVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 文档服务实现类
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class DocumentServiceImpl extends BaseServiceImpl<DocumentMapper, BaseDocument> implements DocumentService {

    private final DocumentStorageManager storageManager;
    private final DocumentSearchManager searchManager;
    private final DocumentSecurityManager securityManager;

    @Override
    public Long createDocument(DocumentCreateDTO createDTO, InputStream inputStream) {
        try {
            // 1. 创建文档记录
            BaseDocument document = BeanUtils.copyProperties(createDTO, BaseDocument.class);
            baseMapper.insert(document);

            // 2. 上传文件内容
            storageManager.uploadDocument(document.getId(), inputStream);

            return document.getId();
        } catch (IOException e) {
            log.error("创建文档失败", e);
            throw new RuntimeException("创建文档失败", e);
        }
    }

    @Override
    public void updateDocument(Long id, DocumentUpdateDTO updateDTO) {
        // 1. 检查权限
        BaseDocument document = baseMapper.selectById(id);
        if (!securityManager.checkAccessPermission(document)) {
            throw new SecurityException("没有修改权限");
        }

        // 2. 更新文档
        BeanUtils.copyPropertiesIgnoreNull(updateDTO, document);
        baseMapper.updateById(document);
    }

    @Override
    public void updateDocumentContent(Long id, InputStream inputStream) {
        try {
            BaseDocument document = baseMapper.selectById(id);
            if (document != null) {
                storageManager.updateDocument(id, inputStream);
            }
        } catch (IOException e) {
            log.error("更新文档内容失败", e);
            throw new RuntimeException("更新文档内容失败", e);
        }
    }

    @Override
    public void deleteDocument(Long id) {
        // 1. 检查权限
        BaseDocument document = baseMapper.selectById(id);
        if (!securityManager.checkAccessPermission(document)) {
            throw new SecurityException("没有删除权限");
        }

        // 2. 删除文件
        storageManager.deleteDocument(id);

        // 3. 删除文档记录
        baseMapper.deleteById(id);
    }

    @Override
    public void deleteDocuments(List<Long> ids) {
        for (Long id : ids) {
            deleteDocument(id);
        }
    }

    @Override
    public DocumentVO getDocumentById(Long id) {
        // 1. 获取文档
        BaseDocument document = baseMapper.selectById(id);
        if (document == null) {
            return null;
        }

        // 2. 检查权限
        if (!securityManager.checkAccessPermission(document)) {
            throw new SecurityException("没有访问权限");
        }

        // 3. 转换为VO
        return BeanUtils.copyProperties(document, DocumentVO.class);
    }

    @Override
    public Page<DocumentVO> pageDocuments(Page<BaseDocument> page, DocumentQueryDTO queryDTO) {
        // 1. 构建查询条件
        LambdaQueryWrapper<BaseDocument> wrapper = new LambdaQueryWrapper<>();
        // TODO: 添加查询条件

        // 2. 执行分页查询
        Page<BaseDocument> result = baseMapper.selectPage(page, wrapper);

        // 3. 转换为VO
        Page<DocumentVO> voPage = new Page<>(result.getCurrent(), result.getSize(), result.getTotal());
        voPage.setRecords(result.getRecords().stream()
            .map(doc -> BeanUtils.copyProperties(doc, DocumentVO.class))
            .collect(Collectors.toList()));
        return voPage;
    }

    @Override
    public InputStream downloadDocument(Long id) {
        try {
            // 1. 检查权限
            BaseDocument document = baseMapper.selectById(id);
            if (!securityManager.checkAccessPermission(document)) {
                throw new SecurityException("没有下载权限");
            }

            // 2. 下载文件
            return storageManager.downloadDocument(id);
        } catch (IOException e) {
            log.error("下载文档失败", e);
            throw new RuntimeException("下载文档失败", e);
        }
    }

    @Override
    public String previewDocument(Long id) {
        BaseDocument document = baseMapper.selectById(id);
        if (document != null && securityManager.checkAccessPermission(document)) {
            return storageManager.previewDocument(id);
        }
        return null;
    }

    @Override
    public String getDocumentUrl(Long id) {
        BaseDocument document = baseMapper.selectById(id);
        if (document != null && securityManager.checkAccessPermission(document)) {
            return storageManager.getDocumentUrl(id);
        }
        return null;
    }

    @Override
    public String getDocumentUrl(Long id, Long expireTime) {
        BaseDocument document = baseMapper.selectById(id);
        if (document != null && securityManager.checkAccessPermission(document)) {
            return storageManager.getDocumentUrl(id, expireTime);
        }
        return null;
    }

    @Override
    public void updateStatus(Long id, String status) {
        BaseDocument document = baseMapper.selectById(id);
        if (document != null) {
            document.setDocStatus(status);
            baseMapper.updateById(document);
        }
    }

    @Override
    public List<DocumentVO> listDocumentsByBusiness(Long businessId, String businessType) {
        LambdaQueryWrapper<BaseDocument> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(businessId != null, BaseDocument::getBusinessId, businessId)
               .eq(businessType != null, BaseDocument::getBusinessType, businessType);
        List<BaseDocument> documents = baseMapper.selectList(wrapper);
        return documents.stream()
            .map(doc -> BeanUtils.copyProperties(doc, DocumentVO.class))
            .collect(Collectors.toList());
    }

    @Override
    public List<DocumentVO> listDocumentsByType(String docType) {
        LambdaQueryWrapper<BaseDocument> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(BaseDocument::getDocType, docType);
        return baseMapper.selectList(wrapper).stream()
            .map(doc -> BeanUtils.copyProperties(doc, DocumentVO.class))
            .collect(Collectors.toList());
    }

    @Override
    public void refreshCache() {
        // TODO: 实现缓存刷新逻辑
    }
}
