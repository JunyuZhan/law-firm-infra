package com.lawfirm.document.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lawfirm.common.data.service.impl.BaseServiceImpl;
import com.lawfirm.common.core.exception.BusinessException;
import com.lawfirm.common.core.log.annotation.OperationLog;
import com.lawfirm.document.mapper.DocumentMapper;
import com.lawfirm.document.service.DocumentService;
import com.lawfirm.model.document.entity.Document;
import com.lawfirm.model.document.query.DocumentQuery;
import com.lawfirm.model.document.vo.DocumentVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class DocumentServiceImpl extends BaseServiceImpl<DocumentMapper, Document, DocumentVO> implements DocumentService {

    @Value("${document.storage.path}")
    private String storagePath;

    @Override
    @Transactional
    @OperationLog(description = "上传文档", type = "DOCUMENT_UPLOAD")
    public DocumentVO upload(MultipartFile file, Document document, String operator) {
        try {
            // 生成文档编号和版本号
            document.setDocumentNumber(generateDocumentNumber());
            document.setVersion("1.0");
            document.setFileName(file.getOriginalFilename());
            document.setFileSize(file.getSize());
            document.setFileType(FilenameUtils.getExtension(file.getOriginalFilename()));
            document.setUploadTime(LocalDateTime.now());
            document.setUploadBy(operator);
            document.setStatus(0); // 待审核状态

            // 保存文件
            String filePath = saveFile(file, document.getDocumentNumber(), document.getVersion());
            document.setFilePath(filePath);

            // 保存文档信息
            save(document);

            return toVO(document);
        } catch (IOException e) {
            log.error("Failed to upload document", e);
            throw new BusinessException("上传文档失败");
        }
    }

    @Override
    @Transactional
    @OperationLog(description = "批量上传文档", type = "DOCUMENT_BATCH_UPLOAD")
    public List<DocumentVO> batchUpload(List<MultipartFile> files, List<Document> documents, String operator) {
        if (files.size() != documents.size()) {
            throw new BusinessException("文件数量与文档信息数量不匹配");
        }

        List<DocumentVO> results = new ArrayList<>();
        for (int i = 0; i < files.size(); i++) {
            results.add(upload(files.get(i), documents.get(i), operator));
        }
        return results;
    }

    @Override
    @Transactional
    @OperationLog(description = "更新文档", type = "DOCUMENT_UPDATE")
    public DocumentVO update(Long id, MultipartFile file, Document document, String operator) {
        Document existingDoc = getById(id);
        if (existingDoc == null) {
            throw new BusinessException("文档不存在");
        }

        try {
            // 更新版本号
            String newVersion = incrementVersion(existingDoc.getVersion());
            document.setId(id);
            document.setVersion(newVersion);
            document.setUpdateTime(LocalDateTime.now());
            document.setUpdateBy(operator);

            if (file != null) {
                // 保存新文件
                String filePath = saveFile(file, existingDoc.getDocumentNumber(), newVersion);
                document.setFilePath(filePath);
                document.setFileName(file.getOriginalFilename());
                document.setFileSize(file.getSize());
                document.setFileType(FilenameUtils.getExtension(file.getOriginalFilename()));
            }

            // 更新文档信息
            updateById(document);

            return toVO(document);
        } catch (IOException e) {
            log.error("Failed to update document", e);
            throw new BusinessException("更新文档失败");
        }
    }

    @Override
    @OperationLog(description = "下载文档", type = "DOCUMENT_DOWNLOAD")
    public byte[] download(Long id, String version) {
        try {
            Document document = getById(id);
            if (document == null) {
                throw new BusinessException("文档不存在");
            }

            String filePath = version == null ? 
                document.getFilePath() : 
                getVersionFilePath(document.getDocumentNumber(), version);

            Path path = Paths.get(filePath);
            if (!Files.exists(path)) {
                throw new BusinessException("文件不存在");
            }

            return Files.readAllBytes(path);
        } catch (IOException e) {
            log.error("Failed to download document", e);
            throw new BusinessException("下载文档失败");
        }
    }

    @Override
    @Transactional
    @OperationLog(description = "删除文档", type = "DOCUMENT_DELETE")
    public void delete(Long id, String operator) {
        Document document = getById(id);
        if (document == null) {
            throw new BusinessException("文档不存在");
        }

        // 删除所有版本的文件
        deleteAllVersions(document.getDocumentNumber());

        // 删除数据库记录
        removeById(id);
    }

    @Override
    @Transactional
    @OperationLog(description = "审核文档", type = "DOCUMENT_AUDIT")
    public DocumentVO audit(Long id, Integer status, String comment, String operator) {
        Document document = getById(id);
        if (document == null) {
            throw new BusinessException("文档不存在");
        }

        document.setStatus(status);
        document.setAuditComment(comment);
        document.setAuditTime(LocalDateTime.now());
        document.setAuditBy(operator);

        updateById(document);

        return toVO(document);
    }

    @Override
    @OperationLog(description = "获取文档版本历史", type = "DOCUMENT_VERSION_HISTORY")
    public List<DocumentVO> getVersionHistory(Long id) {
        Document document = getById(id);
        if (document == null) {
            throw new BusinessException("文档不存在");
        }

        LambdaQueryWrapper<Document> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Document::getDocumentNumber, document.getDocumentNumber())
               .orderByDesc(Document::getVersion);

        return toVO(list(wrapper));
    }

    @Override
    @OperationLog(description = "导出文档列表", type = "DOCUMENT_EXPORT")
    public byte[] exportToExcel(DocumentQuery query) {
        try {
            List<Document> documents = list(buildQueryWrapper(query));
            
            Workbook workbook = new XSSFWorkbook();
            // TODO: 实现Excel导出逻辑
            
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            workbook.write(outputStream);
            return outputStream.toByteArray();
        } catch (IOException e) {
            log.error("Failed to export documents", e);
            throw new BusinessException("导出文档失败");
        }
    }

    private String generateDocumentNumber() {
        return "DOC-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    private String saveFile(MultipartFile file, String documentNumber, String version) throws IOException {
        String fileName = String.format("%s_v%s.%s", 
            documentNumber, 
            version,
            FilenameUtils.getExtension(file.getOriginalFilename()));
        
        Path directory = Paths.get(storagePath, documentNumber);
        Files.createDirectories(directory);
        
        Path filePath = directory.resolve(fileName);
        Files.copy(file.getInputStream(), filePath);
        
        return filePath.toString();
    }

    private String incrementVersion(String version) {
        String[] parts = version.split("\\.");
        int major = Integer.parseInt(parts[0]);
        int minor = Integer.parseInt(parts[1]);
        return String.format("%d.%d", major, minor + 1);
    }

    private String getVersionFilePath(String documentNumber, String version) {
        return Paths.get(storagePath, documentNumber, 
            String.format("%s_v%s", documentNumber, version)).toString();
    }

    private void deleteAllVersions(String documentNumber) {
        try {
            Path directory = Paths.get(storagePath, documentNumber);
            if (Files.exists(directory)) {
                FileUtils.deleteDirectory(new File(directory.toString()));
            }
        } catch (IOException e) {
            log.error("Failed to delete document versions", e);
            throw new BusinessException("删除文档版本失败");
        }
    }

    private LambdaQueryWrapper<Document> buildQueryWrapper(DocumentQuery query) {
        LambdaQueryWrapper<Document> wrapper = new LambdaQueryWrapper<>();
        // TODO: 根据查询条件构建wrapper
        return wrapper;
    }

    @Override
    protected void beforeCreate(Document entity) {
        // 文档创建前的验证逻辑
        if (entity.getDocumentNumber() == null) {
            entity.setDocumentNumber(generateDocumentNumber());
        }
        if (entity.getVersion() == null) {
            entity.setVersion("1.0");
        }
    }

    @Override
    protected void beforeUpdate(Document entity) {
        // 文档更新前的验证逻辑
        Document existingDoc = getById(entity.getId());
        if (existingDoc == null) {
            throw new BusinessException("文档不存在");
        }
    }

    @Override
    protected void afterLoad(DocumentVO dto, Document entity) {
        // 文档加载后的处理逻辑
        if (entity != null) {
            // 可以在这里添加额外的处理逻辑，比如设置文档状态描述等
        }
    }

    @Override
    protected DocumentVO createDTO() {
        return new DocumentVO();
    }

    @Override
    protected Document createEntity() {
        return new Document();
    }
} 