package com.lawfirm.document.manager.storage;

import com.lawfirm.common.security.utils.SecurityUtils;
import com.lawfirm.core.storage.service.support.FileOperator;
import com.lawfirm.model.storage.service.FileService;
import com.lawfirm.model.storage.service.BucketService;
import com.lawfirm.model.storage.dto.file.FileUploadDTO;
import com.lawfirm.model.storage.entity.file.FileObject;
import com.lawfirm.model.storage.vo.FileVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * 文档存储管理器接口
 */
public interface DocumentStorageManager {

    /**
     * 上传文档
     *
     * @param file 文件
     * @param path 存储路径
     * @return 文件访问URL
     */
    String uploadDocument(MultipartFile file, String path) throws IOException;

    /**
     * 上传文档
     *
     * @param id 文档ID
     * @param inputStream 文件输入流
     * @return 文件访问URL
     */
    String uploadDocument(Long id, InputStream inputStream) throws IOException;

    /**
     * 更新文档
     *
     * @param id 文档ID
     * @param inputStream 文件输入流
     */
    void updateDocument(Long id, InputStream inputStream) throws IOException;

    /**
     * 删除文档
     *
     * @param path 存储路径
     */
    void deleteDocument(String path);

    /**
     * 删除文档
     *
     * @param id 文档ID
     */
    void deleteDocument(Long id);

    /**
     * 获取文档
     *
     * @param path 存储路径
     * @return 文件输入流
     */
    InputStream getDocument(String path) throws IOException;

    /**
     * 获取文档
     *
     * @param id 文档ID
     * @return 文件输入流
     */
    InputStream getDocument(Long id) throws IOException;

    /**
     * 下载文档
     *
     * @param id 文档ID
     * @return 文件输入流
     */
    InputStream downloadDocument(Long id) throws IOException;

    /**
     * 预览文档
     *
     * @param id 文档ID
     * @return 预览URL
     */
    String previewDocument(Long id);

    /**
     * 获取文档访问URL
     *
     * @param path 存储路径
     * @return 访问URL
     */
    String getDocumentUrl(String path);

    /**
     * 获取文档访问URL
     *
     * @param id 文档ID
     * @return 访问URL
     */
    String getDocumentUrl(Long id);

    /**
     * 获取文档访问URL（带有效期）
     *
     * @param path 存储路径
     * @param expireTime 有效期（秒）
     * @return 访问URL
     */
    String getDocumentUrl(String path, Long expireTime);

    /**
     * 获取文档访问URL（带有效期）
     *
     * @param id 文档ID
     * @param expireTime 有效期（秒）
     * @return 访问URL
     */
    String getDocumentUrl(Long id, Long expireTime);

    /**
     * 检查文档是否存在
     *
     * @param path 存储路径
     * @return 是否存在
     */
    boolean exists(String path);

    /**
     * 检查文档是否存在
     *
     * @param id 文档ID
     * @return 是否存在
     */
    boolean exists(Long id);

    /**
     * 复制文档
     *
     * @param sourcePath 源路径
     * @param targetPath 目标路径
     */
    void copyDocument(String sourcePath, String targetPath) throws IOException;

    /**
     * 移动文档
     *
     * @param sourcePath 源路径
     * @param targetPath 目标路径
     */
    void moveDocument(String sourcePath, String targetPath) throws IOException;

    /**
     * 获取文档大小
     *
     * @param path 存储路径
     * @return 文件大小（字节）
     */
    long getSize(String path);

    /**
     * 获取文档大小
     *
     * @param id 文档ID
     * @return 文件大小（字节）
     */
    long getSize(Long id);

    /**
     * 获取文档最后修改时间
     *
     * @param path 存储路径
     * @return 最后修改时间（毫秒）
     */
    long getLastModified(String path);

    /**
     * 获取文档最后修改时间
     *
     * @param id 文档ID
     * @return 最后修改时间（毫秒）
     */
    long getLastModified(Long id);
}
