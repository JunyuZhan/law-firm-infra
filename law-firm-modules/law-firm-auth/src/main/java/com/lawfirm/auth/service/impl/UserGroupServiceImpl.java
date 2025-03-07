package com.lawfirm.auth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lawfirm.auth.exception.BusinessException;
import com.lawfirm.model.auth.dto.usergroup.UserGroupCreateDTO;
import com.lawfirm.model.auth.dto.usergroup.UserGroupQueryDTO;
import com.lawfirm.model.auth.dto.usergroup.UserGroupUpdateDTO;
import com.lawfirm.model.auth.entity.UserGroup;
import com.lawfirm.model.auth.mapper.UserGroupMapper;
import com.lawfirm.model.auth.service.UserGroupService;
import com.lawfirm.model.auth.vo.UserGroupVO;
import com.lawfirm.model.auth.vo.UserVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import com.lawfirm.common.util.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 用户组服务实现类
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserGroupServiceImpl extends ServiceImpl<UserGroupMapper, UserGroup> implements UserGroupService {

    private final UserGroupMapper userGroupMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createUserGroup(UserGroupCreateDTO createDTO) {
        // 校验用户组编码唯一性
        UserGroup existGroup = this.getByCode(createDTO.getCode());
        if (existGroup != null) {
            throw new BusinessException("用户组编码已存在");
        }

        // 创建用户组
        UserGroup userGroup = new UserGroup();
        BeanUtils.copyProperties(createDTO, userGroup, UserGroupCreateDTO.class, UserGroup.class);
        
        // 设置默认值
        if (userGroup.getStatus() == null) {
            userGroup.setStatus(0); // 默认正常状态
        }
        if (userGroup.getSort() == null) {
            userGroup.setSort(0); // 默认排序值
        }
        
        // 保存用户组
        this.save(userGroup);
        
        // 处理用户关联
        if (createDTO.getUserIds() != null && !createDTO.getUserIds().isEmpty()) {
            createDTO.getUserIds().forEach(userId -> 
                this.addUserToGroup(userGroup.getId(), userId));
        }
        
        return userGroup.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateUserGroup(Long id, UserGroupUpdateDTO updateDTO) {
        // 检查用户组是否存在
        UserGroup userGroup = this.getById(id);
        if (userGroup == null) {
            throw new BusinessException("用户组不存在");
        }
        
        // 创建一个新的用户组对象，然后将更新DTO的属性复制到新对象上
        UserGroup updatedGroup = new UserGroup();
        BeanUtils.copyProperties(updateDTO, updatedGroup, UserGroupUpdateDTO.class, UserGroup.class);
        // 设置ID
        updatedGroup.setId(id);
        
        // 手动将更新对象上的非null字段拷贝到原始对象
        if (updatedGroup.getName() != null) {
            userGroup.setName(updatedGroup.getName());
        }
        if (updatedGroup.getCode() != null) {
            userGroup.setCode(updatedGroup.getCode());
        }
        if (updatedGroup.getDescription() != null) {
            userGroup.setDescription(updatedGroup.getDescription());
        }
        if (updatedGroup.getParentId() != null) {
            userGroup.setParentId(updatedGroup.getParentId());
        }
        if (updatedGroup.getSort() != null) {
            userGroup.setSort(updatedGroup.getSort());
        }
        if (updatedGroup.getStatus() != null) {
            userGroup.setStatus(updatedGroup.getStatus());
        }
        
        this.updateById(userGroup);
        
        // 更新用户关联（如果提供）
        if (updateDTO.getUserIds() != null) {
            // 先移除所有关联
            userGroupMapper.removeAllUsersFromGroup(id);
            
            // 再添加新的关联
            if (!updateDTO.getUserIds().isEmpty()) {
                updateDTO.getUserIds().forEach(userId -> 
                    this.addUserToGroup(id, userId));
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteUserGroup(Long id) {
        // 检查用户组是否存在
        UserGroup userGroup = this.getById(id);
        if (userGroup == null) {
            throw new BusinessException("用户组不存在");
        }
        
        // 检查是否有子用户组
        long childCount = this.count(Wrappers.<UserGroup>lambdaQuery()
                .eq(UserGroup::getParentId, id));
        if (childCount > 0) {
            throw new BusinessException("该用户组下存在子用户组，不能删除");
        }
        
        // 删除用户关联
        userGroupMapper.removeAllUsersFromGroup(id);
        
        // 删除用户组
        this.removeById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteUserGroups(List<Long> ids) {
        ids.forEach(this::deleteUserGroup);
    }

    @Override
    public UserGroupVO getUserGroupById(Long id) {
        UserGroup userGroup = this.getById(id);
        if (userGroup == null) {
            return null;
        }
        
        return convertToVO(userGroup);
    }

    @Override
    public Page<UserGroupVO> pageUserGroups(UserGroupQueryDTO queryDTO) {
        // 构建查询条件
        LambdaQueryWrapper<UserGroup> wrapper = Wrappers.<UserGroup>lambdaQuery()
                .like(StringUtils.isNotBlank(queryDTO.getName()), UserGroup::getName, queryDTO.getName())
                .like(StringUtils.isNotBlank(queryDTO.getCode()), UserGroup::getCode, queryDTO.getCode())
                .eq(queryDTO.getParentId() != null, UserGroup::getParentId, queryDTO.getParentId())
                .eq(queryDTO.getStatus() != null, UserGroup::getStatus, queryDTO.getStatus())
                .orderByAsc(UserGroup::getSort);
        
        // 执行分页查询
        Page<UserGroup> page = new Page<>(queryDTO.getPageNum(), queryDTO.getPageSize());
        Page<UserGroup> userGroupPage = this.page(page, wrapper);
        
        // 创建结果页
        Page<UserGroupVO> resultPage = new Page<>();
        // 手动设置分页信息
        resultPage.setCurrent(userGroupPage.getCurrent());
        resultPage.setSize(userGroupPage.getSize());
        resultPage.setTotal(userGroupPage.getTotal());
        resultPage.setPages(userGroupPage.getPages());
        
        // 转换记录列表
        List<UserGroupVO> voList = userGroupPage.getRecords().stream()
                .map(this::convertToVO)
                .collect(Collectors.toList());
        resultPage.setRecords(voList);
        
        return resultPage;
    }

    @Override
    public List<UserGroupVO> listAllUserGroups() {
        List<UserGroup> list = this.list(Wrappers.<UserGroup>lambdaQuery()
                .orderByAsc(UserGroup::getSort));
        
        return list.stream()
                .map(this::convertToVO)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserGroupVO> getUserGroupTree() {
        // 获取所有用户组
        List<UserGroup> allGroups = this.list(Wrappers.<UserGroup>lambdaQuery()
                .orderByAsc(UserGroup::getSort));
        
        // 转换成VO
        List<UserGroupVO> voList = allGroups.stream()
                .map(this::convertToVO)
                .collect(Collectors.toList());
        
        // 构建map，便于查找
        Map<Long, UserGroupVO> voMap = new HashMap<>();
        voList.forEach(vo -> voMap.put(vo.getId(), vo));
        
        // 构建树形结构
        List<UserGroupVO> result = new ArrayList<>();
        for (UserGroupVO vo : voList) {
            if (vo.getParentId() == null || vo.getParentId() == 0) {
                // 根节点
                result.add(vo);
            } else {
                // 子节点，添加到父节点的children
                UserGroupVO parent = voMap.get(vo.getParentId());
                if (parent != null) {
                    if (parent.getChildren() == null) {
                        parent.setChildren(new ArrayList<>());
                    }
                    parent.getChildren().add(vo);
                }
            }
        }
        
        return result;
    }

    @Override
    public UserGroup getByCode(String code) {
        return this.getOne(Wrappers.<UserGroup>lambdaQuery()
                .eq(UserGroup::getCode, code));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateStatus(Long id, Integer status) {
        // 检查用户组是否存在
        UserGroup userGroup = this.getById(id);
        if (userGroup == null) {
            throw new BusinessException("用户组不存在");
        }
        
        // 更新状态
        userGroup.setStatus(status);
        this.updateById(userGroup);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addUserToGroup(Long userGroupId, Long userId) {
        // 检查用户组是否存在
        UserGroup userGroup = this.getById(userGroupId);
        if (userGroup == null) {
            throw new BusinessException("用户组不存在");
        }
        
        // 添加用户到用户组
        userGroupMapper.addUserToGroup(userGroupId, userId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addUsersToGroup(Long userGroupId, List<Long> userIds) {
        // 检查用户组是否存在
        UserGroup userGroup = this.getById(userGroupId);
        if (userGroup == null) {
            throw new BusinessException("用户组不存在");
        }
        
        // 批量添加用户到用户组
        userIds.forEach(userId -> userGroupMapper.addUserToGroup(userGroupId, userId));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeUserFromGroup(Long userGroupId, Long userId) {
        // 检查用户组是否存在
        UserGroup userGroup = this.getById(userGroupId);
        if (userGroup == null) {
            throw new BusinessException("用户组不存在");
        }
        
        // 从用户组移除用户
        userGroupMapper.removeUserFromGroup(userGroupId, userId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeUsersFromGroup(Long userGroupId, List<Long> userIds) {
        // 检查用户组是否存在
        UserGroup userGroup = this.getById(userGroupId);
        if (userGroup == null) {
            throw new BusinessException("用户组不存在");
        }
        
        // 批量从用户组移除用户
        userIds.forEach(userId -> userGroupMapper.removeUserFromGroup(userGroupId, userId));
    }

    @Override
    public List<UserGroupVO> listUserGroupsByUserId(Long userId) {
        // 查询用户所属的用户组
        List<UserGroup> userGroups = userGroupMapper.selectByUserId(userId);
        
        return userGroups.stream()
                .map(this::convertToVO)
                .collect(Collectors.toList());
    }
    
    /**
     * 将实体转换为VO
     */
    private UserGroupVO convertToVO(UserGroup userGroup) {
        if (userGroup == null) {
            return null;
        }
        
        UserGroupVO vo = new UserGroupVO();
        BeanUtils.copyProperties(userGroup, vo, UserGroup.class, UserGroupVO.class);
        
        // 设置状态名称
        vo.setStatusName(userGroup.getStatus() == 0 ? "正常" : "禁用");
        
        // 设置父级名称（如果有父级）
        if (userGroup.getParentId() != null && userGroup.getParentId() > 0) {
            UserGroup parent = this.getById(userGroup.getParentId());
            if (parent != null) {
                vo.setParentName(parent.getName());
            }
        }
        
        // 转换创建时间和更新时间格式
        // 这里假设BaseVO已经处理了日期格式化，如果没有，可以在这里处理
        
        return vo;
    }
}