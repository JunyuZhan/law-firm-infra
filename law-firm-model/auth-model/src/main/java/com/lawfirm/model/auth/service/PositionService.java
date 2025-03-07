package com.lawfirm.model.auth.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lawfirm.model.auth.dto.position.PositionCreateDTO;
import com.lawfirm.model.auth.dto.position.PositionQueryDTO;
import com.lawfirm.model.auth.dto.position.PositionUpdateDTO;
import com.lawfirm.model.auth.vo.PositionVO;

import java.util.List;

/**
 * 职位服务接口
 */
public interface PositionService {
    
    /**
     * 创建职位
     *
     * @param createDTO 创建参数
     * @return 职位ID
     */
    Long createPosition(PositionCreateDTO createDTO);
    
    /**
     * 更新职位
     *
     * @param id        职位ID
     * @param updateDTO 更新参数
     */
    void updatePosition(Long id, PositionUpdateDTO updateDTO);
    
    /**
     * 删除职位
     *
     * @param id 职位ID
     */
    void deletePosition(Long id);
    
    /**
     * 批量删除职位
     *
     * @param ids 职位ID列表
     */
    void deletePositions(List<Long> ids);
    
    /**
     * 获取职位详情
     *
     * @param id 职位ID
     * @return 职位视图对象
     */
    PositionVO getPositionById(Long id);
    
    /**
     * 分页查询职位列表
     *
     * @param queryDTO 查询条件
     * @return 职位分页数据
     */
    Page<PositionVO> pagePositions(PositionQueryDTO queryDTO);
    
    /**
     * 获取所有职位列表
     *
     * @return 职位列表
     */
    List<PositionVO> listAllPositions();
} 