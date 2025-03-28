package com.lawfirm.core.storage.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lawfirm.common.cache.annotation.RepeatSubmit;
import com.lawfirm.common.cache.annotation.SimpleCache;
import com.lawfirm.common.security.annotation.RequiresPermissions;
import com.lawfirm.core.storage.config.StorageProperties;
import com.lawfirm.core.storage.strategy.StorageContext;
import com.lawfirm.core.storage.strategy.StorageStrategy;
import com.lawfirm.model.base.service.impl.BaseServiceImpl;
import com.lawfirm.model.storage.dto.bucket.BucketCreateDTO;
import com.lawfirm.model.storage.dto.bucket.BucketQueryDTO;
import com.lawfirm.model.storage.dto.bucket.BucketUpdateDTO;
import com.lawfirm.model.storage.entity.bucket.StorageBucket;
import com.lawfirm.model.storage.enums.StorageTypeEnum;
import com.lawfirm.model.storage.mapper.StorageBucketMapper;
import com.lawfirm.model.storage.service.BucketService;
import com.lawfirm.model.storage.vo.BucketVO;
import com.lawfirm.model.storage.vo.PageVO;

import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;

/**
 * 存储桶服务实现类
 */
@Slf4j
@Component("storageBucketServiceImpl")
@RequiredArgsConstructor
public class BucketServiceImpl extends BaseServiceImpl<StorageBucketMapper, StorageBucket> implements BucketService {

    private final StorageContext storageContext;
    private final StorageProperties storageProperties;
    
    @Override
    @Transactional
    @RepeatSubmit(interval = 5, timeUnit = TimeUnit.SECONDS)
    @RequiresPermissions("storage:bucket:create")
    public BucketVO create(BucketCreateDTO createDTO) {
        // 获取存储类型
        StorageTypeEnum storageType = createDTO.getStorageType();
        
        // 检查存储类型是否支持
        if (!storageContext.isSupported(storageType)) {
            throw new IllegalArgumentException("不支持的存储类型: " + storageType);
        }
        
        // 检查桶名称是否已存在
        QueryWrapper<StorageBucket> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("bucket_name", createDTO.getBucketName());
        if (exists(queryWrapper)) {
            throw new IllegalArgumentException("存储桶名称已存在: " + createDTO.getBucketName());
        }
        
        // 创建存储桶对象
        StorageBucket bucket = new StorageBucket();
        bucket.setBucketName(createDTO.getBucketName());
        bucket.setStorageType(storageType);
        bucket.setRemark(createDTO.getRemark());
        // 设置桶类型(如果有)
        if (createDTO.getBucketType() != null) {
            bucket.setBucketType(createDTO.getBucketType());
        }
        bucket.setMaxSize(createDTO.getMaxSize() != null ? createDTO.getMaxSize() : 0L);
        bucket.setUsedSize(0L);
        bucket.setFileCount(0L);
        bucket.setStatus(1); // 1表示正常状态
        
        // 根据存储类型设置相关配置
        switch (storageType) {
            case LOCAL:
                bucket.setDomain(storageProperties.getLocal().getUrlPrefix());
                break;
            case MINIO:
                bucket.setAccessKey(storageProperties.getMinio().getAccessKey());
                bucket.setSecretKey(storageProperties.getMinio().getSecretKey());
                bucket.setDomain(storageProperties.getMinio().getEndpoint());
                break;
            case ALIYUN_OSS:
                bucket.setAccessKey(storageProperties.getAliyunOss().getAccessKey());
                bucket.setSecretKey(storageProperties.getAliyunOss().getSecretKey());
                bucket.setDomain(storageProperties.getAliyunOss().getEndpoint());
                break;
            case TENCENT_COS:
                bucket.setAccessKey(storageProperties.getTencentCos().getSecretId());
                bucket.setSecretKey(storageProperties.getTencentCos().getSecretKey());
                bucket.setConfig("region=" + storageProperties.getTencentCos().getRegion() + 
                        ";appId=" + storageProperties.getTencentCos().getAppId());
                break;
            default:
                throw new IllegalArgumentException("不支持的存储类型: " + storageType);
        }
        
        // 保存到数据库
        save(bucket);
        
        // 在存储服务中创建桶
        StorageStrategy strategy = storageContext.getStrategy(storageType);
        boolean created = strategy.createBucket(bucket.getBucketName(), false); // 默认私有
        
        if (!created) {
            // 创建失败，删除数据库记录
            remove(bucket.getId());
            throw new RuntimeException("创建存储桶失败");
        }
        
        log.info("存储桶创建成功: {}", bucket.getBucketName());
        return convertToVO(bucket);
    }
    
    @SimpleCache(key = "'bucket_' + #bucketId", timeout = 300, timeUnit = TimeUnit.SECONDS)
    @RequiresPermissions("storage:bucket:read")
    public BucketVO getInfo(Long bucketId) {
        StorageBucket bucket = getById(bucketId);
        if (bucket == null) {
            throw new RuntimeException("存储桶不存在");
        }
        
        return convertToVO(bucket);
    }
    
    @Transactional
    @RepeatSubmit(interval = 5, timeUnit = TimeUnit.SECONDS)
    @RequiresPermissions("storage:bucket:update")
    public BucketVO update(BucketUpdateDTO updateDTO) {
        StorageBucket bucket = getById(updateDTO.getBucketId());
        if (bucket == null) {
            throw new RuntimeException("存储桶不存在");
        }
        
        // 更新可变字段
        if (updateDTO.getDescription() != null) {
            bucket.setRemark(updateDTO.getDescription());
        }
        
        if (updateDTO.getDomain() != null) {
            bucket.setDomain(updateDTO.getDomain());
        }
        
        if (updateDTO.getBucketName() != null) {
            bucket.setBucketName(updateDTO.getBucketName());
        }
        
        if (updateDTO.getBucketType() != null) {
            bucket.setBucketType(updateDTO.getBucketType());
        }
        
        // 保存更新
        update(bucket);
        
        log.info("存储桶更新成功: {}", bucket.getBucketName());
        return convertToVO(bucket);
    }
    
