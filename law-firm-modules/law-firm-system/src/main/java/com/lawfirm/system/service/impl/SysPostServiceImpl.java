package com.lawfirm.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lawfirm.common.data.service.impl.BaseServiceImpl;
import com.lawfirm.common.core.exception.BusinessException;
import com.lawfirm.system.mapper.SysPostMapper;
import com.lawfirm.model.system.entity.SysPost;
import com.lawfirm.model.system.vo.SysPostVO;
import com.lawfirm.model.system.dto.SysPostDTO;
import com.lawfirm.system.service.SysPostService;
import lombok.RequiredArgsConstructor;
import com.lawfirm.common.util.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 岗位服务实现类
 */
@Service
@RequiredArgsConstructor
public class SysPostServiceImpl extends BaseServiceImpl<SysPostMapper, SysPost, SysPostVO> implements SysPostService {

    private final SysPostMapper postMapper;

    @Override
    protected SysPost createEntity() {
        return new SysPost();
    }

    @Override
    protected SysPostVO createVO() {
        return new SysPostVO();
    }

    private SysPostVO dtoToVO(SysPostDTO dto) {
        if (dto == null) {
            return null;
        }
        SysPostVO vo = createVO();
        BeanUtils.copyProperties(dto, vo);
        return vo;
    }

    private SysPost dtoToEntity(SysPostDTO dto) {
        if (dto == null) {
            return null;
        }
        SysPost entity = createEntity();
        BeanUtils.copyProperties(dto, entity);
        return entity;
    }

    @Override
    public SysPostVO entityToVO(SysPost entity) {
        if (entity == null) {
            return null;
        }
        SysPostVO vo = createVO();
        BeanUtils.copyProperties(entity, vo);
        return vo;
    }

    @Override
    public SysPost voToEntity(SysPostVO vo) {
        if (vo == null) {
            return null;
        }
        SysPost entity = createEntity();
        BeanUtils.copyProperties(vo, entity);
        return entity;
    }

    @Override
    public boolean checkPostCodeUnique(String code) {
        return postMapper.selectCount(new LambdaQueryWrapper<SysPost>()
                .eq(SysPost::getCode, code)) == 0;
    }

    @Override
    public boolean checkPostNameUnique(String name) {
        return postMapper.selectCount(new LambdaQueryWrapper<SysPost>()
                .eq(SysPost::getName, name)) == 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SysPostVO create(SysPostDTO dto) {
        // 检查岗位编码是否已存在
        if (!checkPostCodeUnique(dto.getCode())) {
            throw new BusinessException("岗位编码已存在");
        }
        
        // 检查岗位名称是否已存在
        if (!checkPostNameUnique(dto.getName())) {
            throw new BusinessException("岗位名称已存在");
        }
        
        SysPost entity = dtoToEntity(dto);
        save(entity);
        return entityToVO(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SysPostVO update(SysPostDTO dto) {
        // 检查岗位是否存在
        SysPost existingPost = getById(dto.getId());
        if (existingPost == null) {
            throw new BusinessException("岗位不存在");
        }

        // 检查岗位编码是否已被其他岗位使用
        if (!existingPost.getCode().equals(dto.getCode()) && !checkPostCodeUnique(dto.getCode())) {
            throw new BusinessException("岗位编码已存在");
        }

        // 检查岗位名称是否已被其他岗位使用
        if (!existingPost.getName().equals(dto.getName()) && !checkPostNameUnique(dto.getName())) {
            throw new BusinessException("岗位名称已存在");
        }

        SysPost entity = dtoToEntity(dto);
        updateById(entity);
        return entityToVO(entity);
    }
}