package com.lawfirm.auth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lawfirm.model.auth.mapper.UserMapper;
import com.lawfirm.auth.utils.SecurityUtils;
import com.lawfirm.model.auth.dto.user.UserCreateDTO;
import com.lawfirm.model.auth.dto.user.UserQueryDTO;
import com.lawfirm.model.auth.dto.user.UserUpdateDTO;
import com.lawfirm.model.auth.entity.User;
import com.lawfirm.model.auth.service.UserService;
import com.lawfirm.model.auth.vo.UserInfoVO;
import com.lawfirm.model.auth.vo.UserVO;
import com.lawfirm.auth.security.details.SecurityUserDetails;
import com.lawfirm.model.base.service.impl.BaseServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.context.annotation.Primary;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 认证用户服务实现类
 */
@Slf4j
@Service("userDetailsService")
@Primary
@RequiredArgsConstructor
public class UserServiceImpl extends BaseServiceImpl<UserMapper, User> implements UserService, UserDetailsService {
    
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    
    /**
     * 获取当前登录用户ID
     */
    public Long getCurrentUserId() {
        return SecurityUtils.getCurrentUserId();
    }
    
    @Override
    public Long createUser(UserCreateDTO createDTO) {
        User user = new User();
        BeanUtils.copyProperties(createDTO, user);
        // 加密密码
        user.setPassword(passwordEncoder.encode(createDTO.getPassword()));
        save(user);
        return user.getId();
    }
    
    @Override
    public void updateUser(Long id, UserUpdateDTO updateDTO) {
        User user = getById(id);
        if (user == null) {
            throw new RuntimeException("用户不存在");
        }
        BeanUtils.copyProperties(updateDTO, user);
        updateById(user);
    }
    
    @Override
    public void deleteUser(Long id) {
        removeById(id);
    }
    
    @Override
    public void deleteUsers(List<Long> ids) {
        removeByIds(ids);
    }
    
    @Override
    public UserVO getUserById(Long id) {
        User user = getById(id);
        if (user == null) {
            return null;
        }
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(user, userVO);
        return userVO;
    }
    
    @Override
    public Page<UserVO> pageUsers(UserQueryDTO queryDTO) {
        Page<User> page = new Page<>(queryDTO.getPageNum(), queryDTO.getPageSize());
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        // 添加查询条件
        if (queryDTO.getUsername() != null) {
            wrapper.like(User::getUsername, queryDTO.getUsername());
        }
        if (queryDTO.getPhone() != null) {
            wrapper.like(User::getMobile, queryDTO.getPhone());
        }
        if (queryDTO.getStatus() != null) {
            wrapper.eq(User::getStatus, queryDTO.getStatus());
        }
        
        Page<User> userPage = page(page, wrapper);
        Page<UserVO> userVOPage = new Page<>();
        BeanUtils.copyProperties(userPage, userVOPage, "records");
        
        List<UserVO> userVOList = userPage.getRecords().stream().map(user -> {
            UserVO userVO = new UserVO();
            BeanUtils.copyProperties(user, userVO);
            return userVO;
        }).collect(Collectors.toList());
        
        userVOPage.setRecords(userVOList);
        return userVOPage;
    }
    
