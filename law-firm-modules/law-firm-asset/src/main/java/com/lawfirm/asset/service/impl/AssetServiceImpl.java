package com.lawfirm.asset.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lawfirm.asset.dto.request.AssetAddRequest;
import com.lawfirm.asset.dto.request.AssetQueryRequest;
import com.lawfirm.asset.dto.request.AssetUpdateRequest;
import com.lawfirm.asset.dto.response.AssetResponse;
import com.lawfirm.asset.entity.Asset;
import com.lawfirm.asset.mapper.AssetMapper;
import com.lawfirm.asset.service.IAssetService;
import com.lawfirm.common.core.exception.BusinessException;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

/**
 * 资产服务实现类
 */
@Service
public class AssetServiceImpl extends ServiceImpl<AssetMapper, Asset> implements IAssetService {

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long addAsset(AssetAddRequest request) {
        // 检查资产编号是否已存在
        if (checkAssetNoExists(request.getAssetNo())) {
            throw new BusinessException("资产编号已存在");
        }
        
        // 转换并保存实体
        Asset asset = new Asset();
        BeanUtils.copyProperties(request, asset);
        save(asset);
        
        return asset.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateAsset(AssetUpdateRequest request) {
        // 检查资产是否存在
        Asset asset = getById(request.getId());
        if (asset == null) {
            throw new BusinessException("资产不存在");
        }
        
        // 转换并更新实体
        BeanUtils.copyProperties(request, asset);
        updateById(asset);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteAsset(Long id) {
        // 检查资产是否存在
        if (!removeById(id)) {
            throw new BusinessException("资产不存在");
        }
    }

    @Override
    public IPage<AssetResponse> pageAssets(AssetQueryRequest request) {
        // 构建查询条件
        LambdaQueryWrapper<Asset> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(StringUtils.hasText(request.getAssetNo()), Asset::getAssetNo, request.getAssetNo())
                .like(StringUtils.hasText(request.getAssetName()), Asset::getAssetName, request.getAssetName())
                .eq(request.getAssetType() != null, Asset::getAssetType, request.getAssetType())
                .eq(request.getStatus() != null, Asset::getStatus, request.getStatus())
                .eq(request.getUserId() != null, Asset::getUserId, request.getUserId())
                .orderByDesc(Asset::getUpdateTime);
        
        // 执行分页查询
        Page<Asset> page = new Page<>(request.getPageNum(), request.getPageSize());
        Page<Asset> assetPage = page(page, wrapper);
        
        // 转换响应结果
        return assetPage.convert(this::convertToResponse);
    }

    @Override
    public AssetResponse getAssetById(Long id) {
        Asset asset = getById(id);
        if (asset == null) {
            throw new BusinessException("资产不存在");
        }
        return convertToResponse(asset);
    }

    /**
     * 检查资产编号是否已存在
     */
    private boolean checkAssetNoExists(String assetNo) {
        LambdaQueryWrapper<Asset> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Asset::getAssetNo, assetNo);
        return count(wrapper) > 0;
    }

    /**
     * 转换为响应对象
     */
    private AssetResponse convertToResponse(Asset asset) {
        if (asset == null) {
            return null;
        }
        AssetResponse response = new AssetResponse();
        BeanUtils.copyProperties(asset, response);
        
        // 设置类型名称
        response.setAssetTypeName(getAssetTypeName(asset.getAssetType()));
        // 设置状态名称
        response.setStatusName(getStatusName(asset.getStatus()));
        // TODO: 设置使用人姓名，需要调用用户服务
        
        return response;
    }

    /**
     * 获取资产类型名称
     */
    private String getAssetTypeName(Integer assetType) {
        if (assetType == null) {
            return null;
        }
        switch (assetType) {
            case 1: return "办公设备";
            case 2: return "电子设备";
            case 3: return "家具";
            case 4: return "其他";
            default: return null;
        }
    }

    /**
     * 获取状态名称
     */
    private String getStatusName(Integer status) {
        if (status == null) {
            return null;
        }
        switch (status) {
            case 1: return "在用";
            case 2: return "闲置";
            case 3: return "维修";
            case 4: return "报废";
            default: return null;
        }
    }
}
