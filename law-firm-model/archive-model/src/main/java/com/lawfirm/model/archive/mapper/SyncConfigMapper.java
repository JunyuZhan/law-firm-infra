package com.lawfirm.model.archive.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lawfirm.model.archive.entity.SyncConfig;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * 同步配置Mapper接口
 */
@Mapper
public interface SyncConfigMapper extends BaseMapper<SyncConfig> {
    /**
     * 根据系统编码查询同步配置
     *
     * @param systemCode 系统编码
     * @return 同步配置
     */
    @Select("SELECT * FROM archive_sync_config WHERE system_code = #{systemCode} AND is_deleted = 0 LIMIT 1")
    SyncConfig findBySystemCode(@Param("systemCode") String systemCode);
} 