    @Override
    public User getByUsername(String username) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername, username);
        return getOne(wrapper);
    }
    
    @Override
    public void updatePassword(Long id, String oldPassword, String newPassword) {
        User user = getById(id);
        if (user == null) {
            throw new RuntimeException("用户不存在");
        }
        
        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new RuntimeException("原密码不正确");
        }
        
        user.setPassword(passwordEncoder.encode(newPassword));
        updateById(user);
    }
    
    @Override
    public String resetPassword(Long id) {
        User user = getById(id);
        if (user == null) {
            throw new RuntimeException("用户不存在");
        }
        
        // 生成随机密码
        String password = "123456"; // 简化处理，实际应生成随机密码
        user.setPassword(passwordEncoder.encode(password));
        updateById(user);
        
        return password;
    }
    
    @Override
    public void updateStatus(Long id, Integer status) {
        User user = getById(id);
        if (user == null) {
            throw new RuntimeException("用户不存在");
        }
        
        user.setStatus(status);
        updateById(user);
    }
    
    @Override
    public List<String> getUserPermissions(Long id) {
        // 简化处理，实际应从数据库查询
        return new ArrayList<>();
    }
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = getByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("用户不存在");
        }
        
        // 获取用户权限列表
        List<String> permissions = getUserPermissions(user.getId());
        
        // 创建并返回SecurityUserDetails对象
        return new SecurityUserDetails(user, permissions);
    }
    
    @Override
    public UserDetails loadUserByEmail(String email) {
        User user = getByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("用户不存在");
        }
        
        // 获取用户权限列表
        List<String> permissions = getUserPermissions(user.getId());
        
        // 创建并返回SecurityUserDetails对象
        return new SecurityUserDetails(user, permissions);
    }
    
    @Override
    public UserDetails loadUserByMobile(String mobile) {
        User user = getByMobile(mobile);
        if (user == null) {
            throw new UsernameNotFoundException("用户不存在");
        }
        
        // 获取用户权限列表
        List<String> permissions = getUserPermissions(user.getId());
        
        // 创建并返回SecurityUserDetails对象
        return new SecurityUserDetails(user, permissions);
    }
    
    @Override
    public User getByEmail(String email) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getEmail, email);
        return getOne(wrapper);
    }
    
    @Override
    public User getByMobile(String mobile) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getMobile, mobile);
        return getOne(wrapper);
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void assignRoles(Long userId, List<Long> roleIds) {
        // 简化处理，实际应先删除用户角色关系，再添加新的关系
    }
    
    @Override
    public List<Long> getUserRoleIds(Long userId) {
        // 简化处理，实际应从数据库查询
        return new ArrayList<>();
    }
    
    @Override
    public UserInfoVO getUserInfo(Long userId) {
        User user = getById(userId);
        if (user == null) {
            return null;
        }
        
        UserInfoVO userInfoVO = new UserInfoVO();
        BeanUtils.copyProperties(user, userInfoVO);
        
        // 设置用户权限
        userInfoVO.setPermissions(getUserPermissions(userId));
        
        return userInfoVO;
    }
    
    @Override
    public boolean exists(QueryWrapper<User> queryWrapper) {
        return count(queryWrapper) > 0;
    }
    
    @Override
    public long count(QueryWrapper<User> queryWrapper) {
        return baseMapper.selectCount(queryWrapper);
    }
    
    @Override
    public Page<User> page(Page<User> page, QueryWrapper<User> queryWrapper) {
        return baseMapper.selectPage(page, queryWrapper);
    }
    
    @Override
    public List<User> list(QueryWrapper<User> queryWrapper) {
        return baseMapper.selectList(queryWrapper);
    }
    
    @Override
    public User getById(Long id) {
        return baseMapper.selectById(id);
    }

    @Override
    public boolean save(User entity) {
        return super.save(entity);
    }

    @Override
    public boolean saveBatch(List<User> entityList) {
        return super.saveBatch(entityList);
    }

    @Override
    public boolean update(User entity) {
        return super.updateById(entity);
    }

    @Override
    public boolean updateBatch(List<User> entityList) {
        return super.updateBatchById(entityList);
    }

    @Override
    public boolean remove(Long id) {
        return super.removeById(id);
    }

    @Override
    public boolean removeBatch(List<Long> idList) {
        return super.removeByIds(idList);
    }

    /**
     * 获取用户详细信息，适配vue-vben-admin
     */
    @Override
    public UserInfoVO getUserDetailInfo(Long userId) {
        // 获取基本用户信息
        UserInfoVO userInfoVO = getUserInfo(userId);
        if (userInfoVO == null) {
            return null;
        }
        
        // 获取用户实体
        User user = getById(userId);
        
        // 设置真实姓名 - 用用户名代替，实际项目应从员工表获取
        userInfoVO.setRealName(user.getUsername());
        
        // 设置头像 - 使用默认头像，实际项目应从员工表获取
        userInfoVO.setAvatar("https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
        
        // 设置用户描述
        userInfoVO.setDesc("系统用户");
        
        // 获取用户角色 - 转换为vue-vben-admin格式
        List<Map<String, String>> roleList = getUserRoleIds(userId).stream()
            .map(roleId -> {
                java.util.Map<String, String> role = new java.util.HashMap<>();
                // 从角色服务获取角色信息
                String roleName = "角色" + roleId; // 简化处理，实际应从角色服务获取
                String roleValue = "role_" + roleId;
                role.put("roleName", roleName);
                role.put("value", roleValue);
                return role;
            })
            .collect(java.util.stream.Collectors.toList());
        userInfoVO.setRoles(roleList);
        
        // 设置权限数组
        List<String> permissions = getUserPermissions(userId);
        userInfoVO.setPermissions(permissions);
        userInfoVO.setPermissionList(permissions.toArray(new String[0]));
        
        return userInfoVO;
    }
}

