package com.lawfirm.document.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lawfirm.document.dto.request.DocumentVersionAddRequest;
import com.lawfirm.document.dto.response.DocumentVersionResponse;
import com.lawfirm.document.entity.Document;
import com.lawfirm.document.entity.DocumentVersion;
import com.lawfirm.document.mapper.DocumentVersionMapper;
import com.lawfirm.document.service.IDocumentService;
import com.lawfirm.document.service.IDocumentVersionService;
import com.lawfirm.common.core.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 文档版本服务实现类
 */
@Service
@RequiredArgsConstructor
public class DocumentVersionServiceImpl extends ServiceImpl<DocumentVersionMapper, DocumentVersion> implements IDocumentVersionService {

    private final IDocumentService documentService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long addVersion(DocumentVersionAddRequest request) {
        // 检查文档是否存在
        Document document = documentService.getById(request.getDocId());
        if (document == null) {
            throw new BusinessException("文档不存在");
        }
        
        // 获取最新版本号
        Integer latestVersionNo = getLatestVersionNo(request.getDocId());
        
        // 创建新版本
        DocumentVersion version = new DocumentVersion();
        BeanUtils.copyProperties(request, version);
        version.setVersionNo(latestVersionNo + 1);
        save(version);
        
        // 更新文档当前版本号
        document.setVersion(version.getVersionNo());
        documentService.updateById(document);
        
        return version.getId();
    }

    @Override
    public List<DocumentVersionResponse> getVersionsByDocId(Long docId) {
        // 检查文档是否存在
        if (!documentService.getById(docId)) {
            throw new BusinessException("文档不存在");
        }
        
        // 查询所有版本
        LambdaQueryWrapper<DocumentVersion> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(DocumentVersion::getDocId, docId)
                .orderByDesc(DocumentVersion::getVersionNo);
        
        return list(wrapper).stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public DocumentVersionResponse getVersion(Long docId, Integer versionNo) {
        // 查询指定版本
        LambdaQueryWrapper<DocumentVersion> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(DocumentVersion::getDocId, docId)
                .eq(DocumentVersion::getVersionNo, versionNo);
        
        DocumentVersion version = getOne(wrapper);
        if (version == null) {
            throw new BusinessException("指定版本不存在");
        }
        
        return convertToResponse(version);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void rollbackToVersion(Long docId, Integer versionNo) {
        // 检查文档是否存在
        Document document = documentService.getById(docId);
        if (document == null) {
            throw new BusinessException("文档不存在");
        }
        
        // 检查版本是否存在
        DocumentVersion version = getVersion(docId, versionNo);
        if (version == null) {
            throw new BusinessException("指定版本不存在");
        }
        
        // 更新文档信息
        document.setVersion(versionNo);
        document.setFileSize(version.getFileSize());
        document.setStoragePath(version.getStoragePath());
        documentService.updateById(document);
    }

    /**
     * 获取最新版本号
     */
    private Integer getLatestVersionNo(Long docId) {
        LambdaQueryWrapper<DocumentVersion> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(DocumentVersion::getDocId, docId)
                .orderByDesc(DocumentVersion::getVersionNo)
                .last("LIMIT 1");
        
        DocumentVersion latestVersion = getOne(wrapper);
        return latestVersion != null ? latestVersion.getVersionNo() : 0;
    }

    /**
     * 转换为响应对象
     */
    private DocumentVersionResponse convertToResponse(DocumentVersion version) {
        if (version == null) {
            return null;
        }
        DocumentVersionResponse response = new DocumentVersionResponse();
        BeanUtils.copyProperties(version, response);
        return response;
    }
} 