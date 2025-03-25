package com.lawfirm.cases.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lawfirm.cases.core.audit.CaseAuditProvider;
import com.lawfirm.cases.core.message.CaseMessageManager;
import com.lawfirm.model.cases.dto.business.CaseDocumentDTO;
import com.lawfirm.model.cases.entity.business.CaseDocument;
import com.lawfirm.model.cases.mapper.business.CaseDocumentMapper;
import com.lawfirm.model.cases.service.business.CaseDocumentService;
import com.lawfirm.model.cases.vo.business.CaseDocumentVO;
import com.lawfirm.model.document.dto.document.DocumentCreateDTO;
import com.lawfirm.model.document.dto.document.DocumentQueryDTO;
import com.lawfirm.model.document.dto.document.DocumentUpdateDTO;
import com.lawfirm.model.document.service.DocumentService;
import com.lawfirm.model.document.vo.DocumentVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 案件文档服务实现类
 */
@Slf4j
@Service("caseDocumentServiceImpl")
@RequiredArgsConstructor
public class CaseDocumentServiceImpl implements CaseDocumentService {

    private final CaseDocumentMapper documentMapper;
    private final CaseAuditProvider auditProvider;
    private final CaseMessageManager messageManager;
    private final DocumentService documentService; // 通用文档服务

