package com.lawfirm.model.task.service;

import com.lawfirm.model.base.service.BaseService;
import com.lawfirm.model.task.dto.WorkTaskAttachmentDTO;
import com.lawfirm.model.task.entity.WorkTaskAttachment;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 工作任务附件服务接口
 */
public interface WorkTaskAttachmentService extends BaseService<WorkTaskAttachment> {
    
    /**
     * 上传工作任务附件
     *
     * @param taskId 工作任务ID
     * @param file 文件
     * @return 上传的附件DTO
     */
    WorkTaskAttachmentDTO uploadAttachment(Long taskId, MultipartFile file);
    
    /**
     * 删除工作任务附件
     *
     * @param attachmentId 附件ID
     */
    void deleteAttachment(Long attachmentId);
    
    /**
     * 获取工作任务附件详情
     *
     * @param attachmentId 附件ID
     * @return 工作任务附件DTO
     */
    WorkTaskAttachmentDTO getAttachmentDetail(Long attachmentId);
    
    /**
     * 获取任务附件列表
     *
     * @param taskId 任务ID
     * @return 任务附件列表
     */
    List<WorkTaskAttachmentDTO> getAttachmentsByTaskId(Long taskId);
    
    /**
     * 查询工作任务附件列表
     *
     * @param taskId 工作任务ID
     * @return 工作任务附件列表
     */
    List<WorkTaskAttachmentDTO> queryAttachmentList(Long taskId);
    
    /**
     * 下载工作任务附件
     *
     * @param attachmentId 附件ID
     * @return 附件文件
     */
    byte[] downloadAttachment(Long attachmentId);
    
    /**
     * 预览工作任务附件
     *
     * @param attachmentId 附件ID
     * @return 附件预览内容
     */
    byte[] previewAttachment(Long attachmentId);
    
    /**
     * 获取附件预览URL
     *
     * @param attachmentId 附件ID
     * @return 预览URL
     */
    String getAttachmentPreviewUrl(Long attachmentId);
    
    /**
     * 获取附件下载URL
     *
     * @param attachmentId 附件ID
     * @return 下载URL
     */
    String getAttachmentDownloadUrl(Long attachmentId);
} 