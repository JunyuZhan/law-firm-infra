package com.lawfirm.model.auth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lawfirm.model.auth.entity.UserPosition;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户-职位关联Mapper接口
 */
@Mapper
public interface UserPositionMapper extends BaseMapper<UserPosition> {
    
    /**
     * 根据用户ID查询职位ID列表
     *
     * @param userId 用户ID
     * @return 职位ID列表
     */
    List<Long> selectPositionIdsByUserId(@Param("userId") Long userId);
    
    /**
     * 根据用户ID查询主职位ID
     *
     * @param userId 用户ID
     * @return 主职位ID
     */
    Long selectPrimaryPositionIdByUserId(@Param("userId") Long userId);
    
    /**
     * 批量插入用户职位关联
     *
     * @param userPositions 用户职位关联列表
     * @return 影响行数
     */
    int batchInsert(@Param("list") List<UserPosition> userPositions);
} 