    private static final String BUSINESS_TYPE = "CASE_DOCUMENT";

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long uploadDocument(CaseDocumentDTO documentDTO) {
        log.info("上传文档: 案件ID={}, 文档名称={}", documentDTO.getCaseId(), documentDTO.getDocumentName());

        // 1. 创建文档实体
        CaseDocument document = new CaseDocument();
        BeanUtils.copyProperties(documentDTO, document);
        document.setDocumentStatus(1); // 初始状态：正常
        document.setCreateTime(LocalDateTime.now());
        document.setUpdateTime(LocalDateTime.now());
        
        // 2. 保存文档元数据
        documentMapper.insert(document);
        Long documentId = document.getId();
        
        // 3. 上传文档内容到文档服务
        if (documentDTO.getFileContent() != null && !documentDTO.getFileContent().isEmpty()) {
            try {
                // 创建通用文档DTO
                DocumentCreateDTO createDTO = new DocumentCreateDTO();
                createDTO.setTitle(documentDTO.getDocumentName());
                createDTO.setDocType(document.getDocumentType().toString());
                createDTO.setBusinessId(documentDTO.getCaseId());
                createDTO.setBusinessType(BUSINESS_TYPE);
                createDTO.setFileName(documentDTO.getDocumentName());
                if (documentDTO.getDocumentFormat() != null) {
                    createDTO.setFileType(documentDTO.getDocumentFormat());
                }
                
                // Base64解码文件内容
                byte[] fileBytes = Base64.getDecoder().decode(documentDTO.getFileContent());
                InputStream inputStream = new ByteArrayInputStream(fileBytes);
                
                // 上传到文档服务
                Long docId = documentService.createDocument(createDTO, inputStream);
                
                // 更新关联ID
                document.setDocumentPath(docId.toString());
                documentMapper.updateById(document);
            } catch (Exception e) {
                log.error("文档上传失败", e);
                throw new RuntimeException("文档上传失败: " + e.getMessage());
            }
        }
        
        // 4. 记录审计
        auditProvider.auditCaseStatusChange(
                documentDTO.getCaseId(),
                documentDTO.getCreatorId(),
                "0", // 无状态
                "1", // 上传状态
                "上传文档: " + document.getDocumentName()
        );
        
        // 5. 发送消息通知
        List<Map<String, Object>> changes = List.of(
            Map.of(
                "changeType", "DOCUMENT_UPLOAD",
                "documentId", documentId,
                "documentName", document.getDocumentName(),
                "documentType", document.getDocumentType()
            )
        );
        
        messageManager.sendCaseTeamChangeMessage(
                documentDTO.getCaseId(),
                changes,
                documentDTO.getCreatorId()
        );

        log.info("文档上传成功, ID: {}", documentId);
        return documentId;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean batchUploadDocuments(List<CaseDocumentDTO> documentDTOs) {
        log.info("批量上传文档: 数量={}", documentDTOs.size());
        
        for (CaseDocumentDTO dto : documentDTOs) {
            uploadDocument(dto);
        }
        
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateDocument(CaseDocumentDTO documentDTO) {
        log.info("更新文档信息: ID={}", documentDTO.getId());
        
        // 1. 获取原文档数据
        CaseDocument oldDocument = documentMapper.selectById(documentDTO.getId());
        if (oldDocument == null) {
            throw new RuntimeException("文档不存在: " + documentDTO.getId());
        }
        
        // 2. 更新文档信息
        CaseDocument document = new CaseDocument();
        BeanUtils.copyProperties(documentDTO, document);
        document.setUpdateTime(LocalDateTime.now());
        
        // 3. 保存更新
        int result = documentMapper.updateById(document);
        
        // 4. 处理文件内容更新
        String docIdStr = oldDocument.getDocumentPath();
        if (documentDTO.getFileContent() != null && !documentDTO.getFileContent().isEmpty() && 
            StringUtils.hasText(docIdStr)) {
            try {
                Long docId = Long.parseLong(docIdStr);
                
                // 创建通用文档DTO
                DocumentUpdateDTO updateDTO = new DocumentUpdateDTO();
                updateDTO.setTitle(documentDTO.getDocumentName());
                updateDTO.setDocType(document.getDocumentType().toString());
                
                // 更新文档服务中的文档
                documentService.updateDocument(docId, updateDTO);
                
                // 更新文档内容
                byte[] fileBytes = Base64.getDecoder().decode(documentDTO.getFileContent());
                InputStream inputStream = new ByteArrayInputStream(fileBytes);
                documentService.updateDocumentContent(docId, inputStream);
            } catch (Exception e) {
                log.error("文档内容更新失败", e);
                throw new RuntimeException("文档内容更新失败: " + e.getMessage());
            }
        }
        
        // 5. 记录审计
        Map<String, Object> changes = new HashMap<>();
        // 需要比较oldDocument和document，提取变更字段
        if (!oldDocument.getDocumentName().equals(document.getDocumentName())) {
            changes.put("documentName", Map.of("old", oldDocument.getDocumentName(), "new", document.getDocumentName()));
        }
        if (oldDocument.getDocumentType() != null && document.getDocumentType() != null && 
            !oldDocument.getDocumentType().equals(document.getDocumentType())) {
            changes.put("documentType", Map.of("old", oldDocument.getDocumentType(), "new", document.getDocumentType()));
        }
        if (oldDocument.getSecurityLevel() != null && document.getSecurityLevel() != null && 
            !oldDocument.getSecurityLevel().equals(document.getSecurityLevel())) {
            changes.put("securityLevel", Map.of("old", oldDocument.getSecurityLevel(), "new", document.getSecurityLevel()));
        }
        
        auditProvider.auditCaseUpdate(
                documentDTO.getCaseId(),
                documentDTO.getLastModifierId(),
                oldDocument,
                document,
                changes
        );
        
        log.info("文档更新成功, ID: {}", documentDTO.getId());
        return result > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteDocument(Long documentId) {
        log.info("删除文档: ID={}", documentId);
        
        // 1. 获取文档数据
        CaseDocument document = documentMapper.selectById(documentId);
        if (document == null) {
            throw new RuntimeException("文档不存在: " + documentId);
        }
        
        // 2. 软删除文档记录
        document.setDeleted(1);
        document.setUpdateTime(LocalDateTime.now());
        int result = documentMapper.updateById(document);
        
        // 3. 从文档服务中删除
        String docIdStr = document.getDocumentPath();
        if (StringUtils.hasText(docIdStr)) {
            try {
                Long docId = Long.parseLong(docIdStr);
                documentService.deleteDocument(docId);
            } catch (Exception e) {
                log.error("从文档服务删除失败", e);
                // 继续处理，不影响案件文档的删除
            }
        }
        
        // 4. 记录审计
        Long operatorId = 0L; // 实际应从上下文或参数获取
        
        auditProvider.auditCaseStatusChange(
                document.getCaseId(),
                operatorId,
                "1", // 正常状态
                "9", // 删除状态
                "删除文档: " + document.getDocumentName()
        );
        
        log.info("文档删除成功, ID: {}", documentId);
        return result > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean batchDeleteDocuments(List<Long> documentIds) {
        log.info("批量删除文档: 数量={}", documentIds.size());
        
        for (Long id : documentIds) {
            deleteDocument(id);
        }
        
        return true;
    }

    @Override
    public CaseDocumentVO getDocumentDetail(Long documentId) {
        log.info("获取文档详情: ID={}", documentId);
        
        CaseDocument document = documentMapper.selectById(documentId);
        if (document == null) {
            throw new RuntimeException("文档不存在: " + documentId);
        }
        
        CaseDocumentVO vo = new CaseDocumentVO();
        BeanUtils.copyProperties(document, vo);
        
        // 如果有关联文档，尝试获取额外信息
        String docIdStr = document.getDocumentPath();
        if (StringUtils.hasText(docIdStr)) {
            try {
                Long docId = Long.parseLong(docIdStr);
                DocumentVO docVO = documentService.getDocumentById(docId);
                if (docVO != null) {
                    // 补充文档信息（根据实际字段进行映射）
                    if (vo.getDocumentSize() == null || vo.getDocumentSize() == 0) {
                        vo.setDocumentSize(docVO.getFileSize());
                    }
                    // 可以补充其他信息...
                }
            } catch (Exception e) {
                log.warn("获取关联文档详情失败: {}", e.getMessage());
                // 忽略异常，继续返回基本信息
            }
        }
        
        return vo;
    }

    @Override
    public List<CaseDocumentVO> listCaseDocuments(Long caseId) {
        log.info("获取案件所有文档: 案件ID={}", caseId);
        
        LambdaQueryWrapper<CaseDocument> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CaseDocument::getCaseId, caseId);
        wrapper.eq(CaseDocument::getDeleted, 0); // 只查询未删除的
        wrapper.orderByDesc(CaseDocument::getCreateTime);
        
        List<CaseDocument> documents = documentMapper.selectList(wrapper);
        
        return documents.stream().map(entity -> {
            CaseDocumentVO vo = new CaseDocumentVO();
            BeanUtils.copyProperties(entity, vo);
            return vo;
        }).collect(Collectors.toList());
    }

    @Override
    public IPage<CaseDocumentVO> pageDocuments(Long caseId, Integer documentType, Integer securityLevel, 
                                             Integer pageNum, Integer pageSize) {
        log.info("分页查询文档: 案件ID={}, 文档类型={}, 安全级别={}, 页码={}, 每页大小={}", 
                 caseId, documentType, securityLevel, pageNum, pageSize);
        
        Page<CaseDocument> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<CaseDocument> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CaseDocument::getCaseId, caseId);
        wrapper.eq(CaseDocument::getDeleted, 0); // 只查询未删除的
        
        if (documentType != null) {
            wrapper.eq(CaseDocument::getDocumentType, documentType);
        }
        if (securityLevel != null) {
            wrapper.eq(CaseDocument::getSecurityLevel, securityLevel);
        }
        
        // 排序
        wrapper.orderByDesc(CaseDocument::getUpdateTime);
        
        // 执行查询
        IPage<CaseDocument> resultPage = documentMapper.selectPage(page, wrapper);
        
        // 转换为VO
        return resultPage.convert(entity -> {
            CaseDocumentVO vo = new CaseDocumentVO();
            BeanUtils.copyProperties(entity, vo);
            return vo;
        });
    }

    @Override
    public byte[] downloadDocument(Long documentId) {
        log.info("下载文档: ID={}", documentId);
        
        // 1. 获取文档元数据
        CaseDocument document = documentMapper.selectById(documentId);
        if (document == null) {
            throw new RuntimeException("文档不存在: " + documentId);
        }
        
        // 2. 从文档服务获取文档内容
        byte[] fileContent = null;
        String docIdStr = document.getDocumentPath();
        if (StringUtils.hasText(docIdStr)) {
            try {
                Long docId = Long.parseLong(docIdStr);
                InputStream inputStream = documentService.downloadDocument(docId);
                if (inputStream != null) {
                    fileContent = inputStream.readAllBytes();
                    inputStream.close();
                }
            } catch (Exception e) {
                log.error("文档下载失败", e);
                throw new RuntimeException("文档下载失败: " + e.getMessage());
            }
        }
        
        if (fileContent == null) {
            throw new RuntimeException("文档内容为空");
        }
        
        // 3. 记录下载审计
        Long operatorId = 0L; // 实际应从上下文或参数获取
        
        auditProvider.auditCaseStatusChange(
                document.getCaseId(),
                operatorId,
                null, 
                null,
                "下载文档: " + document.getDocumentName()
        );
        
        log.info("文档下载成功, ID: {}", documentId);
        return fileContent;
    }

    @Override
    public String previewDocument(Long documentId) {
        log.info("预览文档: ID={}", documentId);
        
        // 1. 获取文档元数据
        CaseDocument document = documentMapper.selectById(documentId);
        if (document == null) {
            throw new RuntimeException("文档不存在: " + documentId);
        }
        
        // 2. 从文档服务获取预览URL
        String previewUrl = null;
        String docIdStr = document.getDocumentPath();
        if (StringUtils.hasText(docIdStr)) {
            try {
                Long docId = Long.parseLong(docIdStr);
                previewUrl = documentService.previewDocument(docId);
            } catch (Exception e) {
                log.error("生成预览链接失败", e);
                throw new RuntimeException("生成预览链接失败: " + e.getMessage());
            }
        }
        
        if (previewUrl == null) {
            throw new RuntimeException("无法生成预览链接");
        }
        
        // 3. 记录预览审计
        Long operatorId = 0L; // 实际应从上下文或参数获取
        
        auditProvider.auditCaseStatusChange(
                document.getCaseId(),
                operatorId,
                null, 
                null,
                "预览文档: " + document.getDocumentName()
        );
        
        log.info("文档预览URL生成成功, ID: {}", documentId);
        return previewUrl;
    }

    @Override
    public String shareDocument(Long documentId, Integer shareType, Integer expireTime) {
        log.info("分享文档: ID={}, 分享类型={}, 过期时间={}分钟", documentId, shareType, expireTime);
        
        // 1. 获取文档元数据
        CaseDocument document = documentMapper.selectById(documentId);
        if (document == null) {
            throw new RuntimeException("文档不存在: " + documentId);
        }
        
        // 2. 从文档服务获取分享链接
        String shareUrl = null;
        String docIdStr = document.getDocumentPath();
        if (StringUtils.hasText(docIdStr)) {
            try {
                Long docId = Long.parseLong(docIdStr);
                // 转换过期时间从分钟到秒
                Long expireSeconds = expireTime != null ? expireTime * 60L : null;
                shareUrl = documentService.getDocumentUrl(docId, expireSeconds);
            } catch (Exception e) {
                log.error("生成分享链接失败", e);
                throw new RuntimeException("生成分享链接失败: " + e.getMessage());
            }
        }
        
        if (shareUrl == null) {
            throw new RuntimeException("无法生成分享链接");
        }
        
        // 3. 记录分享审计
        Long operatorId = 0L; // 实际应从上下文或参数获取
        
        auditProvider.auditCaseStatusChange(
                document.getCaseId(),
                operatorId,
                null, 
                null,
                "分享文档: " + document.getDocumentName()
        );
        
        log.info("文档分享链接生成成功, ID: {}", documentId);
        return shareUrl;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean setSecurityLevel(Long documentId, Integer securityLevel) {
        log.info("设置文档安全级别: ID={}, 级别={}", documentId, securityLevel);
        
        // 1. 获取文档数据
        CaseDocument document = documentMapper.selectById(documentId);
        if (document == null) {
            throw new RuntimeException("文档不存在: " + documentId);
        }
        
        // 2. 更新安全级别
        Integer oldLevel = document.getSecurityLevel();
        document.setSecurityLevel(securityLevel);
        document.setUpdateTime(LocalDateTime.now());
        int result = documentMapper.updateById(document);
        
        // 3. 更新文档服务中的安全级别
        String docIdStr = document.getDocumentPath();
        if (StringUtils.hasText(docIdStr)) {
            try {
                Long docId = Long.parseLong(docIdStr);
                DocumentUpdateDTO updateDTO = new DocumentUpdateDTO();
                updateDTO.setAccessLevel(String.valueOf(securityLevel)); // 使用accessLevel字段
                documentService.updateDocument(docId, updateDTO);
            } catch (Exception e) {
                log.error("更新文档服务中的安全级别失败", e);
                // 继续处理，不影响案件文档的安全级别更新
            }
        }
        
        // 4. 记录审计
        Long operatorId = 0L; // 实际应从上下文或参数获取
        
        auditProvider.auditCaseStatusChange(
                document.getCaseId(),
                operatorId,
                oldLevel != null ? oldLevel.toString() : "0", 
                securityLevel != null ? securityLevel.toString() : "0",
                "设置文档安全级别: " + document.getDocumentName()
        );
        
        log.info("文档安全级别设置成功, ID: {}", documentId);
        return result > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean addDocumentTag(Long documentId, String tag) {
        log.info("添加文档标签: ID={}, 标签={}", documentId, tag);
        
        // 1. 获取文档数据
        CaseDocument document = documentMapper.selectById(documentId);
        if (document == null) {
            throw new RuntimeException("文档不存在: " + documentId);
        }
        
        // 2. 添加标签
        String oldTags = document.getDocumentKeywords();
        if (oldTags == null || oldTags.isEmpty()) {
            document.setDocumentKeywords(tag);
        } else if (!oldTags.contains(tag)) {
            document.setDocumentKeywords(oldTags + "," + tag);
        } else {
            // 标签已存在，无需添加
            return true;
        }
        
        document.setUpdateTime(LocalDateTime.now());
        int result = documentMapper.updateById(document);
        
        // 3. 记录审计
        Long operatorId = 0L; // 实际应从上下文或参数获取
        
        auditProvider.auditCaseStatusChange(
                document.getCaseId(),
                operatorId,
                null, 
                null,
                "添加文档标签: " + tag
        );
        
        log.info("文档标签添加成功, ID: {}", documentId);
        return result > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean removeDocumentTag(Long documentId, String tag) {
        log.info("移除文档标签: ID={}, 标签={}", documentId, tag);
        
        // 1. 获取文档数据
        CaseDocument document = documentMapper.selectById(documentId);
        if (document == null) {
            throw new RuntimeException("文档不存在: " + documentId);
        }
        
        // 2. 移除标签
        String oldTags = document.getDocumentKeywords();
        if (oldTags == null || oldTags.isEmpty() || !oldTags.contains(tag)) {
            // 标签不存在，无需移除
            return true;
        }
        
        // 处理标签字符串
        String[] tags = oldTags.split(",");
        StringBuilder newTags = new StringBuilder();
        for (String t : tags) {
            if (!t.equals(tag)) {
                if (newTags.length() > 0) {
                    newTags.append(",");
                }
                newTags.append(t);
            }
        }
        
        document.setDocumentKeywords(newTags.toString());
        document.setUpdateTime(LocalDateTime.now());
        int result = documentMapper.updateById(document);
        
        // 3. 记录审计
        Long operatorId = 0L; // 实际应从上下文或参数获取
        
        auditProvider.auditCaseStatusChange(
                document.getCaseId(),
                operatorId,
                null, 
                null,
                "移除文档标签: " + tag
        );
        
        log.info("文档标签移除成功, ID: {}", documentId);
        return result > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean addDocumentNote(Long documentId, String note) {
        log.info("添加文档备注: ID={}", documentId);
        
        // 1. 获取文档数据
        CaseDocument document = documentMapper.selectById(documentId);
        if (document == null) {
            throw new RuntimeException("文档不存在: " + documentId);
        }
        
        // 2. 添加备注
        String oldDesc = document.getDocumentDescription();
        if (oldDesc == null || oldDesc.isEmpty()) {
            document.setDocumentDescription(note);
        } else {
            document.setDocumentDescription(oldDesc + "\n" + note);
        }
        
        document.setUpdateTime(LocalDateTime.now());
        int result = documentMapper.updateById(document);
        
        // 3. 记录审计
        Long operatorId = 0L; // 实际应从上下文或参数获取
        
        auditProvider.auditCaseStatusChange(
                document.getCaseId(),
                operatorId,
                null, 
                null,
                "添加文档备注"
        );
        
        log.info("文档备注添加成功, ID: {}", documentId);
        return result > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean moveDocument(Long documentId, Long targetFolderId) {
        log.info("移动文档: ID={}, 目标文件夹ID={}", documentId, targetFolderId);
        
        // 1. 获取文档数据
        CaseDocument document = documentMapper.selectById(documentId);
        if (document == null) {
            throw new RuntimeException("文档不存在: " + documentId);
        }
        
        // 2. 移动文档（简化实现，实际应检查目标文件夹的有效性）
        document.setUpdateTime(LocalDateTime.now());
        // CaseDocument实体使用previousVersionId作为父文件夹ID
        document.setPreviousVersionId(targetFolderId);
        int result = documentMapper.updateById(document);
        
        // 3. 记录审计
        Long operatorId = 0L; // 实际应从上下文或参数获取
        
        auditProvider.auditCaseStatusChange(
                document.getCaseId(),
                operatorId,
                null, 
                null,
                "移动文档: " + document.getDocumentName()
        );
        
        log.info("文档移动成功, ID: {}", documentId);
        return result > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean copyDocument(Long documentId, Long targetFolderId) {
        log.info("复制文档: ID={}, 目标文件夹ID={}", documentId, targetFolderId);
        
        // 1. 获取文档数据
        CaseDocument document = documentMapper.selectById(documentId);
        if (document == null) {
            throw new RuntimeException("文档不存在: " + documentId);
        }
        
        // 2. 复制文档元数据
        CaseDocument newDocument = new CaseDocument();
        BeanUtils.copyProperties(document, newDocument);
        newDocument.setId(null); // 确保创建新记录
        newDocument.setPreviousVersionId(targetFolderId); // 使用previousVersionId作为父文件夹ID
        newDocument.setDocumentName(document.getDocumentName() + " (副本)");
        newDocument.setCreateTime(LocalDateTime.now());
        newDocument.setUpdateTime(LocalDateTime.now());
        
        documentMapper.insert(newDocument);
        Long newDocumentId = newDocument.getId();
        
        // 3. 复制文件内容
        String docIdStr = document.getDocumentPath();
        if (StringUtils.hasText(docIdStr)) {
            try {
                Long docId = Long.parseLong(docIdStr);
                // DocumentService接口没有copyDocument方法，需要手动实现复制逻辑
                // 1. 下载原文档内容
                InputStream inputStream = documentService.downloadDocument(docId);
                if (inputStream != null) {
                    // 2. 创建新文档
                    DocumentCreateDTO createDTO = new DocumentCreateDTO();
                    createDTO.setTitle(newDocument.getDocumentName());
                    createDTO.setDocType(newDocument.getDocumentType().toString());
                    createDTO.setBusinessId(newDocument.getCaseId());
                    createDTO.setBusinessType(BUSINESS_TYPE);
                    createDTO.setFileName(newDocument.getDocumentName());
                    
                    // 3. 上传到文档服务
                    Long newDocId = documentService.createDocument(createDTO, inputStream);
                    
                    // 4. 更新关联ID
                    newDocument.setDocumentPath(newDocId.toString());
                    documentMapper.updateById(newDocument);
                }
            } catch (Exception e) {
                log.error("复制文档失败", e);
                throw new RuntimeException("复制文档失败: " + e.getMessage());
            }
        }
        
        // 4. 记录审计
        Long operatorId = 0L; // 实际应从上下文或参数获取
        
        auditProvider.auditCaseStatusChange(
                document.getCaseId(),
                operatorId,
                null, 
                null,
                "复制文档: " + document.getDocumentName()
        );
        
        log.info("文档复制成功, 原ID: {}, 新ID: {}", documentId, newDocumentId);
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createFolder(Long caseId, Long parentFolderId, String folderName) {
        log.info("创建文件夹: 案件ID={}, 父文件夹ID={}, 文件夹名={}", caseId, parentFolderId, folderName);
        
        // 1. 创建文件夹记录
        CaseDocument folder = new CaseDocument();
        folder.setCaseId(caseId);
        folder.setPreviousVersionId(parentFolderId); // 使用previousVersionId作为父文件夹ID
        folder.setDocumentName(folderName);
        folder.setDocumentType(0); // 假设0表示文件夹
        folder.setDocumentStatus(1); // 正常状态
        folder.setCreateTime(LocalDateTime.now());
        folder.setUpdateTime(LocalDateTime.now());
        
        documentMapper.insert(folder);
        Long folderId = folder.getId();
        
        // 2. 记录审计
        Long operatorId = 0L; // 实际应从上下文或参数获取
        
        auditProvider.auditCaseStatusChange(
                caseId,
                operatorId,
                null, 
                null,
                "创建文件夹: " + folderName
        );
        
        log.info("文件夹创建成功, ID: {}", folderId);
        return folderId;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean renameFolder(Long folderId, String newName) {
        log.info("重命名文件夹: ID={}, 新名称={}", folderId, newName);
        
        // 1. 获取文件夹数据
        CaseDocument folder = documentMapper.selectById(folderId);
        if (folder == null) {
            throw new RuntimeException("文件夹不存在: " + folderId);
        }
        
        // 2. 执行重命名
        String oldName = folder.getDocumentName();
        folder.setDocumentName(newName);
        folder.setUpdateTime(LocalDateTime.now());
        int result = documentMapper.updateById(folder);
        
        // 3. 记录审计
        Long operatorId = 0L; // 实际应从上下文或参数获取
        
        auditProvider.auditCaseStatusChange(
                folder.getCaseId(),
                operatorId,
                null, 
                null,
                "重命名文件夹: " + oldName + " -> " + newName
        );
        
        log.info("文件夹重命名成功, ID: {}", folderId);
        return result > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteFolder(Long folderId) {
        log.info("删除文件夹: ID={}", folderId);
        
        // 1. 获取文件夹数据
        CaseDocument folder = documentMapper.selectById(folderId);
        if (folder == null) {
            throw new RuntimeException("文件夹不存在: " + folderId);
        }
        
        // 2. 检查文件夹是否为空（实际实现应查询子文件和子文件夹）
        LambdaQueryWrapper<CaseDocument> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CaseDocument::getPreviousVersionId, folderId); // 使用previousVersionId作为父文件夹ID
        wrapper.eq(CaseDocument::getDeleted, 0);
        long count = documentMapper.selectCount(wrapper);
        
        if (count > 0) {
            throw new RuntimeException("文件夹不为空，无法删除");
        }
        
        // 3. 执行删除（软删除）
        folder.setDeleted(1);
        folder.setUpdateTime(LocalDateTime.now());
        int result = documentMapper.updateById(folder);
        
        // 4. 记录审计
        Long operatorId = 0L; // 实际应从上下文或参数获取
        
        auditProvider.auditCaseStatusChange(
                folder.getCaseId(),
                operatorId,
                "1", // 正常状态
                "9", // 删除状态
                "删除文件夹: " + folder.getDocumentName()
        );
        
        log.info("文件夹删除成功, ID: {}", folderId);
        return result > 0;
    }

    @Override
    public List<CaseDocumentVO> getFolderTree(Long caseId) {
        log.info("获取文件夹树: 案件ID={}", caseId);
        
        // 获取所有文件夹
        LambdaQueryWrapper<CaseDocument> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CaseDocument::getCaseId, caseId);
        wrapper.eq(CaseDocument::getDocumentType, 0); // 假设0表示文件夹
        wrapper.eq(CaseDocument::getDeleted, 0);
        wrapper.orderByAsc(CaseDocument::getPreviousVersionId); // 使用previousVersionId作为父文件夹ID排序
        wrapper.orderByAsc(CaseDocument::getDocumentName);
        
        List<CaseDocument> folders = documentMapper.selectList(wrapper);
        
        // 转换为VO
        return folders.stream().map(entity -> {
            CaseDocumentVO vo = new CaseDocumentVO();
            BeanUtils.copyProperties(entity, vo);
            return vo;
        }).collect(Collectors.toList());
    }

    @Override
    public boolean checkDocumentExists(Long documentId) {
        log.info("检查文档是否存在: ID={}", documentId);
        
        if (documentId == null) {
            return false;
        }
        
        LambdaQueryWrapper<CaseDocument> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CaseDocument::getId, documentId);
        wrapper.eq(CaseDocument::getDeleted, 0);
        
        return documentMapper.selectCount(wrapper) > 0;
    }

    @Override
    public int countDocuments(Long caseId, Integer documentType, Integer securityLevel) {
        log.info("统计文档数量: 案件ID={}, 文档类型={}, 安全级别={}", caseId, documentType, securityLevel);
        
        LambdaQueryWrapper<CaseDocument> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CaseDocument::getCaseId, caseId);
        wrapper.eq(CaseDocument::getDeleted, 0);
        
        if (documentType != null) {
            wrapper.eq(CaseDocument::getDocumentType, documentType);
        }
        if (securityLevel != null) {
            wrapper.eq(CaseDocument::getSecurityLevel, securityLevel);
        }
        
        return documentMapper.selectCount(wrapper).intValue();
    }
}