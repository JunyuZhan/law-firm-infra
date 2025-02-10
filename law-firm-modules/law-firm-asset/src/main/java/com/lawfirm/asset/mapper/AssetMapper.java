package com.lawfirm.asset.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lawfirm.asset.entity.Asset;
import org.apache.ibatis.annotations.Mapper;

/**
 * 资产Mapper接口
 */
@Mapper
public interface AssetMapper extends BaseMapper<Asset> {
}
