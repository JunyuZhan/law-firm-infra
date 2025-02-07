package com.lawfirm.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lawfirm.common.data.service.impl.BaseServiceImpl;
import com.lawfirm.common.core.exception.BusinessException;
import com.lawfirm.model.system.entity.SysLawFirm;
import com.lawfirm.model.system.vo.SysLawFirmVO;
import com.lawfirm.system.mapper.SysLawFirmMapper;
import com.lawfirm.system.service.SysLawFirmService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;

/**
 * 律所服务实现类
 */
@Service
@RequiredArgsConstructor
public class SysLawFirmServiceImpl extends BaseServiceImpl<SysLawFirmMapper, SysLawFirm, SysLawFirmVO> implements SysLawFirmService {

    private final SysLawFirmMapper lawFirmMapper;

    @Override
    protected SysLawFirm createEntity() {
        return new SysLawFirm();
    }

    @Override
    protected SysLawFirmVO createVO() {
        return new SysLawFirmVO();
    }

    @Override
    public SysLawFirmVO entityToVO(SysLawFirm entity) {
        if (entity == null) {
            return null;
        }
        SysLawFirmVO vo = createVO();
        BeanUtils.copyProperties(entity, vo);
        return vo;
    }

    @Override
    public SysLawFirm voToEntity(SysLawFirmVO vo) {
        if (vo == null) {
            return null;
        }
        SysLawFirm entity = createEntity();
        BeanUtils.copyProperties(vo, entity);
        return entity;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void audit(Long id, Integer status, String remark) {
        // 校验律所是否存在
        SysLawFirm lawFirm = getById(id);
        if (lawFirm == null) {
            throw new BusinessException("律所不存在");
        }
        
        // 更新审核状态
        lawFirm.setStatus(status);
        lawFirm.setRemark(remark);
        updateById(lawFirm);
    }

    @Override
    public boolean exists(Long id) {
        return id != null && lawFirmMapper.selectById(id) != null;
    }
} 