    @Transactional
    @RequiresPermissions("storage:bucket:delete")
    public boolean delete(Long bucketId) {
        StorageBucket bucket = getById(bucketId);
        if (bucket == null) {
            return false;
        }
        
        // 检查桶是否为空
        if (bucket.getFileCount() > 0) {
            throw new RuntimeException("存储桶非空，无法删除");
        }
        
        // 在存储服务中删除桶
        StorageStrategy strategy = storageContext.getStrategy(bucket.getStorageType());
        boolean removed = strategy.removeBucket(bucket.getBucketName());
        
        if (!removed) {
            log.warn("存储桶删除失败: {}", bucket.getBucketName());
            return false;
        }
        
        // 删除数据库记录
        return remove(bucket.getId());
    }
    
    @SimpleCache(key = "'all_buckets'", timeout = 300, timeUnit = TimeUnit.SECONDS)
    @RequiresPermissions("storage:bucket:read")
    public List<BucketVO> listAll() {
        QueryWrapper<StorageBucket> queryWrapper = new QueryWrapper<>();
        List<StorageBucket> buckets = list(queryWrapper);
        List<BucketVO> result = new ArrayList<>(buckets.size());
        
        for (StorageBucket bucket : buckets) {
            result.add(convertToVO(bucket));
        }
        
        return result;
    }
    
    @Override
    public boolean updateConfig(Long id, String config) {
        StorageBucket bucket = getById(id);
        if (bucket == null) {
            return false;
        }
        
        bucket.setConfig(config);
        return update(bucket);
    }
    
    @Override
    public boolean updateAccessKey(Long id, String accessKey, String secretKey) {
        StorageBucket bucket = getById(id);
        if (bucket == null) {
            return false;
        }
        
        bucket.setAccessKey(accessKey);
        bucket.setSecretKey(secretKey);
        return update(bucket);
    }
    
    @Override
    public boolean updateStatus(Long id, Integer status) {
        StorageBucket bucket = getById(id);
        if (bucket == null) {
            return false;
        }
        
        bucket.setStatus(status);
        return update(bucket);
    }
    
    @Override
    public BucketVO getUsage(Long id) {
        StorageBucket bucket = getById(id);
        if (bucket == null) {
            throw new RuntimeException("存储桶不存在");
        }
        
        return convertToVO(bucket);
    }
    
    @Override
    @Transactional
    public boolean clear(Long id) {
        // 这里应该添加清空存储桶的实现
        // 删除所有文件并更新使用情况
        return false;
    }
    
    @Override
    public boolean checkNameAvailable(String bucketName) {
        QueryWrapper<StorageBucket> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("bucket_name", bucketName);
        return !exists(queryWrapper);
    }
    
    @RequiresPermissions("storage:bucket:query")
    public PageVO<BucketVO> query(BucketQueryDTO queryDTO) {
        log.info("查询存储桶列表: {}", queryDTO);
        
        // 构建查询条件
        QueryWrapper<StorageBucket> wrapper = new QueryWrapper<>();
        if (queryDTO.getStorageType() != null) {
            wrapper.eq("storage_type", queryDTO.getStorageType());
        }
        if (queryDTO.getBucketType() != null) {
            wrapper.eq("bucket_type", queryDTO.getBucketType());
        }
        if (queryDTO.getBucketName() != null) {
            wrapper.like("bucket_name", queryDTO.getBucketName());
        }
        
        // 分页查询
        Page<StorageBucket> page = new Page<>(queryDTO.getPageNum(), queryDTO.getPageSize());
        page = page(page, wrapper);
        
        // 转换为VO列表
        List<BucketVO> voList = new ArrayList<>();
        for (StorageBucket bucket : page.getRecords()) {
            voList.add(convertToVO(bucket));
        }
        
        return new PageVO<>(voList, page.getTotal(), queryDTO.getPageNum(), queryDTO.getPageSize());
    }
    
    @Override
    @RequiresPermissions("storage:bucket:list")
    public List<StorageBucket> list(QueryWrapper<StorageBucket> wrapper) {
        log.info("获取存储桶列表");
        return super.list(wrapper);
    }
    
    @Override
    @RequiresPermissions("storage:bucket:list")
    public Page<StorageBucket> page(Page<StorageBucket> page, QueryWrapper<StorageBucket> wrapper) {
        return super.page(page, wrapper);
    }
    
    /**
     * 将桶对象转换为VO对象
     */
    private BucketVO convertToVO(StorageBucket bucket) {
        if (bucket == null) {
            return null;
        }
        
        BucketVO vo = new BucketVO();
        vo.setId(bucket.getId());
        vo.setBucketName(bucket.getBucketName());
        vo.setStorageType(bucket.getStorageType());
        if (bucket.getBucketType() != null) {
            vo.setBucketType(bucket.getBucketType());
        }
        vo.setRemark(bucket.getRemark());
        vo.setDomain(bucket.getDomain());
        vo.setMaxSize(bucket.getMaxSize());
        vo.setUsedSize(bucket.getUsedSize());
        vo.setFileCount(bucket.getFileCount());
        vo.setStatus(bucket.getStatus());
        vo.setCreateTime(bucket.getCreateTime());
        vo.setUpdateTime(bucket.getUpdateTime());
        vo.setCreateBy(bucket.getCreateBy());
        vo.setUpdateBy(bucket.getUpdateBy());
        
        return vo;
    }
} 