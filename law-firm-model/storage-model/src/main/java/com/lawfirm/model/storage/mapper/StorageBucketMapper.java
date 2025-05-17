package com.lawfirm.model.storage.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lawfirm.model.storage.entity.bucket.StorageBucket;
import com.lawfirm.model.storage.enums.StorageTypeEnum;
import com.lawfirm.model.storage.constant.StorageSqlConstants;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 存储桶数据访问接口
 */
@Mapper
public interface StorageBucketMapper extends BaseMapper<StorageBucket> {
    
    /**
     * 根据桶名称统计数量
     *
     * @param bucketName 桶名称
     * @return 数量
     */
    @Select(StorageSqlConstants.COUNT_BY_BUCKET_NAME)
    long countByBucketName(@Param("bucketName") String bucketName);
    
    /**
     * 根据桶名称查询
     *
     * @param bucketName 桶名称
     * @return 存储桶
     */
    @Select(StorageSqlConstants.FIND_BY_BUCKET_NAME)
    StorageBucket findByBucketName(@Param("bucketName") String bucketName);
    
    /**
     * 根据存储类型查询列表
     *
     * @param storageType 存储类型
     * @return 存储桶列表
     */
    @Select(StorageSqlConstants.FIND_BY_STORAGE_TYPE)
    List<StorageBucket> findByStorageType(@Param("storageType") StorageTypeEnum storageType);
    
    /**
     * 根据桶ID查询总文件大小
     *
     * @param bucketId 桶ID
     * @return 文件总大小
     */
    @Select(StorageSqlConstants.SUM_FILE_SIZE_BY_BUCKET_ID)
    Long sumFileSizeByBucketId(@Param("bucketId") Long bucketId);
    
    /**
     * 根据桶ID查询
     *
     * @param id 桶ID
     * @return 存储桶
     */
    @Select(StorageSqlConstants.FIND_BY_ID)
    StorageBucket findById(@Param("id") Long id);
} 