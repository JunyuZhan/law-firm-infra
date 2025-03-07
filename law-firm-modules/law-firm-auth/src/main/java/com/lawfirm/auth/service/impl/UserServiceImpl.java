package com.lawfirm.auth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lawfirm.model.auth.mapper.UserMapper;
import com.lawfirm.model.auth.service.UserService;
import com.lawfirm.model.auth.service.UserRoleService;
import com.lawfirm.model.auth.dto.user.UserCreateDTO;
import com.lawfirm.model.auth.dto.user.UserQueryDTO;
import com.lawfirm.model.auth.dto.user.UserUpdateDTO;
import com.lawfirm.model.auth.entity.User;
import com.lawfirm.model.auth.entity.UserRole;
import com.lawfirm.model.auth.vo.UserVO;
import com.lawfirm.common.core.exception.BusinessException;
import com.lawfirm.common.util.BeanUtils;
import com.lawfirm.common.security.crypto.SensitiveDataService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRoleService userRoleService;
    private final SensitiveDataService sensitiveDataService;

    @Override
    @Transactional
    public Long createUser(UserCreateDTO createDTO) {
        // 记录创建用户操作，脱敏敏感信息
        String maskedMobile = createDTO.getMobile() != null ? 
            sensitiveDataService.maskPhoneNumber(createDTO.getMobile()) : null;
        String maskedEmail = createDTO.getEmail() != null ? 
            sensitiveDataService.maskEmail(createDTO.getEmail()) : null;
        log.info("创建用户: {}, 手机号: {}, 邮箱: {}", 
                createDTO.getUsername(), maskedMobile, maskedEmail);
        
        // 检查用户名是否已存在
        if (getByUsername(createDTO.getUsername()) != null) {
            throw new BusinessException("用户名已存在");
        }
        
        // 检查手机号是否已存在
        if (StringUtils.isNotBlank(createDTO.getMobile()) && 
            baseMapper.selectByPhone(createDTO.getMobile()) != null) {
            throw new BusinessException("手机号已存在");
        }
        
        // 检查邮箱是否已存在
        if (StringUtils.isNotBlank(createDTO.getEmail()) && 
            baseMapper.selectByEmail(createDTO.getEmail()) != null) {
            throw new BusinessException("邮箱已存在");
        }

        User user = new User();
        BeanUtils.copyProperties(createDTO, user, UserCreateDTO.class, User.class);
        // 设置默认密码
        if (StringUtils.isBlank(user.getPassword())) {
            user.setPassword(RandomStringUtils.randomAlphanumeric(8));
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setStatus(0); // 默认正常状态
        user.setPasswordExpireTime(LocalDateTime.now().plusDays(90)); // 90天后密码过期

        save(user);
        
        // 分配角色
        if (createDTO.getRoleIds() != null && !createDTO.getRoleIds().isEmpty()) {
            assignRoles(user.getId(), createDTO.getRoleIds());
        }
        
        return user.getId();
    }

    @Override
    @Transactional
    public void updateUser(Long id, UserUpdateDTO updateDTO) {
        User user = getById(id);
        if (user == null) {
            throw new BusinessException("用户不存在");
        }

        // 检查用户名是否已存在
        if (StringUtils.isNotBlank(updateDTO.getUsername()) && 
            !Objects.equals(user.getUsername(), updateDTO.getUsername()) && 
            getByUsername(updateDTO.getUsername()) != null) {
            throw new BusinessException("用户名已存在");
        }
        
        // 检查手机号是否已存在
        if (StringUtils.isNotBlank(updateDTO.getMobile()) && 
            !Objects.equals(user.getMobile(), updateDTO.getMobile()) && 
            baseMapper.selectByPhone(updateDTO.getMobile()) != null) {
            throw new BusinessException("手机号已存在");
        }
        
        // 检查邮箱是否已存在
        if (StringUtils.isNotBlank(updateDTO.getEmail()) && 
            !Objects.equals(user.getEmail(), updateDTO.getEmail()) && 
            baseMapper.selectByEmail(updateDTO.getEmail()) != null) {
            throw new BusinessException("邮箱已存在");
        }

        // 创建新对象，拷贝非空属性
        User updatedUser = new User();
        BeanUtils.copyProperties(updateDTO, updatedUser, UserUpdateDTO.class, User.class);
        // 手动设置ID
        updatedUser.setId(id);
        
        // 手动将更新对象上的非null字段拷贝到原始对象
        if (updatedUser.getUsername() != null) {
            user.setUsername(updatedUser.getUsername());
        }
        if (updatedUser.getRealName() != null) {
            user.setRealName(updatedUser.getRealName());
        }
        if (updatedUser.getNickname() != null) {
            user.setNickname(updatedUser.getNickname());
        }
        if (updatedUser.getMobile() != null) {
            user.setMobile(updatedUser.getMobile());
        }
        if (updatedUser.getEmail() != null) {
            user.setEmail(updatedUser.getEmail());
        }
        if (updatedUser.getGender() != null) {
            user.setGender(updatedUser.getGender());
        }
        if (updatedUser.getAvatar() != null) {
            user.setAvatar(updatedUser.getAvatar());
        }
        if (updatedUser.getBirthday() != null) {
            user.setBirthday(updatedUser.getBirthday());
        }
        if (updatedUser.getPositionId() != null) {
            user.setPositionId(updatedUser.getPositionId());
        }
        if (updatedUser.getDepartmentId() != null) {
            user.setDepartmentId(updatedUser.getDepartmentId());
        }
        if (updatedUser.getUserType() != null) {
            user.setUserType(updatedUser.getUserType());
        }
        if (updatedUser.getStatus() != null) {
            user.setStatus(updatedUser.getStatus());
        }
        if (updatedUser.getRemark() != null) {
            user.setRemark(updatedUser.getRemark());
        }
        
        updateById(user);
        
        // 更新角色
        if (updateDTO.getRoleIds() != null) {
            assignRoles(id, updateDTO.getRoleIds());
        }
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        // 删除用户角色关联
        userRoleService.remove(new LambdaQueryWrapper<UserRole>()
            .eq(UserRole::getUserId, id));
        // 删除用户
        removeById(id);
    }

    @Override
    @Transactional
    public void deleteUsers(List<Long> ids) {
        // 删除用户角色关联
        userRoleService.remove(new LambdaQueryWrapper<UserRole>()
            .in(UserRole::getUserId, ids));
        // 删除用户
        removeBatchByIds(ids);
    }

    @Override
    public UserVO getUserById(Long id) {
        User user = getById(id);
        if (user == null) {
            return null;
        }
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(user, userVO, User.class, UserVO.class);
        userVO.setRoleIds(getUserRoleIds(id));
        return userVO;
    }

    @Override
    public Page<UserVO> pageUsers(UserQueryDTO queryDTO) {
        Page<User> page = new Page<>(queryDTO.getPageNum(), queryDTO.getPageSize());
        
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<User>()
            .like(StringUtils.isNotBlank(queryDTO.getUsername()), 
                  User::getUsername, queryDTO.getUsername())
            .like(StringUtils.isNotBlank(queryDTO.getRealName()), 
                  User::getRealName, queryDTO.getRealName())
            .like(StringUtils.isNotBlank(queryDTO.getMobile()), 
                  User::getMobile, queryDTO.getMobile())
            .eq(queryDTO.getStatus() != null, 
                User::getStatus, queryDTO.getStatus())
            .eq(queryDTO.getUserType() != null, 
                User::getUserType, queryDTO.getUserType())
            .eq(queryDTO.getPositionId() != null, 
                User::getPositionId, queryDTO.getPositionId())
            .orderByDesc(User::getCreateTime);

        Page<User> userPage = page(page, wrapper);
        
        return userPage.convert(user -> {
            UserVO userVO = new UserVO();
            BeanUtils.copyProperties(user, userVO, User.class, UserVO.class);
            userVO.setRoleIds(getUserRoleIds(user.getId()));
            return userVO;
        });
    }

    @Override
    public User getByUsername(String username) {
        return baseMapper.selectByUsername(username);
    }

    @Override
    @Transactional
    public void updatePassword(Long id, String oldPassword, String newPassword) {
        User user = getById(id);
        if (user == null) {
            throw new BusinessException("用户不存在");
        }
        
        // 验证旧密码
        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new BusinessException("旧密码错误");
        }
        
        // 更新密码
        user.setPassword(passwordEncoder.encode(newPassword));
        user.setPasswordExpireTime(LocalDateTime.now().plusDays(90)); // 重置密码过期时间
        updateById(user);
    }

    @Override
    @Transactional
    public String resetPassword(Long id) {
        User user = getById(id);
        if (user == null) {
            throw new BusinessException("用户不存在");
        }
        
        // 生成随机密码
        String newPassword = RandomStringUtils.randomAlphanumeric(8);
        user.setPassword(passwordEncoder.encode(newPassword));
        user.setPasswordExpireTime(LocalDateTime.now().plusDays(90)); // 重置密码过期时间
        updateById(user);
        
        return newPassword;
    }

    @Override
    @Transactional
    public void updateStatus(Long id, Integer status) {
        User user = new User();
        user.setId(id);
        user.setStatus(status);
        updateById(user);
    }

    @Override
    @Transactional
    public void assignRoles(Long id, List<Long> roleIds) {
        // 删除原有角色
        userRoleService.remove(new LambdaQueryWrapper<UserRole>()
            .eq(UserRole::getUserId, id));
            
        // 分配新角色
        if (roleIds != null && !roleIds.isEmpty()) {
            List<UserRole> userRoles = roleIds.stream()
                .map(roleId -> new UserRole()
                    .setUserId(id)
                    .setRoleId(roleId))
                .toList();
            userRoleService.saveBatch(userRoles);
        }
    }

    @Override
    public List<Long> getUserRoleIds(Long id) {
        return baseMapper.selectRoleIdsByUserId(id);
    }

    @Override
    public List<String> getUserPermissions(Long id) {
        return baseMapper.selectPermissionsByUserId(id);
    }

    @Override
    @Transactional
    public boolean save(User user) {
        if (user.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        return super.save(user);
    }

    @Override
    @Transactional
    public boolean updateById(User user) {
        if (user.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        return super.updateById(user);
    }
}