package com.lawfirm.system.service;

import com.lawfirm.common.data.service.BaseService;
import com.lawfirm.model.system.entity.SysPost;
import com.lawfirm.model.system.vo.SysPostVO;
import com.lawfirm.model.system.dto.SysPostDTO;

/**
 * 岗位服务接口
 */
public interface SysPostService extends BaseService<SysPost, SysPostVO> {
    
    /**
     * 创建岗位
     */
    SysPostVO create(SysPostDTO dto);
    
    /**
     * 更新岗位
     */
    SysPostVO update(SysPostDTO dto);
    
    /**
     * 校验岗位编码是否唯一
     */
    boolean checkPostCodeUnique(String code);
    
    /**
     * 校验岗位名称是否唯一
     */
    boolean checkPostNameUnique(String name);
} 