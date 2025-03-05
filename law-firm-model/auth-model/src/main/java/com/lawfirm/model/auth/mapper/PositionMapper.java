package com.lawfirm.model.auth.mapper;

import com.lawfirm.model.auth.entity.Position;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 职位数据访问接口
 * 
 * @author lawfirm
 */
public interface PositionMapper {
    
    /**
     * 根据ID查询职位
     * 
     * @param id 职位ID
     * @return 职位实体
     */
    Position selectById(Long id);
    
    /**
     * 根据职位编码查询职位
     * 
     * @param code 职位编码
     * @return 职位实体
     */
    Position selectByCode(String code);
    
    /**
     * 查询所有职位
     * 
     * @return 职位列表
     */
    List<Position> selectAll();
    
    /**
     * 根据级别查询职位
     * 
     * @param level 职位级别
     * @return 职位列表
     */
    List<Position> selectByLevel(Integer level);
    
    /**
     * 根据部门ID查询职位
     * 
     * @param departmentId 部门ID
     * @return 职位列表
     */
    List<Position> selectByDepartmentId(Long departmentId);
    
    /**
     * 分页查询职位列表
     * 
     * @param pageNum 页码
     * @param pageSize 每页数量
     * @param name 职位名称（可选）
     * @param level 职位级别（可选）
     * @return 职位列表
     */
    List<Position> selectPage(@Param("pageNum") Integer pageNum, 
                            @Param("pageSize") Integer pageSize,
                            @Param("name") String name,
                            @Param("level") Integer level);
    
    /**
     * 查询职位总数
     * 
     * @param name 职位名称（可选）
     * @param level 职位级别（可选）
     * @return 职位总数
     */
    int selectCount(@Param("name") String name, @Param("level") Integer level);
    
    /**
     * 新增职位
     * 
     * @param position 职位实体
     * @return 影响行数
     */
    int insert(Position position);
    
    /**
     * 更新职位
     * 
     * @param position 职位实体
     * @return 影响行数
     */
    int update(Position position);
    
    /**
     * 删除职位
     * 
     * @param id 职位ID
     * @return 影响行数
     */
    int deleteById(Long id);
    
    /**
     * 批量删除职位
     * 
     * @param ids 职位ID数组
     * @return 影响行数
     */
    int deleteBatchByIds(@Param("ids") List<Long> ids);
    
    /**
     * 查询用户的职位
     * 
     * @param userId 用户ID
     * @return 职位实体
     */
    Position selectByUserId(Long userId);
} 