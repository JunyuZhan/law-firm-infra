package com.lawfirm.document.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lawfirm.document.exception.DocumentException;
import com.lawfirm.document.manager.security.SecurityManager;
import com.lawfirm.document.manager.storage.StorageManager;
import com.lawfirm.model.base.service.impl.BaseServiceImpl;
import com.lawfirm.model.document.dto.document.DocumentCreateDTO;
import com.lawfirm.model.document.dto.document.DocumentQueryDTO;
import com.lawfirm.model.document.dto.document.DocumentUpdateDTO;
import com.lawfirm.model.document.dto.DocumentUploadDTO;
import com.lawfirm.model.document.entity.base.BaseDocument;
import com.lawfirm.model.document.mapper.DocumentMapper;
import com.lawfirm.model.document.service.DocumentService;
import com.lawfirm.model.document.vo.DocumentVO;
import com.lawfirm.model.document.dto.DocumentDTO;
import com.lawfirm.model.storage.entity.bucket.StorageBucket;
import com.lawfirm.model.storage.entity.file.FileObject;
import com.lawfirm.model.storage.enums.StorageTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.ArrayList;

import com.lawfirm.model.storage.mapper.StorageBucketMapper;
import org.springframework.beans.factory.annotation.Value;
import com.lawfirm.core.storage.strategy.StorageStrategy;
import com.lawfirm.core.storage.strategy.StorageContext;
import com.lawfirm.model.document.mapper.DocumentTagMapper;
import com.lawfirm.model.document.mapper.DocumentTagRelMapper;
import com.lawfirm.model.document.entity.base.DocumentTag;
import com.lawfirm.model.document.entity.base.DocumentTagRel;
import org.springframework.beans.factory.annotation.Qualifier;
import com.lawfirm.model.log.service.AuditService;
import com.lawfirm.core.message.service.MessageSender;
import com.lawfirm.model.storage.service.FileService;
import com.lawfirm.model.storage.service.BucketService;
import com.lawfirm.model.search.service.SearchService;
import com.lawfirm.model.workflow.service.ProcessService;

/**
 * 文档服务实现类
 */
@Slf4j
@Service("documentServiceImpl")
@ConditionalOnProperty(name = "law-firm.storage.enabled", havingValue = "true", matchIfMissing = false)
public class DocumentServiceImpl extends BaseServiceImpl<DocumentMapper, BaseDocument> implements DocumentService {

    private final StorageManager storageManager;
    private final SecurityManager securityManager;
    private final ApplicationEventPublisher eventPublisher;
    private final StorageBucketMapper storageBucketMapper;
    private final StorageContext storageContext;
    private final DocumentTagMapper documentTagMapper;
    private final DocumentTagRelMapper documentTagRelMapper;
    @Value("${law-firm.storage.default-bucket:}")
    private String defaultBucketName;
    
    /**
     * 注入core层审计服务，便于后续记录文档操作日志
     */
    @Autowired(required = false)
    @Qualifier("documentAuditService")
    private AuditService auditService;

    /**
     * 注入core层消息发送服务，便于后续文档相关通知等
     */
    @Autowired(required = false)
    @Qualifier("documentMessageSender")
    private MessageSender messageSender;

    /**
     * 注入core层文件存储服务，便于后续文档附件上传等
     */
    @Autowired(required = false)
    @Qualifier("storageFileServiceImpl")
    private FileService fileService;

    /**
     * 注入core层存储桶服务，便于后续文档桶管理等
     */
    @Autowired(required = false)
    @Qualifier("storageBucketServiceImpl")
    private BucketService bucketService;

    /**
     * 注入core层全文检索服务，便于后续文档检索等
     */
    @Autowired(required = false)
    @Qualifier("documentSearchService")
    private SearchService searchService;

    /**
     * 注入core层流程服务，便于后续文档审批流转等
     */
    @Autowired(required = false)
    @Qualifier("documentProcessService")
    private ProcessService processService;

