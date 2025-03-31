package com.lawfirm.model.system.mapper.upgrade;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lawfirm.model.system.entity.upgrade.Patch;
import com.lawfirm.model.system.constant.SystemSqlConstants;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 系统补丁Mapper接口
 */
@Mapper
public interface PatchMapper extends BaseMapper<Patch> {
    
    /**
     * 查询待执行的升级脚本
     *
     * @return 待执行脚本列表
     */
    @Select(SystemSqlConstants.Upgrade.SELECT_PENDING_SCRIPTS)
    List<Patch> selectPendingScripts();
} 