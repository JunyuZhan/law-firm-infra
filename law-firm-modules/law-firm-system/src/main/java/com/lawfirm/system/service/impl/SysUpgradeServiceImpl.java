package com.lawfirm.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lawfirm.common.core.exception.BusinessException;
import com.lawfirm.common.core.model.page.PageResult;
import com.lawfirm.common.data.service.impl.BaseServiceImpl;
import com.lawfirm.model.system.entity.SysUpgrade;
import com.lawfirm.system.mapper.SysUpgradeMapper;
import com.lawfirm.model.system.dto.SysUpgradeDTO;
import com.lawfirm.model.system.dto.UpgradePackageDTO;
import com.lawfirm.model.system.vo.SysUpgradeVO;
import com.lawfirm.model.system.vo.UpgradeLogVO;
import com.lawfirm.model.system.vo.UpgradePackageVO;
import com.lawfirm.system.service.SysUpgradeService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SysUpgradeServiceImpl extends BaseServiceImpl<SysUpgradeMapper, SysUpgrade, SysUpgradeVO> implements SysUpgradeService {

    private final SysUpgradeMapper upgradeMapper;

    @Override
    protected SysUpgradeVO createVO() {
        return new SysUpgradeVO();
    }

    @Override
    protected SysUpgrade createEntity() {
        return new SysUpgrade();
    }

    private SysUpgradeVO dtoToVO(SysUpgradeDTO dto) {
        if (dto == null) {
            return null;
        }
        SysUpgradeVO vo = createVO();
        BeanUtils.copyProperties(dto, vo);
        return vo;
    }

    private SysUpgrade dtoToEntity(SysUpgradeDTO dto) {
        if (dto == null) {
            return null;
        }
        SysUpgrade entity = createEntity();
        BeanUtils.copyProperties(dto, entity);
        return entity;
    }

    @Override
    public SysUpgradeVO entityToVO(SysUpgrade entity) {
        if (entity == null) {
            return null;
        }
        SysUpgradeVO vo = createVO();
        BeanUtils.copyProperties(entity, vo);
        return vo;
    }

    @Override
    public SysUpgrade voToEntity(SysUpgradeVO vo) {
        if (vo == null) {
            return null;
        }
        SysUpgrade entity = createEntity();
        BeanUtils.copyProperties(vo, entity);
        return entity;
    }

    @Override
    public PageResult<SysUpgradeVO> pageVO(Page<SysUpgrade> page, QueryWrapper<SysUpgrade> wrapper) {
        Page<SysUpgrade> upgradePage = page(page, wrapper);
        List<SysUpgradeVO> records = entityListToVOList(upgradePage.getRecords());
        return new PageResult<>(records, upgradePage.getTotal());
    }

    @Override
    public List<SysUpgradeVO> listVO(QueryWrapper<SysUpgrade> wrapper) {
        List<SysUpgrade> list = list(wrapper);
        return entityListToVOList(list);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SysUpgradeVO upload(UpgradePackageDTO packageDTO) {
        // TODO: 实现上传功能
        return null;
    }

    @Override
    public PageResult<SysUpgradeVO> page(Integer pageNum, Integer pageSize) {
        Page<SysUpgrade> page = new Page<>(pageNum, pageSize);
        return pageVO(page, new QueryWrapper<>());
    }

    @Override
    public void execute(Long id) {
        // 校验升级包是否存在
        if (!exists(id)) {
            throw new BusinessException("升级包不存在");
        }
        // TODO: 实现执行功能
    }

    @Override
    public PageResult<SysUpgradeVO> log(Integer pageNum, Integer pageSize) {
        // TODO: 实现分页查询升级日志功能
        return null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public UpgradePackageVO uploadPackage(MultipartFile file, UpgradePackageDTO dto) {
        if (file == null || file.isEmpty()) {
            throw new BusinessException("升级包文件不能为空");
        }
        // TODO: 实现升级包上传功能
        return null;
    }

    @Override
    public Page<UpgradePackageVO> getPackages(Page<UpgradePackageVO> page) {
        // TODO: 实现获取升级包列表功能
        return null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void executeUpgrade(Long packageId) {
        // 校验升级包是否存在
        if (!exists(packageId)) {
            throw new BusinessException("升级包不存在");
        }
        // TODO: 实现执行升级功能
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void rollbackUpgrade(Long packageId) {
        // 校验升级包是否存在
        if (!exists(packageId)) {
            throw new BusinessException("升级包不存在");
        }
        // TODO: 实现回滚升级功能
    }

    @Override
    public List<UpgradeLogVO> getLogs(Long packageId) {
        // 校验升级包是否存在
        if (!exists(packageId)) {
            throw new BusinessException("升级包不存在");
        }
        // TODO: 实现获取升级日志功能
        return null;
    }

    @Override
    public boolean exists(Long id) {
        return id != null && upgradeMapper.selectById(id) != null;
    }

    @Override
    public SysUpgradeVO toUpgradeVO(SysUpgradeDTO dto) {
        return dtoToVO(dto);
    }

    @Override
    public List<SysUpgradeVO> toUpgradeVOList(List<SysUpgradeDTO> dtoList) {
        if (dtoList == null) {
            return null;
        }
        return dtoList.stream()
                .map(this::dtoToVO)
                .toList();
    }
}