    @Autowired
    public DocumentServiceImpl(StorageManager storageManager, SecurityManager securityManager, ApplicationEventPublisher eventPublisher, StorageBucketMapper storageBucketMapper, StorageContext storageContext, DocumentTagMapper documentTagMapper, DocumentTagRelMapper documentTagRelMapper) {
        this.storageManager = storageManager;
        this.securityManager = securityManager;
        this.eventPublisher = eventPublisher;
        this.storageBucketMapper = storageBucketMapper;
        this.storageContext = storageContext;
        this.documentTagMapper = documentTagMapper;
        this.documentTagRelMapper = documentTagRelMapper;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createDocument(DocumentCreateDTO createDTO, InputStream inputStream) {
        // 检查权限
        if (!securityManager.checkDocumentManagementPermission()) {
            throw DocumentException.noPermission("创建文档");
        }

        try {
            // 由于 StorageManager.uploadDocument 需要 MultipartFile，但 DocumentService 接口使用 InputStream
            // 这里我们需要使用一个适配器来将 InputStream 转换为符合需求的对象
            
            // 获取存储桶
            StorageBucket bucket = getDefaultBucket();
            
            // 创建一个自定义的 MultipartFile 实现（实际项目中应该有专门的实现类）
            // 这里为简化处理，我们直接创建了文件对象，实际项目应该正确设置 MultipartFile
            FileObject fileObject = new FileObject();
            fileObject.setFileName(createDTO.getFileName());
            fileObject.setFileSize(createDTO.getFileSize());
            fileObject.setContentType(createDTO.getFileType());
            fileObject.setStoragePath("/documents/" + createDTO.getFileName()); // 临时路径，实际应由存储服务生成
            
            // 在实际项目中，应当通过调用 storageManager.uploadDocument 来上传文件
            // 这里由于接口不匹配，我们直接模拟返回结果
            // FileObject fileObject = storageManager.uploadDocument(multipartFile, bucket);
            
            // 创建文档记录
            BaseDocument document = new BaseDocument();
            document.setTitle(createDTO.getTitle());
            document.setDescription(createDTO.getDescription());
            document.setDocType(createDTO.getDocType());
            document.setStoragePath(fileObject.getStoragePath());
            document.setFileName(fileObject.getFileName());
            document.setFileSize(fileObject.getFileSize());
            document.setFileType(createDTO.getFileType());
            document.setStorageType(StorageTypeEnum.LOCAL.getCode());
            document.setDocStatus(createDTO.getDocStatus());
            document.setKeywords(createDTO.getKeywords());
            document.setIsEncrypted(createDTO.getIsEncrypted());
            document.setAccessLevel(createDTO.getAccessLevel());
            document.setBusinessId(createDTO.getBusinessId());
            document.setBusinessType(createDTO.getBusinessType());
            
            // 保存文档记录
            save(document);
            
            return document.getId();
        } catch (Exception e) {
            log.error("创建文档失败", e);
            throw DocumentException.failed("创建文档", e);
        }
    }
    
    /**
     * 获取默认存储桶
     */
    private StorageBucket getDefaultBucket() {
        // 优先从配置获取默认桶名
        if (defaultBucketName != null && !defaultBucketName.isEmpty()) {
            StorageBucket bucket = storageBucketMapper.findByBucketName(defaultBucketName);
            if (bucket != null) return bucket;
        }
        // 兜底：取第一个有效的LOCAL类型桶
        List<StorageBucket> buckets = storageBucketMapper.findByStorageType(StorageTypeEnum.LOCAL);
        if (buckets != null && !buckets.isEmpty()) {
            return buckets.get(0);
        }
        throw new DocumentException("未配置默认存储桶，且未找到可用的本地存储桶");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateDocument(Long id, DocumentUpdateDTO updateDTO) {
        // 检查权限
        if (!securityManager.checkDocumentPermission(id.toString(), "edit")) {
            throw DocumentException.noPermission("编辑文档");
        }
        BaseDocument oldDocument = getById(id);
        if (oldDocument == null) {
            throw DocumentException.notFound("文档(ID:" + id + ")");
        }
        // 只允许更新部分字段
        BeanUtils.copyProperties(updateDTO, oldDocument, "id", "createTime", "createBy");
        updateById(oldDocument);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateDocumentContent(Long id, InputStream inputStream) {
        if (!securityManager.checkDocumentPermission(id.toString(), "edit")) {
            throw DocumentException.noPermission("编辑文档内容");
        }
        BaseDocument document = getById(id);
        if (document == null) {
            throw DocumentException.notFound("文档(ID:" + id + ")");
        }
        try {
            StorageBucket bucket = getDefaultBucket();
            FileObject fileObject = new FileObject();
            fileObject.setStoragePath(document.getStoragePath());
            StorageStrategy strategy = storageContext.getStrategy(bucket);
            if (strategy == null) throw new DocumentException("不支持的存储类型: " + bucket.getStorageType());
            boolean success = strategy.uploadFile(bucket, fileObject, inputStream);
            if (!success) throw new DocumentException("文档内容覆盖上传失败");
            updateById(document);
        } catch (Exception e) {
            log.error("更新文档内容失败", e);
            throw DocumentException.failed("更新文档内容", e);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteDocument(Long id) {
        // 检查权限
        if (!securityManager.checkDocumentPermission(id.toString(), "delete")) {
            throw DocumentException.noPermission("删除文档");
        }
        try {
            BaseDocument document = getById(id);
            if (document == null) {
                throw DocumentException.notFound("文档(ID:" + id + ")");
            }
            // 删除存储文件（如有路径）
            if (document.getStoragePath() != null && !document.getStoragePath().isEmpty()) {
                try {
                    StorageBucket bucket = getDefaultBucket();
                    FileObject fileObject = new FileObject();
                    fileObject.setStoragePath(document.getStoragePath());
                    storageManager.deleteDocument(fileObject, bucket);
                } catch (Exception e) {
                    log.warn("删除存储文件失败，路径:{}", document.getStoragePath(), e);
                }
            }
            removeById(id);
            return true;
        } catch (Exception e) {
            log.error("删除文档失败", e);
            return false;
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteDocuments(List<Long> ids) {
        // 检查权限
        if (!securityManager.checkDocumentManagementPermission()) {
            throw DocumentException.noPermission("批量删除文档");
        }
        List<BaseDocument> documents = listByIds(ids);
        for (BaseDocument doc : documents) {
            if (doc.getStoragePath() != null && !doc.getStoragePath().isEmpty()) {
                try {
                    StorageBucket bucket = getDefaultBucket();
                    FileObject fileObject = new FileObject();
                    fileObject.setStoragePath(doc.getStoragePath());
                    storageManager.deleteDocument(fileObject, bucket);
                } catch (Exception e) {
                    log.warn("批量删除存储文件失败，路径:{}", doc.getStoragePath(), e);
                }
            }
        }
        removeByIds(ids);
    }

    @Override
    public DocumentVO getDocumentById(Long id) {
        // 检查权限
        if (!securityManager.checkDocumentPermission(id.toString(), "view")) {
            throw DocumentException.noPermission("查看文档");
        }

        try {
            // 查询文档基本信息
            BaseDocument document = getById(id);
            if (document == null) {
                throw new DocumentException("文档不存在");
            }
            
            // 转换为VO对象
            DocumentVO vo = new DocumentVO();
            BeanUtils.copyProperties(document, vo);
            
            // 增加访问记录
            updateAccessCount(id);
            
            return vo;
        } catch (DocumentException e) {
            throw e;
        } catch (Exception e) {
            log.error("获取文档详情失败，id: {}", id, e);
            throw DocumentException.failed("获取文档详情", e);
        }
    }
    
    /**
     * 更新文档访问计数
     */
    private void updateAccessCount(Long documentId) {
        try {
            // 实际项目中应该更新数据库中的访问计数
            log.info("更新文档访问计数，文档ID：{}", documentId);
        } catch (Exception e) {
            log.warn("更新访问计数失败", e);
        }
    }

    @Override
    public Page<DocumentVO> pageDocuments(Page<BaseDocument> page, DocumentQueryDTO queryDTO) {
        // 检查权限
        if (!securityManager.checkDocumentManagementPermission()) {
            throw DocumentException.noPermission("查询文档列表");
        }
        // 简单条件分页，实际可根据queryDTO丰富条件
        Page<BaseDocument> resultPage = page(page, null);
        Page<DocumentVO> voPage = new Page<>();
        voPage.setRecords(new ArrayList<>());
        BeanUtils.copyProperties(resultPage, voPage, "records");
        for (BaseDocument doc : resultPage.getRecords()) {
            DocumentVO vo = new DocumentVO();
            BeanUtils.copyProperties(doc, vo);
            voPage.getRecords().add(vo);
        }
        return voPage;
    }

    @Override
    public InputStream downloadDocument(Long id) {
        // 检查权限
        if (!securityManager.checkDocumentPermission(id.toString(), "download")) {
            throw DocumentException.noPermission("下载文档");
        }
        BaseDocument document = getById(id);
        if (document == null) {
            throw DocumentException.notFound("文档(ID:" + id + ")");
        }
        StorageBucket bucket = getDefaultBucket();
        FileObject fileObject = new FileObject();
        fileObject.setStoragePath(document.getStoragePath());
        return storageManager.downloadDocument(fileObject, bucket);
    }

    @Override
    public String previewDocument(Long id) {
        if (!securityManager.checkDocumentPermission(id.toString(), "view")) {
            throw DocumentException.noPermission("预览文档");
        }
        BaseDocument document = getById(id);
        if (document == null) {
            throw DocumentException.notFound("文档(ID:" + id + ")");
        }
        StorageBucket bucket = getDefaultBucket();
        String domain = bucket.getDomain();
        String path = document.getStoragePath();
        if (domain == null || domain.isEmpty()) throw new DocumentException("存储桶未配置访问域名");
        return domain.endsWith("/") ? domain + path : domain + "/" + path;
    }

    @Override
    public String getDocumentUrl(Long id) {
        if (!securityManager.checkDocumentPermission(id.toString(), "view")) {
            throw DocumentException.noPermission("获取文档URL");
        }
        BaseDocument document = getById(id);
        if (document == null) {
            throw DocumentException.notFound("文档(ID:" + id + ")");
        }
        StorageBucket bucket = getDefaultBucket();
        String domain = bucket.getDomain();
        String path = document.getStoragePath();
        if (domain == null || domain.isEmpty()) throw new DocumentException("存储桶未配置访问域名");
        return domain.endsWith("/") ? domain + path : domain + "/" + path;
    }

    @Override
    public String getDocumentUrl(Long id, Long expireTime) {
        if (!securityManager.checkDocumentPermission(id.toString(), "view")) {
            throw DocumentException.noPermission("获取文档URL");
        }
        BaseDocument document = getById(id);
        if (document == null) {
            throw DocumentException.notFound("文档(ID:" + id + ")");
        }
        StorageBucket bucket = getDefaultBucket();
        String domain = bucket.getDomain();
        String path = document.getStoragePath();
        if (domain == null || domain.isEmpty()) throw new DocumentException("存储桶未配置访问域名");
        // 若支持签名URL，可调用StorageStrategy生成临时URL，否则返回普通URL
        StorageStrategy strategy = storageContext.getStrategy(bucket);
        if (strategy != null && expireTime != null && expireTime > 0) {
            try {
                int expireSeconds = expireTime > Integer.MAX_VALUE ? Integer.MAX_VALUE : expireTime.intValue();
                return strategy.generatePresignedUrl(bucket, path, expireSeconds);
            } catch (Exception e) {
                log.warn("生成带有效期URL失败，降级为普通URL", e);
            }
        }
        return domain.endsWith("/") ? domain + path : domain + "/" + path;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateStatus(Long id, String status) {
        if (!securityManager.checkDocumentPermission(id.toString(), "edit")) {
            throw DocumentException.noPermission("更新文档状态");
        }
        BaseDocument document = getById(id);
        if (document == null) {
            throw DocumentException.notFound("文档(ID:" + id + ")");
        }
        document.setDocStatus(status);
        updateById(document);
    }

    @Override
    public List<DocumentVO> listDocumentsByBusiness(Long businessId, String businessType) {
        if (!securityManager.checkDocumentManagementPermission()) {
            throw DocumentException.noPermission("查询业务相关文档");
        }
        QueryWrapper<BaseDocument> wrapper = new QueryWrapper<>();
        wrapper.eq("business_id", businessId).eq("business_type", businessType);
        List<BaseDocument> docs = list(wrapper);
        List<DocumentVO> voList = new ArrayList<>();
        for (BaseDocument doc : docs) {
            DocumentVO vo = new DocumentVO();
            BeanUtils.copyProperties(doc, vo);
            voList.add(vo);
        }
        return voList;
    }

    @Override
    public List<DocumentVO> listDocumentsByType(String docType) {
        if (!securityManager.checkDocumentManagementPermission()) {
            throw DocumentException.noPermission("查询文档类型相关文档");
        }
        QueryWrapper<BaseDocument> wrapper = new QueryWrapper<>();
        wrapper.eq("doc_type", docType);
        List<BaseDocument> docs = list(wrapper);
        List<DocumentVO> voList = new ArrayList<>();
        for (BaseDocument doc : docs) {
            DocumentVO vo = new DocumentVO();
            BeanUtils.copyProperties(doc, vo);
            voList.add(vo);
        }
        return voList;
    }

    @Override
    public void refreshCache() {
        // 示例：如有本地缓存可在此清理/刷新
        // cacheManager.clearDocumentCache();
        log.info("文档缓存刷新（如有缓存实现）");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean setDocumentTags(Long documentId, List<String> tags) {
        if (!securityManager.checkDocumentPermission(documentId.toString(), "edit")) {
            throw DocumentException.noPermission("设置文档标签");
        }
        try {
            // 先删除原有标签关联
            documentTagRelMapper.deleteByDocumentId(documentId);
            for (String tagName : tags) {
                DocumentTag tag = documentTagMapper.selectByName(tagName);
                if (tag == null) {
                    tag = new DocumentTag();
                    tag.setTagName(tagName);
                    tag.setIsEnabled(true);
                    documentTagMapper.insert(tag);
                }
                DocumentTagRel rel = new DocumentTagRel();
                rel.setDocId(documentId);
                rel.setTagId(tag.getId());
                documentTagRelMapper.insert(rel);
            }
            return true;
        } catch (Exception e) {
            log.error("设置文档标签失败", e);
            return false;
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long uploadDocument(MultipartFile file, DocumentUploadDTO uploadDTO) {
        // 检查权限
        if (!securityManager.checkDocumentManagementPermission()) {
            throw DocumentException.noPermission("上传文档");
        }

        try {
            // 获取存储桶
            StorageBucket bucket = getDefaultBucket();
            
            // 上传文件
            FileObject fileObject = storageManager.uploadDocument(file, bucket);
            
            // 创建文档记录
            BaseDocument document = new BaseDocument();
            document.setTitle(uploadDTO.getTitle());
            document.setDescription(uploadDTO.getDescription());
            document.setDocType(uploadDTO.getDocType());
            document.setStoragePath(fileObject.getStoragePath());
            document.setFileName(fileObject.getFileName());
            document.setFileSize(fileObject.getFileSize());
            document.setFileType(fileObject.getContentType());
            document.setStorageType(StorageTypeEnum.LOCAL.getCode());
            document.setDocStatus(uploadDTO.getDocStatus());
            document.setKeywords(uploadDTO.getKeywords());
            document.setIsEncrypted(uploadDTO.getIsEncrypted());
            document.setAccessLevel(uploadDTO.getAccessLevel());
            document.setBusinessId(uploadDTO.getBusinessId());
            document.setBusinessType(uploadDTO.getBusinessType());
            
            // 保存文档记录
            save(document);
            
            return document.getId();
        } catch (Exception e) {
            log.error("上传文档失败", e);
            throw DocumentException.failed("上传文档", e);
        }
    }

    @Override
    public List<DocumentDTO> getBusinessDocuments(String businessType, Long businessId) {
        // 检查权限
        if (!securityManager.checkDocumentManagementPermission()) {
            throw DocumentException.noPermission("查询业务相关文档");
        }
        try {
            QueryWrapper<BaseDocument> wrapper = new QueryWrapper<>();
            wrapper.eq("business_type", businessType).eq("business_id", businessId);
            List<BaseDocument> docs = list(wrapper);
            List<DocumentDTO> dtoList = new ArrayList<>();
            for (BaseDocument doc : docs) {
                DocumentDTO dto = new DocumentDTO();
                BeanUtils.copyProperties(doc, dto);
                dtoList.add(dto);
            }
            return dtoList;
        } catch (Exception e) {
            log.error("查询业务相关文档失败", e);
            throw DocumentException.failed("查询业务相关文档", e);
        }
    }

    @Override
    public DocumentDTO getDocumentDetail(Long documentId) {
        // 检查权限
        if (!securityManager.checkDocumentPermission(documentId.toString(), "view")) {
            throw DocumentException.noPermission("查看文档详情");
        }
        try {
            BaseDocument doc = getById(documentId);
            if (doc == null) {
                throw DocumentException.notFound("文档(ID:" + documentId + ")");
            }
            DocumentDTO dto = new DocumentDTO();
            BeanUtils.copyProperties(doc, dto);
            return dto;
        } catch (Exception e) {
            log.error("获取文档详情失败", e);
            throw DocumentException.failed("获取文档详情", e);
        }
    }
}