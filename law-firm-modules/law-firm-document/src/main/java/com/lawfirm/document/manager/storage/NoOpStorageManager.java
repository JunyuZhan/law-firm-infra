package com.lawfirm.document.manager.storage;

import com.lawfirm.common.core.constant.ResultCode;
import com.lawfirm.common.core.exception.BusinessException;
import com.lawfirm.core.storage.strategy.StorageContext;
import com.lawfirm.model.document.dto.DocumentUploadDTO;
import com.lawfirm.model.document.vo.DocumentVO;
import com.lawfirm.model.storage.vo.FileVO;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Map;

/**
 * 存储服务的空实现管理器
 * 在存储服务被禁用时使用，提供默认行为和必要的Bean
 */
@Slf4j
@Component
@Order(1)
@ConditionalOnProperty(name = "lawfirm.storage.enabled", havingValue = "false", matchIfMissing = true)
public class NoOpStorageManager {

    /**
     * 创建一个空的StorageContext实现
     * 当存储服务禁用时，提供必要的依赖
     */
    @Bean(name = "noOpStorageContext")
    @ConditionalOnMissingBean
    public StorageContext storageContext() {
        log.info("创建存储服务空实现的Context");
        return new StorageContext(new ArrayList<>());
    }

    /**
     * 上传文档（空实现）
     */
    public DocumentVO upload(DocumentUploadDTO uploadDTO, MultipartFile file) {
        log.warn("存储服务已禁用，无法上传文档");
        throw new BusinessException(ResultCode.ERROR, "存储服务已禁用，无法上传文档");
    }

    /**
     * 保存文档信息（空实现）
     */
    public DocumentVO save(Map<String, Object> saveDTO) {
        log.warn("存储服务已禁用，无法保存文档信息");
        throw new BusinessException(ResultCode.ERROR, "存储服务已禁用，无法保存文档信息");
    }

    /**
     * 获取文档信息（空实现）
     */
    public DocumentVO getInfo(Long id) {
        log.warn("存储服务已禁用，无法获取文档信息");
        throw new BusinessException(ResultCode.ERROR, "存储服务已禁用，无法获取文档信息");
    }

    /**
     * 下载文档（空实现）
     */
    public void download(Long id, HttpServletResponse response) {
        log.warn("存储服务已禁用，无法下载文档");
        throw new BusinessException(ResultCode.ERROR, "存储服务已禁用，无法下载文档");
    }

    /**
     * 预览文档（空实现）
     */
    public String preview(Long id) {
        log.warn("存储服务已禁用，无法预览文档");
        throw new BusinessException(ResultCode.ERROR, "存储服务已禁用，无法预览文档");
    }

    /**
     * 删除文档（空实现）
     */
    public boolean delete(Long id) {
        log.warn("存储服务已禁用，无法删除文档");
        throw new BusinessException(ResultCode.ERROR, "存储服务已禁用，无法删除文档");
    }

    /**
     * 获取文件信息（空实现）
     */
    public FileVO getFileInfo(Long fileId) {
        log.warn("存储服务已禁用，无法获取文件信息");
        throw new BusinessException(ResultCode.ERROR, "存储服务已禁用，无法获取文件信息");
    }
} 