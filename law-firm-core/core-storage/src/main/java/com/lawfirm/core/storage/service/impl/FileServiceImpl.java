package com.lawfirm.core.storage.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lawfirm.common.cache.annotation.RateLimiter;
import com.lawfirm.common.cache.annotation.RepeatSubmit;
import com.lawfirm.common.cache.annotation.SimpleCache;
import com.lawfirm.common.security.annotation.RequiresPermissions;
import com.lawfirm.core.storage.config.StorageProperties;
import com.lawfirm.core.storage.service.support.FileOperator;
import com.lawfirm.core.storage.service.support.FileUploader;
import com.lawfirm.model.base.service.impl.BaseServiceImpl;
import com.lawfirm.model.storage.dto.file.FileQueryDTO;
import com.lawfirm.model.storage.dto.file.FileUploadDTO;
import com.lawfirm.model.storage.entity.file.FileInfo;
import com.lawfirm.model.storage.entity.file.FileObject;
import com.lawfirm.model.storage.mapper.FileObjectMapper;
import com.lawfirm.model.storage.service.FileService;
import com.lawfirm.model.storage.vo.FileVO;
import com.lawfirm.model.storage.vo.PageVO;

import lombok.extern.slf4j.Slf4j;

/**
 * 文件服务实现类
 */
@Slf4j
@Service
public class FileServiceImpl extends BaseServiceImpl<FileObjectMapper, FileObject> implements FileService {

    private final FileUploader fileUploader;
    private final FileOperator fileOperator;
    private final StorageProperties storageProperties;
    
    public FileServiceImpl(FileObjectMapper baseMapper, FileUploader fileUploader, 
                          FileOperator fileOperator, StorageProperties storageProperties) {
        super();
        this.fileUploader = fileUploader;
        this.fileOperator = fileOperator;
        this.storageProperties = storageProperties;
    }
    
    @Override
    @Transactional
    @SimpleCache(key = "'upload_' + #uploadDTO.fileName", timeout = 60)
    @RepeatSubmit(interval = 5, timeUnit = TimeUnit.SECONDS)
    @RequiresPermissions("storage:file:upload")
    public FileVO upload(FileUploadDTO uploadDTO) {
        try {
            // 使用文件上传器处理上传
            FileObject fileObject = fileUploader.upload(uploadDTO);
            
            // 转换为VO对象
            return convertToVO(fileObject);
        } catch (Exception e) {
            log.error("文件上传失败", e);
            throw new RuntimeException("文件上传失败: " + e.getMessage());
        }
    }
    
    @Transactional
    @SimpleCache(key = "'upload_multi_' + #file.originalFilename", timeout = 60)
    @RepeatSubmit(interval = 5, timeUnit = TimeUnit.SECONDS)
    @RequiresPermissions("storage:file:upload")
    public FileVO upload(MultipartFile file, Long bucketId, String description, String tags) {
        try {
            // 使用文件上传器处理上传
            FileObject fileObject = fileUploader.upload(file, bucketId, description, tags);
            
            // 转换为VO对象
            return convertToVO(fileObject);
        } catch (Exception e) {
            log.error("文件上传失败", e);
            throw new RuntimeException("文件上传失败: " + e.getMessage());
        }
    }
    
    @SimpleCache(key = "'file_' + #fileId", timeout = 300, timeUnit = TimeUnit.SECONDS)
    @RequiresPermissions("storage:file:read")
    public FileVO getInfo(Long fileId) {
        FileObject fileObject = getById(fileId);
        if (fileObject == null) {
            throw new RuntimeException("文件不存在");
        }
        
        return convertToVO(fileObject);
    }
    
    @Override
    @RateLimiter(
        rate = 30,
        rateInterval = 60
    )
    @RequiresPermissions("storage:file:download")
    public byte[] download(Long fileId) {
        try (InputStream is = fileOperator.downloadFile(fileId)) {
            if (is == null) {
                throw new RuntimeException("文件不存在或无法访问");
            }
            
            // 更新下载计数
            FileObject fileObject = getById(fileId);
            if (fileObject != null) {
                // 如果FileInfo不存在，创建一个
                FileInfo fileInfo = fileObject.getFileInfo();
                if (fileInfo == null) {
                    fileInfo = new FileInfo();
                    fileObject.setFileInfo(fileInfo);
                }
                
                // 更新下载次数
                Long downloadCount = fileInfo.getDownloadCount();
                if (downloadCount == null) {
                    downloadCount = 0L;
                }
                fileInfo.setDownloadCount(downloadCount + 1);
                fileInfo.setLastDownloadTime(System.currentTimeMillis());
                
                update(fileObject);
            }
            
            // 读取文件内容
            return readInputStream(is);
        } catch (IOException e) {
            log.error("文件下载失败", e);
            throw new RuntimeException("文件下载失败: " + e.getMessage());
        }
    }
    
    @Override
    @RequiresPermissions("storage:file:read")
    public String getAccessUrl(Long fileId) {
        return getAccessUrl(fileId, 3600);
    }
    
    @SimpleCache(key = "'url_' + #fileId + '_' + #expireSeconds", timeout = 300, timeUnit = TimeUnit.SECONDS)
    @RequiresPermissions("storage:file:read")
    @Override
    public String getAccessUrl(Long fileId, Integer expireSeconds) {
        return fileOperator.getFileUrl(fileId, expireSeconds);
    }
    
    @Transactional
    @RequiresPermissions("storage:file:delete")
    public boolean delete(Long fileId) {
        return fileOperator.deleteFile(fileId);
    }
    
