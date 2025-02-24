package com.lawfirm.model.storage.service;

import com.lawfirm.model.base.service.BaseService;
import com.lawfirm.model.storage.dto.bucket.BucketCreateDTO;
import com.lawfirm.model.storage.entity.bucket.StorageBucket;
import com.lawfirm.model.storage.vo.BucketVO;

/**
 * 存储桶服务接口
 */
public interface BucketService extends BaseService<StorageBucket> {

    /**
     * 创建存储桶
     *
     * @param createDTO 创建参数
     * @return 存储桶视图对象
     */
    BucketVO create(BucketCreateDTO createDTO);

    /**
     * 更新存储桶配置
     *
     * @param id     存储桶ID
     * @param config 配置信息
     * @return 是否成功
     */
    boolean updateConfig(Long id, String config);

    /**
     * 更新存储桶访问密钥
     *
     * @param id        存储桶ID
     * @param accessKey 访问密钥
     * @param secretKey 密钥密文
     * @return 是否成功
     */
    boolean updateAccessKey(Long id, String accessKey, String secretKey);

    /**
     * 更新存储桶状态
     *
     * @param id     存储桶ID
     * @param status 状态（0：无效，1：有效）
     * @return 是否成功
     */
    boolean updateStatus(Long id, Integer status);

    /**
     * 获取存储桶使用情况
     *
     * @param id 存储桶ID
     * @return 存储桶视图对象
     */
    BucketVO getUsage(Long id);

    /**
     * 清空存储桶
     *
     * @param id 存储桶ID
     * @return 是否成功
     */
    boolean clear(Long id);

    /**
     * 检查存储桶名称是否可用
     *
     * @param bucketName 存储桶名称
     * @return 是否可用
     */
    boolean checkNameAvailable(String bucketName);
} 