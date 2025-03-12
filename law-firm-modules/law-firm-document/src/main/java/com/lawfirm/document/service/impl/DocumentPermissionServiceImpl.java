package com.lawfirm.document.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lawfirm.common.core.utils.BeanUtils;
import com.lawfirm.document.manager.security.DocumentSecurityManager;
import com.lawfirm.model.base.service.impl.BaseServiceImpl;
import com.lawfirm.model.document.dto.permission.PermissionCreateDTO;
import com.lawfirm.model.document.dto.permission.PermissionQueryDTO;
import com.lawfirm.model.document.dto.permission.PermissionUpdateDTO;
import com.lawfirm.model.document.entity.DocumentPermission;
import com.lawfirm.model.document.mapper.DocumentPermissionMapper;
import com.lawfirm.model.document.service.DocumentPermissionService;
import com.lawfirm.model.document.vo.PermissionVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 文档权限服务实现类
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class DocumentPermissionServiceImpl extends BaseServiceImpl<DocumentPermissionMapper, DocumentPermission> implements DocumentPermissionService {

    private final DocumentSecurityManager securityManager;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createPermission(PermissionCreateDTO createDTO) {
        // 1. 检查授权权限
        if (!securityManager.checkAccessPermission(createDTO.getDocumentId())) {
            throw new SecurityException("没有授权权限");
        }

        // 2. 创建权限记录
        DocumentPermission permission = BeanUtils.copyProperties(createDTO, DocumentPermission.class);
        baseMapper.insert(permission);

        return permission.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updatePermission(PermissionUpdateDTO updateDTO) {
        // 1. 检查授权权限
        DocumentPermission permission = baseMapper.selectById(updateDTO.getId());
        if (!securityManager.checkAccessPermission(permission.getDocumentId())) {
            throw new SecurityException("没有授权权限");
        }

        // 2. 更新权限
        BeanUtils.copyProperties(updateDTO, permission);
        baseMapper.updateById(permission);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deletePermission(Long id) {
        // 1. 检查授权权限
        DocumentPermission permission = baseMapper.selectById(id);
        if (!securityManager.checkAccessPermission(permission.getDocumentId())) {
            throw new SecurityException("没有授权权限");
        }

        // 2. 删除权限记录
        baseMapper.deleteById(id);
    }

    @Override
    public PermissionVO getPermission(Long id) {
        // 1. 获取权限记录
        DocumentPermission permission = baseMapper.selectById(id);
        if (permission == null) {
            return null;
        }

        // 2. 检查访问权限
        if (!securityManager.checkAccessPermission(permission.getDocumentId())) {
            throw new SecurityException("没有访问权限");
        }

        // 3. 转换为VO
        return BeanUtils.copyProperties(permission, PermissionVO.class);
    }

    @Override
    public List<PermissionVO> listPermissions(PermissionQueryDTO queryDTO) {
        // 1. 构建查询条件
        LambdaQueryWrapper<DocumentPermission> wrapper = new LambdaQueryWrapper<>();
        // TODO: 添加查询条件

        // 2. 执行查询
        List<DocumentPermission> permissions = baseMapper.selectList(wrapper);

        // 3. 转换为VO
        return BeanUtils.copyList(permissions, PermissionVO.class);
    }

    @Override
    public Page<PermissionVO> pagePermissions(PermissionQueryDTO queryDTO) {
        // 1. 构建查询条件
        LambdaQueryWrapper<DocumentPermission> wrapper = new LambdaQueryWrapper<>();
        // TODO: 添加查询条件

        // 2. 执行分页查询
        Page<DocumentPermission> page = baseMapper.selectPage(
            new Page<>(queryDTO.getPageNum(), queryDTO.getPageSize()),
            wrapper
        );

        // 3. 转换为VO
        return BeanUtils.copyPage(page, PermissionVO.class);
    }
} 