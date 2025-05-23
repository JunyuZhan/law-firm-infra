package com.lawfirm.model.storage.service;

import com.lawfirm.model.base.service.BaseService;
import com.lawfirm.model.storage.dto.file.FileQueryDTO;
import com.lawfirm.model.storage.dto.file.FileUploadDTO;
import com.lawfirm.model.storage.entity.file.FileObject;
import com.lawfirm.model.storage.vo.FileVO;
import com.lawfirm.model.storage.vo.PageVO;
import org.springframework.web.multipart.MultipartFile;

/**
 * 文件服务接口
 */
public interface FileService extends BaseService<FileObject> {

    /**
     * 上传文件 (通过DTO)
     *
     * @param uploadDTO 上传参数
     * @return 文件视图对象
     */
    FileVO upload(FileUploadDTO uploadDTO);
    
    /**
     * 上传文件 (通过MultipartFile)
     *
     * @param file 文件
     * @param bucketId 存储桶ID
     * @param description 文件描述
     * @param tags 文件标签
     * @return 文件视图对象
     */
    FileVO upload(MultipartFile file, Long bucketId, String description, String tags);

    /**
     * 获取文件信息
     * 
     * @param id 文件ID
     * @return 文件视图对象
     */
    FileVO getInfo(Long id);
    
    /**
     * 下载文件
     *
     * @param id 文件ID
     * @return 文件字节数组
     */
    byte[] download(Long id);

    /**
     * 预览文件
     *
     * @param id 文件ID
     * @return 预览URL
     */
    String preview(Long id);

    /**
     * 获取文件访问URL
     *
     * @param id 文件ID
     * @return 访问URL
     */
    String getAccessUrl(Long id);
    
    /**
     * 获取文件访问URL（带过期时间）
     *
     * @param id 文件ID
     * @param expireMinutes 过期时间（分钟）
     * @return 访问URL
     */
    String getAccessUrl(Long id, Integer expireMinutes);

    /**
     * 更新文件信息
     *
     * @param id          文件ID
     * @param description 文件描述
     * @param tags        文件标签
     * @return 是否成功
     */
    boolean updateInfo(Long id, String description, String tags);

    /**
     * 移动文件到指定存储桶
     *
     * @param id       文件ID
     * @param bucketId 目标存储桶ID
     * @return 是否成功
     */
    boolean moveToBucket(Long id, Long bucketId);

    /**
     * 复制文件到指定存储桶
     *
     * @param id       文件ID
     * @param bucketId 目标存储桶ID
     * @return 新文件ID
     */
    Long copyToBucket(Long id, Long bucketId);
    
    /**
     * 删除文件
     * 
     * @param id 文件ID
     * @return 是否成功
     */
    boolean delete(Long id);
    
    /**
     * 查询文件
     * 
     * @param queryDTO 查询条件
     * @return 文件列表（分页）
     */
    PageVO<FileVO> query(FileQueryDTO queryDTO);
} 