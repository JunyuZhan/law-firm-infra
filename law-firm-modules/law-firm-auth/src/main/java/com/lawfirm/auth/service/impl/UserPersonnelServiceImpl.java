package com.lawfirm.auth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lawfirm.model.auth.entity.User;
import com.lawfirm.model.auth.mapper.UserMapper;
import com.lawfirm.model.auth.service.UserPersonnelService;
import com.lawfirm.model.auth.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 认证用户人事服务实现类
 */
@Slf4j
@Service("authUserPersonnelServiceImpl")
@RequiredArgsConstructor
public class UserPersonnelServiceImpl implements UserPersonnelService {

    private final UserMapper userMapper;
    private final UserService userService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean linkUserToEmployee(Long userId, Long employeeId) {
        // 检查是否已存在关联
        if (isUserLinkedToEmployee(userId) || isEmployeeLinkedToUser(employeeId)) {
            log.warn("用户或员工已存在关联关系，userId: {}, employeeId: {}", userId, employeeId);
            return false;
        }

        // 获取用户信息
        User user = userMapper.selectById(userId);
        if (user == null) {
            log.warn("用户不存在，无法关联，userId: {}", userId);
            return false;
        }

        // 设置关联关系
        user.setEmployeeId(employeeId);
        int result = userMapper.updateById(user);
        
        if (result > 0) {
            log.info("关联用户和员工成功，userId: {}, employeeId: {}", userId, employeeId);
            // 同步用户信息到员工
            syncUserInfoToEmployee(userId);
            return true;
        } else {
            log.error("关联用户和员工失败，userId: {}, employeeId: {}", userId, employeeId);
            return false;
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean unlinkUserFromEmployee(Long userId) {
        // 获取用户信息
        User user = userMapper.selectById(userId);
        if (user == null) {
            log.warn("用户不存在，无法解除关联，userId: {}", userId);
            return false;
        }

        // 检查是否存在关联
        if (user.getEmployeeId() == null) {
            log.warn("用户未关联员工，无需解除关联，userId: {}", userId);
            return false;
        }

        // 解除关联关系
        user.setEmployeeId(null);
        int result = userMapper.updateById(user);
        
        if (result > 0) {
            log.info("解除用户和员工关联成功，userId: {}", userId);
            return true;
        } else {
            log.error("解除用户和员工关联失败，userId: {}", userId);
            return false;
        }
    }

    @Override
    public Long getUserIdByEmployeeId(Long employeeId) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getEmployeeId, employeeId);
        
        User user = userMapper.selectOne(queryWrapper);
        
        if (user != null) {
            return user.getId();
        }
        
        return null;
    }

    @Override
    public Long getEmployeeIdByUserId(Long userId) {
        User user = userMapper.selectById(userId);
        
        if (user != null) {
            return user.getEmployeeId();
        }
        
        return null;
    }

    @Override
    public List<Long> getEmployeeIdsByUserIds(List<Long> userIds) {
        if (CollectionUtils.isEmpty(userIds)) {
            return Collections.emptyList();
        }
        
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(User::getId, userIds);
        
        List<User> users = userMapper.selectList(queryWrapper);
        
        // 创建一个映射，保持顺序一致
        Map<Long, User> userMap = users.stream()
                .collect(Collectors.toMap(User::getId, Function.identity()));
        
        // 按照输入的userIds顺序返回employeeIds
        List<Long> result = new ArrayList<>(userIds.size());
        for (Long userId : userIds) {
            User user = userMap.get(userId);
            result.add(user != null ? user.getEmployeeId() : null);
        }
        
        return result;
    }

    @Override
    public List<Long> getUserIdsByEmployeeIds(List<Long> employeeIds) {
        if (CollectionUtils.isEmpty(employeeIds)) {
            return Collections.emptyList();
        }
        
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(User::getEmployeeId, employeeIds);
        
        List<User> users = userMapper.selectList(queryWrapper);
        
        // 创建一个映射，保持顺序一致
        Map<Long, User> employeeToUserMap = users.stream()
                .collect(Collectors.toMap(User::getEmployeeId, Function.identity()));
        
        // 按照输入的employeeIds顺序返回userIds
        List<Long> result = new ArrayList<>(employeeIds.size());
        for (Long employeeId : employeeIds) {
            User user = employeeToUserMap.get(employeeId);
            result.add(user != null ? user.getId() : null);
        }
        
        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean syncUserInfoToEmployee(Long userId) {
        // 获取用户信息
        User user = userMapper.selectById(userId);
        if (user == null) {
            log.warn("用户不存在，无法同步信息，userId: {}", userId);
            return false;
        }
        
        // 获取关联的员工ID
        Long employeeId = user.getEmployeeId();
        if (employeeId == null) {
            log.warn("用户未关联员工，无法同步信息，userId: {}", userId);
            return false;
        }
        
        try {
            // 这里需要调用personnel-model的服务来更新员工信息
            // 由于模块间的松耦合设计，我们可以通过事件或消息队列来实现异步通信
            // 在实际实现中，可以通过Spring Event或消息中间件来实现
            log.info("同步用户信息到员工，userId: {}, employeeId: {}", userId, employeeId);
            
            // 模拟同步成功
            return true;
        } catch (Exception e) {
            log.error("同步用户信息到员工失败，userId: {}, employeeId: {}", userId, employeeId, e);
            return false;
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean syncEmployeeInfoToUser(Long employeeId) {
        // 获取关联的用户ID
        Long userId = getUserIdByEmployeeId(employeeId);
        if (userId == null) {
            log.warn("员工未关联用户，无法同步信息，employeeId: {}", employeeId);
            return false;
        }
        
        try {
            // 这里需要从personnel-model获取员工信息，然后更新用户信息
            // 由于模块间的松耦合设计，我们可以通过事件或消息队列来实现异步通信
            // 在实际实现中，可以通过Spring Event或消息中间件来实现
            log.info("同步员工信息到用户，employeeId: {}, userId: {}", employeeId, userId);
            
            // 模拟同步成功
            return true;
        } catch (Exception e) {
            log.error("同步员工信息到用户失败，employeeId: {}, userId: {}", employeeId, userId, e);
            return false;
        }
    }

    @Override
    public boolean isUserLinkedToEmployee(Long userId) {
        User user = userMapper.selectById(userId);
        return user != null && user.getEmployeeId() != null;
    }

    @Override
    public boolean isEmployeeLinkedToUser(Long employeeId) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getEmployeeId, employeeId);
        
        return userMapper.selectCount(queryWrapper) > 0;
    }
} 