package com.lawfirm.asset.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lawfirm.asset.dto.request.AssetAddRequest;
import com.lawfirm.asset.dto.request.AssetQueryRequest;
import com.lawfirm.asset.dto.request.AssetUpdateRequest;
import com.lawfirm.asset.dto.response.AssetResponse;
import com.lawfirm.asset.entity.Asset;

/**
 * 资产服务接口
 */
public interface IAssetService extends IService<Asset> {

    /**
     * 添加资产
     *
     * @param request 添加请求
     * @return 资产ID
     */
    Long addAsset(AssetAddRequest request);

    /**
     * 更新资产
     *
     * @param request 更新请求
     */
    void updateAsset(AssetUpdateRequest request);

    /**
     * 删除资产
     *
     * @param id 资产ID
     */
    void deleteAsset(Long id);

    /**
     * 分页查询资产
     *
     * @param request 查询请求
     * @return 分页结果
     */
    IPage<AssetResponse> pageAssets(AssetQueryRequest request);

    /**
     * 获取资产详情
     *
     * @param id 资产ID
     * @return 资产详情
     */
    AssetResponse getAssetById(Long id);
}