    @Override
    @RequiresPermissions("storage:file:read")
    public PageVO<FileVO> query(FileQueryDTO queryDTO) {
        // 构建查询条件
        QueryWrapper<FileObject> wrapper = new QueryWrapper<>();
        // 根据查询条件设置wrapper参数
        if (queryDTO.getBucketId() != null) {
            wrapper.eq("bucket_id", queryDTO.getBucketId());
        }
        if (queryDTO.getFileType() != null) {
            wrapper.eq("file_type", queryDTO.getFileType());
        }
        if (queryDTO.getFileName() != null && !queryDTO.getFileName().isEmpty()) {
            wrapper.like("file_name", queryDTO.getFileName());
        }
        
        // 获取当前页数据
        Page<FileObject> page = new Page<>(queryDTO.getPageNum(), queryDTO.getPageSize());
        page = page(page, wrapper);
        
        List<FileVO> result = new ArrayList<>(page.getRecords().size());
        for (FileObject fileObject : page.getRecords()) {
            result.add(convertToVO(fileObject));
        }
        
        // 构建返回结果
        return new PageVO<>(result, page.getTotal(), queryDTO.getPageNum(), queryDTO.getPageSize());
    }
    
    @Override
    @SimpleCache(key = "'preview_' + #id", timeout = 300, timeUnit = TimeUnit.SECONDS)
    @RequiresPermissions("storage:file:read")
    public String preview(Long id) {
        log.info("预览文件, id: {}", id);
        FileObject fileObject = getById(id);
        if (fileObject == null) {
            log.warn("文件不存在, id: {}", id);
            return null;
        }
        
        // 使用文件访问URL作为预览URL
        return getAccessUrl(id);
    }
    
    @Override
    @Transactional
    @RequiresPermissions("storage:file:update")
    public boolean updateInfo(Long id, String description, String tags) {
        log.info("更新文件信息, id: {}, description: {}, tags: {}", id, description, tags);
        FileObject fileObject = getById(id);
        if (fileObject == null) {
            log.warn("文件不存在, id: {}", id);
            return false;
        }
        
        // 如果FileInfo不存在，创建一个
        FileInfo fileInfo = fileObject.getFileInfo();
        if (fileInfo == null) {
            fileInfo = new FileInfo();
            fileObject.setFileInfo(fileInfo);
        }
        
        // 更新文件描述和标签
        fileInfo.setDescription(description);
        fileInfo.setTags(tags);
        
        return update(fileObject);
    }
    
    @Override
    @Transactional
    @RequiresPermissions("storage:file:update")
    public boolean moveToBucket(Long id, Long bucketId) {
        log.info("移动文件到存储桶, id: {}, bucketId: {}", id, bucketId);
        FileObject fileObject = getById(id);
        if (fileObject == null) {
            log.warn("文件不存在, id: {}", id);
            return false;
        }
        
        // 更新文件所属存储桶
        fileObject.setBucketId(bucketId);
        
        return update(fileObject);
    }
    
    @Override
    @Transactional
    @RequiresPermissions("storage:file:update")
    public Long copyToBucket(Long id, Long bucketId) {
        log.info("复制文件到存储桶, id: {}, bucketId: {}", id, bucketId);
        FileObject sourceFile = getById(id);
        if (sourceFile == null) {
            log.warn("文件不存在, id: {}", id);
            return null;
        }
        
        // 创建新的文件对象
        FileObject newFile = new FileObject();
        newFile.setFileName(sourceFile.getFileName());
        newFile.setStorageSize(sourceFile.getStorageSize());
        newFile.setFileType(sourceFile.getFileType());
        newFile.setStoragePath(sourceFile.getStoragePath());
        newFile.setMd5(sourceFile.getMd5());
        
        // 处理FileInfo
        FileInfo sourceInfo = sourceFile.getFileInfo();
        if (sourceInfo != null) {
            FileInfo newInfo = new FileInfo();
            newInfo.setDescription(sourceInfo.getDescription());
            newInfo.setTags(sourceInfo.getTags());
            newFile.setFileInfo(newInfo);
        }
        
        newFile.setBucketId(bucketId);
        
        save(newFile);
        return newFile.getId();
    }
    
    /**
     * 将文件对象转换为VO对象
     */
    private FileVO convertToVO(FileObject fileObject) {
        if (fileObject == null) {
            return null;
        }
        
        FileVO vo = new FileVO();
        vo.setId(fileObject.getId());
        vo.setFileName(fileObject.getFileName());
        vo.setFileType(fileObject.getFileType());
        vo.setExtension(fileObject.getExtension());
        vo.setStorageSize(fileObject.getStorageSize());
        vo.setStoragePath(fileObject.getStoragePath());
        vo.setBucketId(fileObject.getBucketId());
        
        // 处理FileInfo
        FileInfo fileInfo = fileObject.getFileInfo();
        if (fileInfo != null) {
            vo.setDescription(fileInfo.getDescription());
            vo.setTags(fileInfo.getTags());
            vo.setMetadata(fileInfo.getMetadata());
            vo.setAccessCount(fileInfo.getAccessCount());
            vo.setDownloadCount(fileInfo.getDownloadCount());
            vo.setLastAccessTime(fileInfo.getLastAccessTime());
            vo.setLastDownloadTime(fileInfo.getLastDownloadTime());
        }
        
        vo.setStatus(fileObject.getStatus());
        vo.setCreateTime(fileObject.getCreateTime());
        vo.setUpdateTime(fileObject.getUpdateTime());
        vo.setCreateBy(fileObject.getCreateBy());
        vo.setUpdateBy(fileObject.getUpdateBy());
        
        return vo;
    }
    
    /**
     * 读取输入流内容为字节数组
     */
    private byte[] readInputStream(InputStream is) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buffer = new byte[4096];
        int bytesRead;
        
        while ((bytesRead = is.read(buffer)) != -1) {
            baos.write(buffer, 0, bytesRead);
        }
        
        return baos.toByteArray();
    }
} 