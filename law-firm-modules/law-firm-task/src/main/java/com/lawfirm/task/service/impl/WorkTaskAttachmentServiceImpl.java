package com.lawfirm.task.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lawfirm.model.task.dto.WorkTaskAttachmentDTO;
import com.lawfirm.model.task.entity.WorkTask;
import com.lawfirm.model.task.entity.WorkTaskAttachment;
import com.lawfirm.model.task.mapper.WorkTaskAttachmentMapper;
import com.lawfirm.model.task.service.WorkTaskAttachmentService;
import com.lawfirm.model.task.service.WorkTaskService;
import com.lawfirm.task.converter.WorkTaskAttachmentConverter;
import com.lawfirm.task.exception.TaskException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * 工作任务附件服务实现类
 */
@Slf4j
@Service("workTaskAttachmentService")
public class WorkTaskAttachmentServiceImpl extends ServiceImpl<WorkTaskAttachmentMapper, WorkTaskAttachment> implements WorkTaskAttachmentService {

    @Autowired
    private WorkTaskService workTaskService;
    
    @Autowired
    private WorkTaskAttachmentConverter workTaskAttachmentConverter;
    
    @Value("${file.upload.path:/data/upload/}")
    private String uploadPath;
    
    @Value("${file.download.baseUrl:http://localhost:8080/download/}")
    private String downloadBaseUrl;
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public WorkTaskAttachmentDTO uploadAttachment(Long taskId, MultipartFile file) {
        // 校验参数
        if (file == null || file.isEmpty()) {
            throw new TaskException("上传文件不能为空");
        }
        
        // 检查任务是否存在
        WorkTask workTask = workTaskService.getById(taskId);
        if (workTask == null) {
            throw new TaskException("任务不存在");
        }
        
        try {
            // 获取文件信息
            String originalFilename = file.getOriginalFilename();
            String fileSuffix = getFileSuffix(originalFilename);
            String fileName = originalFilename;
            long fileSize = file.getSize();
            String fileType = file.getContentType();
            String fileMd5 = DigestUtils.md5DigestAsHex(file.getInputStream());
            
            // 生成文件存储路径
            String relativePath = generateFilePath(fileSuffix);
            String absolutePath = uploadPath + relativePath;
            
            // 确保目录存在
            File dir = new File(absolutePath).getParentFile();
            if (!dir.exists()) {
                dir.mkdirs();
            }
            
            // 保存文件
            file.transferTo(new File(absolutePath));
            
            // 创建附件记录
            WorkTaskAttachment attachment = new WorkTaskAttachment();
            attachment.setTaskId(taskId);
            attachment.setFileName(fileName);
            attachment.setFilePath(relativePath);
            attachment.setFileSize(fileSize);
            attachment.setFileType(fileType);
            attachment.setFileMd5(fileMd5);
            attachment.setFileSuffix(fileSuffix);
            attachment.setUploaderId(getCurrentUserId());
            attachment.setUploaderName(getCurrentUsername());
            attachment.setDownloadCount(0);
            attachment.setPreviewable(isPreviewable(fileType));
            attachment.setFileIcon(getFileIcon(fileSuffix));
            attachment.setDownloadUrl(getAttachmentDownloadUrl(null) + relativePath);
            attachment.setPreviewUrl(isPreviewable(fileType) ? getAttachmentPreviewUrl(null) + relativePath : null);
            attachment.setCreateBy(getCurrentUsername());
            attachment.setUpdateBy(getCurrentUsername());
            
            // 保存附件
            save(attachment);
            
            // 转换为DTO并返回
            return workTaskAttachmentConverter.entityToDto(attachment);
        } catch (IOException e) {
            log.error("上传文件失败", e);
            throw new TaskException("上传文件失败：" + e.getMessage());
        }
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteAttachment(Long attachmentId) {
        // 检查附件是否存在
        WorkTaskAttachment attachment = getById(attachmentId);
        if (attachment == null) {
            throw new TaskException("附件不存在");
        }
        
        // 检查是否有权限删除附件（只能删除自己上传的附件）
        if (!Objects.equals(attachment.getUploaderId(), getCurrentUserId())) {
            throw new TaskException("无权删除该附件");
        }
        
        // 删除文件
        String absolutePath = uploadPath + attachment.getFilePath();
        File file = new File(absolutePath);
        if (file.exists()) {
            file.delete();
        }
        
        // 删除附件记录
        removeById(attachmentId);
    }
    
    @Override
    public WorkTaskAttachmentDTO getAttachmentDetail(Long attachmentId) {
        // 获取附件
        WorkTaskAttachment attachment = getById(attachmentId);
        if (attachment == null) {
            throw new TaskException("附件不存在");
        }
        
        // 转换为DTO
        return workTaskAttachmentConverter.entityToDto(attachment);
    }
    
    @Override
    public List<WorkTaskAttachmentDTO> getAttachmentsByTaskId(Long taskId) {
        return queryAttachmentList(taskId);
    }
    
    @Override
    public List<WorkTaskAttachmentDTO> queryAttachmentList(Long taskId) {
        // 查询任务的附件
        LambdaQueryWrapper<WorkTaskAttachment> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(WorkTaskAttachment::getTaskId, taskId)
                .orderByDesc(WorkTaskAttachment::getCreateTime);
        
        List<WorkTaskAttachment> attachments = list(wrapper);
        
        // 转换为DTO
        return attachments.stream()
                .map(workTaskAttachmentConverter::entityToDto)
                .collect(Collectors.toList());
    }
    
    @Override
    public byte[] downloadAttachment(Long attachmentId) {
        // 获取附件
        WorkTaskAttachment attachment = getById(attachmentId);
        if (attachment == null) {
            throw new TaskException("附件不存在");
        }
        
        try {
            // 读取文件
            String absolutePath = uploadPath + attachment.getFilePath();
            Path path = Paths.get(absolutePath);
            
            // 更新下载次数
            attachment.setDownloadCount(attachment.getDownloadCount() + 1);
            updateById(attachment);
            
            return Files.readAllBytes(path);
        } catch (IOException e) {
            log.error("下载文件失败", e);
            throw new TaskException("下载文件失败：" + e.getMessage());
        }
    }
    
    @Override
    public byte[] previewAttachment(Long attachmentId) {
        // 获取附件
        WorkTaskAttachment attachment = getById(attachmentId);
        if (attachment == null) {
            throw new TaskException("附件不存在");
        }
        
        // 检查文件是否支持预览
        if (!isPreviewable(attachment.getFileType())) {
            throw new TaskException("该文件类型不支持预览");
        }
        
        try {
            // 读取文件
            String absolutePath = uploadPath + attachment.getFilePath();
            Path path = Paths.get(absolutePath);
            
            return Files.readAllBytes(path);
        } catch (IOException e) {
            log.error("预览文件失败", e);
            throw new TaskException("预览文件失败：" + e.getMessage());
        }
    }
    
    @Override
    public String getAttachmentPreviewUrl(Long attachmentId) {
        if (attachmentId == null) {
            return downloadBaseUrl + "preview/";
        }
        
        // 获取附件
        WorkTaskAttachment attachment = getById(attachmentId);
        if (attachment == null) {
            throw new TaskException("附件不存在");
        }
        
        return attachment.getPreviewUrl();
    }
    
    @Override
    public String getAttachmentDownloadUrl(Long attachmentId) {
        if (attachmentId == null) {
            return downloadBaseUrl;
        }
        
        // 获取附件
        WorkTaskAttachment attachment = getById(attachmentId);
        if (attachment == null) {
            throw new TaskException("附件不存在");
        }
        
        return attachment.getDownloadUrl();
    }
    
    /**
     * 获取文件后缀
     */
    private String getFileSuffix(String fileName) {
        if (StringUtils.hasText(fileName) && fileName.contains(".")) {
            return fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
        }
        return "";
    }
    
    /**
     * 生成文件路径
     */
    private String generateFilePath(String fileSuffix) {
        // 按日期生成目录
        String dateDir = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
        
        // 生成文件名
        String fileName = UUID.randomUUID().toString().replace("-", "") + "." + fileSuffix;
        
        return dateDir + "/" + fileName;
    }
    
    /**
     * 判断文件是否支持预览
     */
    private boolean isPreviewable(String fileType) {
        if (!StringUtils.hasText(fileType)) {
            return false;
        }
        
        // 支持预览的文件类型
        return fileType.startsWith("image/") || 
               fileType.startsWith("text/") || 
               fileType.equals("application/pdf");
    }
    
    /**
     * 获取文件图标
     */
    private String getFileIcon(String fileSuffix) {
        if (!StringUtils.hasText(fileSuffix)) {
            return "file-unknown";
        }
        
        switch (fileSuffix.toLowerCase()) {
            case "pdf":
                return "file-pdf";
            case "doc":
            case "docx":
                return "file-word";
            case "xls":
            case "xlsx":
                return "file-excel";
            case "ppt":
            case "pptx":
                return "file-ppt";
            case "jpg":
            case "jpeg":
            case "png":
            case "gif":
            case "bmp":
                return "file-image";
            case "txt":
            case "log":
            case "md":
                return "file-text";
            case "zip":
            case "rar":
            case "7z":
                return "file-zip";
            default:
                return "file";
        }
    }
    
    @Override
    public Long getCurrentUserId() {
        return 1L; // TODO: 从安全上下文获取当前用户ID
    }
    
    @Override
    public String getCurrentUsername() {
        return "admin"; // TODO: 从安全上下文获取当前用户名
    }
    
    @Override
    public Long getCurrentTenantId() {
        return 1L; // TODO: 从安全上下文获取当前租户ID
    }
    
    @Override
    public boolean exists(QueryWrapper<WorkTaskAttachment> queryWrapper) {
        return baseMapper.exists(queryWrapper);
    }
    
    @Override
    public WorkTaskAttachment getById(Long id) {
        return baseMapper.selectById(id);
    }
    
    @Override
    public boolean update(WorkTaskAttachment entity) {
        return updateById(entity);
    }
    
    @Override
    public boolean removeBatch(List<Long> ids) {
        return removeByIds(ids);
    }
    
    @Override
    public boolean remove(Long id) {
        return removeById(id);
    }
    
    @Override
    public long count(QueryWrapper<WorkTaskAttachment> queryWrapper) {
        return baseMapper.selectCount(queryWrapper);
    }
    
    @Override
    public List<WorkTaskAttachment> list(QueryWrapper<WorkTaskAttachment> queryWrapper) {
        return baseMapper.selectList(queryWrapper);
    }
    
    @Override
    public Page<WorkTaskAttachment> page(Page<WorkTaskAttachment> page, QueryWrapper<WorkTaskAttachment> queryWrapper) {
        return baseMapper.selectPage(page, queryWrapper);
    }
    
    @Override
    public boolean save(WorkTaskAttachment entity) {
        return super.save(entity);
    }
    
    @Override
    public boolean saveBatch(List<WorkTaskAttachment> entityList) {
        return super.saveBatch(entityList);
    }
    
    @Override
    public boolean updateBatch(List<WorkTaskAttachment> entityList) {
        return super.updateBatchById(entityList);
    }
} 