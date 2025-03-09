package com.lawfirm.model.system.mapper.upgrade;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lawfirm.model.system.entity.upgrade.Patch;
import org.apache.ibatis.annotations.Mapper;

/**
 * 系统补丁Mapper接口
 */
@Mapper
public interface PatchMapper extends BaseMapper<Patch> {
} 