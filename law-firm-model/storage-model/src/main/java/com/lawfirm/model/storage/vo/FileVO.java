package com.lawfirm.model.storage.vo;

import com.lawfirm.model.base.vo.BaseVO;
import com.lawfirm.model.storage.enums.FileTypeEnum;
import com.lawfirm.model.storage.enums.StorageTypeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 文件视图对象
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class FileVO extends BaseVO {

    private static final long serialVersionUID = 1L;

    /**
     * 文件名
     */
    private String fileName;

    /**
     * 文件类型
     */
    private FileTypeEnum fileType;

    /**
     * 存储类型
     */
    private StorageTypeEnum storageType;

    /**
     * 文件扩展名
     */
    private String extension;

    /**
     * 文件大小（字节）
     */
    private Long storageSize;

    /**
     * 存储路径
     */
    private String storagePath;

    /**
     * 存储桶ID
     */
    private Long bucketId;

    /**
     * 文件描述
     */
    private String description;

    /**
     * 文件标签
     */
    private String tags;

    /**
     * 文件元数据
     */
    private String metadata;

    /**
     * 访问次数
     */
    private Long accessCount;

    /**
     * 下载次数
     */
    private Long downloadCount;

    /**
     * 最后访问时间
     */
    private Long lastAccessTime;

    /**
     * 最后下载时间
     */
    private Long lastDownloadTime;

    /**
     * 状态（0：无效，1：有效）
     */
    private Integer status;
} 