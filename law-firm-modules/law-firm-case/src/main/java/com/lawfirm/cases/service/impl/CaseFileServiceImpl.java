package com.lawfirm.cases.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lawfirm.model.cases.entity.CaseFile;
import com.lawfirm.cases.mapper.CaseFileMapper;
import com.lawfirm.cases.service.CaseDocumentService;
import com.lawfirm.common.core.exception.BusinessException;
import com.lawfirm.common.core.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;
import org.springframework.web.multipart.MultipartFile;
import org.apache.commons.io.FilenameUtils;
import com.lawfirm.model.base.storage.model.FileMetadata;
import com.lawfirm.model.base.storage.service.StorageService;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * 案件文件服务实现
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CaseFileServiceImpl extends ServiceImpl<CaseFileMapper, CaseFile> implements CaseDocumentService {

    @Value("${app.file-storage.base-path}")
    private String baseStoragePath;

    private final StorageService storageService;
    
    private static final String BUSINESS_TYPE = "case";

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CaseFile uploadFile(Long caseId, MultipartFile file, String description, Boolean isConfidential) {
        try {
            // 生成文件存储路径
            String fileName = file.getOriginalFilename();
            String fileExtension = FilenameUtils.getExtension(fileName);
            String storePath = generateStorePath(caseId, fileExtension);
            Path fullPath = Paths.get(baseStoragePath, storePath);
            
            // 确保目录存在
            Files.createDirectories(fullPath.getParent());
            
            // 保存文件
            Files.copy(file.getInputStream(), fullPath);
            
            // 创建文件记录
            CaseFile caseFile = new CaseFile();
            caseFile.setCaseId(caseId);
            caseFile.setFileName(fileName);
            caseFile.setFilePath(storePath);
            caseFile.setFileSize(file.getSize());
            caseFile.setContentType(file.getContentType());
            caseFile.setDescription(description);
            caseFile.setIsConfidential(isConfidential);
            caseFile.setFileMd5(DigestUtils.md5DigestAsHex(file.getInputStream()));
            
            save(caseFile);
            return caseFile;
            
        } catch (IOException e) {
            log.error("文件上传失败", e);
            throw new BusinessException("文件上传失败");
        }
    }

    @Override
    public CaseFile getFile(Long fileId) {
        CaseFile caseFile = getById(fileId);
        if (caseFile == null) {
            throw new ResourceNotFoundException("文件不存在");
        }
        return caseFile;
    }

    @Override
    public List<CaseFile> getCaseFiles(Long caseId) {
        return lambdaQuery()
                .eq(CaseFile::getCaseId, caseId)
                .list();
    }

    @Override
    public List<CaseFile> getConfidentialFiles(Long caseId) {
        return lambdaQuery()
                .eq(CaseFile::getCaseId, caseId)
                .eq(CaseFile::getIsConfidential, true)
                .list();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteFile(Long fileId) {
        CaseFile caseFile = getFile(fileId);
        
        // 删除物理文件
        try {
            Path filePath = Paths.get(baseStoragePath, caseFile.getFilePath());
            Files.deleteIfExists(filePath);
        } catch (IOException e) {
            log.error("删除文件失败", e);
            throw new BusinessException("删除文件失败");
        }
        
        // 删除数据库记录
        removeById(fileId);
    }

    @Override
    public byte[] downloadFile(Long fileId) {
        CaseFile caseFile = getFile(fileId);
        try {
            Path filePath = Paths.get(baseStoragePath, caseFile.getFilePath());
            return Files.readAllBytes(filePath);
        } catch (IOException e) {
            log.error("下载文件失败", e);
            throw new BusinessException("下载文件失败");
        }
    }
    
    private String generateStorePath(Long caseId, String fileExtension) {
        String datePath = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
        String fileName = String.format("%d_%s.%s", caseId, System.currentTimeMillis(), fileExtension);
        return Paths.get(datePath, fileName).toString();
    }

    @Override
    public FileMetadata uploadCaseFile(MultipartFile file, String caseId, String fileType) {
        String businessKey = String.format("%s/%s", caseId, fileType);
        return storageService.upload(file, BUSINESS_TYPE, businessKey);
    }

    @Override
    public InputStream downloadCaseFile(String fileId) {
        return storageService.download(fileId);
    }

    @Override
    public void deleteCaseFile(String fileId) {
        storageService.delete(fileId);
    }

    @Override
    public List<FileMetadata> listCaseFiles(String caseId, String fileType) {
        String businessKey = String.format("%s/%s", caseId, fileType);
        return storageService.listByBusiness(BUSINESS_TYPE, businessKey);
    }
} 