package com.lawfirm.document.manager.storage;

import com.lawfirm.model.storage.entity.bucket.StorageBucket;
import com.lawfirm.model.storage.entity.file.FileObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 文档存储管理器空实现，当存储功能禁用时使用
 */
@Slf4j
@Component
@ConditionalOnProperty(name = "lawfirm.storage.enabled", havingValue = "false", matchIfMissing = false)
public class NoOpStorageManager {

    /**
     * 上传文档
     *
     * @param file 文件
     * @param bucket 存储桶
     * @return 文件对象
     */
    public FileObject uploadDocument(MultipartFile file, StorageBucket bucket) throws IOException {
        log.warn("存储功能已禁用，忽略上传文档操作");
        return null;
    }

    /**
     * 下载文档
     *
     * @param fileObject 文件对象
     * @param bucket 存储桶
     * @return 文件输入流
     */
    public InputStream downloadDocument(FileObject fileObject, StorageBucket bucket) {
        log.warn("存储功能已禁用，忽略下载文档操作");
        return new ByteArrayInputStream(new byte[0]);
    }

    /**
     * 删除文档
     *
     * @param fileObject 文件对象
     * @param bucket 存储桶
     * @return 是否删除成功
     */
    public boolean deleteDocument(FileObject fileObject, StorageBucket bucket) {
        log.warn("存储功能已禁用，忽略删除文档操作");
        return true;
    }
} 