package com.lawfirm.auth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lawfirm.model.auth.dto.permission.PermissionCreateDTO;
import com.lawfirm.model.auth.dto.permission.PermissionUpdateDTO;
import com.lawfirm.model.auth.entity.Permission;
import com.lawfirm.model.auth.entity.RolePermission;
import com.lawfirm.model.auth.entity.UserRole;
import com.lawfirm.model.auth.mapper.PermissionMapper;
import com.lawfirm.model.auth.mapper.RolePermissionMapper;
import com.lawfirm.model.auth.mapper.UserRoleMapper;
import com.lawfirm.model.auth.service.PermissionService;
import com.lawfirm.model.auth.vo.PermissionVO;
import com.lawfirm.model.auth.vo.RouterVO;
import com.lawfirm.model.auth.vo.MetaVO;
import com.lawfirm.model.base.dto.BaseDTO;
import com.lawfirm.model.base.service.impl.BaseServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 认证权限服务实现类
 */
@Slf4j
@Service("authPermissionServiceImpl")
@RequiredArgsConstructor
public class PermissionServiceImpl extends BaseServiceImpl<PermissionMapper, Permission> implements PermissionService {

    private final PermissionMapper permissionMapper;
    private final RolePermissionMapper rolePermissionMapper;
    private final UserRoleMapper userRoleMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createPermission(PermissionCreateDTO createDTO) {
        Permission permission = new Permission();
        BeanUtils.copyProperties(createDTO, permission);
        
        // 设置默认值
        if (permission.getSort() == null) {
            permission.setSort(0);
        }
        if (permission.getStatus() == null) {
            permission.setStatus(0); // 默认启用
        }
        
        save(permission);
        log.info("创建权限成功: {}", permission.getId());
        return permission.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updatePermission(Long id, PermissionUpdateDTO updateDTO) {
        Permission permission = getById(id);
        if (permission == null) {
            throw new IllegalArgumentException("权限不存在");
        }
        
        BeanUtils.copyProperties(updateDTO, permission);
        updateById(permission);
        log.info("更新权限成功: {}", id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deletePermission(Long id) {
        // 检查是否有子权限
        LambdaQueryWrapper<Permission> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Permission::getParentId, id);
        long count = count(queryWrapper);
        if (count > 0) {
            throw new IllegalArgumentException("存在子权限，无法删除");
        }
        
        // 删除权限与角色的关联
        LambdaQueryWrapper<RolePermission> rpQueryWrapper = new LambdaQueryWrapper<>();
        rpQueryWrapper.eq(RolePermission::getPermissionId, id);
        rolePermissionMapper.delete(rpQueryWrapper);
        
        // 删除权限
        boolean result = removeById(id);
        log.info("删除权限: {}, 结果: {}", id, result);
        return result;
    }

    @Override
    public PermissionVO getPermissionById(Long id) {
        Permission permission = getById(id);
        if (permission == null) {
            return null;
        }
        
        PermissionVO vo = new PermissionVO();
        BeanUtils.copyProperties(permission, vo);
        
        // 设置父权限名称
        if (permission.getParentId() != null && permission.getParentId() != 0) {
            Permission parent = getById(permission.getParentId());
            if (parent != null) {
                vo.setParentName(parent.getName());
            }
        }
        
        return vo;
    }

    @Override
    public List<PermissionVO> listAllPermissions() {
        List<Permission> permissions = list();
        return permissions.stream()
                .map(this::convertToVO)
                .collect(Collectors.toList());
    }

    @Override
    public List<PermissionVO> getPermissionTree() {
        List<Permission> permissions = list(new LambdaQueryWrapper<Permission>()
                .orderByAsc(Permission::getSort));
        
        return buildTree(permissions);
    }

    @Override
    public List<PermissionVO> listPermissionsByRoleId(Long roleId) {
        // 查询角色关联的权限ID
        LambdaQueryWrapper<RolePermission> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(RolePermission::getRoleId, roleId);
        List<RolePermission> rolePermissions = rolePermissionMapper.selectList(queryWrapper);
        
        if (CollectionUtils.isEmpty(rolePermissions)) {
            return Collections.emptyList();
        }
        
        // 获取权限ID列表
        List<Long> permissionIds = rolePermissions.stream()
                .map(RolePermission::getPermissionId)
                .collect(Collectors.toList());
        
        // 查询权限
        List<Permission> permissions = listByIds(permissionIds);
        
        return permissions.stream()
                .map(this::convertToVO)
                .collect(Collectors.toList());
    }

    @Override
    public List<PermissionVO> listPermissionsByUserId(Long userId) {
        // 查询用户角色
        LambdaQueryWrapper<UserRole> urQueryWrapper = new LambdaQueryWrapper<>();
        urQueryWrapper.eq(UserRole::getUserId, userId);
        List<UserRole> userRoles = userRoleMapper.selectList(urQueryWrapper);
        
        if (CollectionUtils.isEmpty(userRoles)) {
            return Collections.emptyList();
        }
        
        // 获取角色ID列表
        List<Long> roleIds = userRoles.stream()
                .map(UserRole::getRoleId)
                .collect(Collectors.toList());
        
        // 查询角色关联的权限ID
        LambdaQueryWrapper<RolePermission> rpQueryWrapper = new LambdaQueryWrapper<>();
        rpQueryWrapper.in(RolePermission::getRoleId, roleIds);
        List<RolePermission> rolePermissions = rolePermissionMapper.selectList(rpQueryWrapper);
        
        if (CollectionUtils.isEmpty(rolePermissions)) {
            return Collections.emptyList();
        }
        
        // 获取权限ID列表
        List<Long> permissionIds = rolePermissions.stream()
                .map(RolePermission::getPermissionId)
                .distinct()
                .collect(Collectors.toList());
        
        // 查询权限
        List<Permission> permissions = listByIds(permissionIds);
        
        return permissions.stream()
                .map(this::convertToVO)
                .collect(Collectors.toList());
    }

    @Override
    public List<String> listPermissionCodesByUserId(Long userId) {
        List<PermissionVO> permissions = listPermissionsByUserId(userId);
        return permissions.stream()
                .map(PermissionVO::getCode)
                .filter(StringUtils::hasText)
                .distinct()
                .collect(Collectors.toList());
    }

    @Override
    public Page<PermissionVO> pagePermissions(BaseDTO dto) {
        Page<Permission> page = new Page<>(dto.getPageNum(), dto.getPageSize());
        Page<Permission> permissionPage = page(page);
        
        Page<PermissionVO> voPage = new Page<>();
        BeanUtils.copyProperties(permissionPage, voPage, "records");
        
        List<PermissionVO> voList = permissionPage.getRecords().stream()
                .map(this::convertToVO)
                .collect(Collectors.toList());
        
        voPage.setRecords(voList);
        return voPage;
    }

    @Override
    public List<PermissionVO> getPermissionTree(BaseDTO dto) {
        List<Permission> permissions = list(new LambdaQueryWrapper<Permission>()
                .orderByAsc(Permission::getSort));
        
        return buildTree(permissions);
    }

    @Override
    public Permission getByCode(String code) {
        LambdaQueryWrapper<Permission> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Permission::getCode, code);
        return getOne(queryWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateStatus(Long id, Integer status) {
        Permission permission = getById(id);
        if (permission == null) {
            throw new IllegalArgumentException("权限不存在");
        }
        
        permission.setStatus(status);
        updateById(permission);
        log.info("更新权限状态成功: {}, 状态: {}", id, status);
    }

    @Override
    public List<PermissionVO> getUserMenuTree(Long userId) {
        List<PermissionVO> permissions = listPermissionsByUserId(userId);
        
        // 过滤出菜单类型的权限
        List<PermissionVO> menus = permissions.stream()
                .filter(p -> p.getType() != null && p.getType() == 0) // 假设0表示菜单类型
                .collect(Collectors.toList());
        
        return buildTreeFromVO(menus);
    }

    @Override
    public List<RouterVO> getUserRouters(Long userId) {
        List<PermissionVO> permissions = listPermissionsByUserId(userId);
        
        // 过滤出菜单类型的权限
        List<PermissionVO> menus = permissions.stream()
                .filter(p -> p.getType() != null && p.getType() == 0) // 假设0表示菜单类型
                .collect(Collectors.toList());
        
        // 构建路由
        return buildRouters(menus);
    }
    
    /**
     * 将权限实体转换为VO
     */
    private PermissionVO convertToVO(Permission permission) {
        PermissionVO vo = new PermissionVO();
        BeanUtils.copyProperties(permission, vo);
        
        // 设置父权限名称
        if (permission.getParentId() != null && permission.getParentId() != 0) {
            Permission parent = getById(permission.getParentId());
            if (parent != null) {
                vo.setParentName(parent.getName());
            }
        }
        
        return vo;
    }
    
    /**
     * 构建权限树
     */
    private List<PermissionVO> buildTree(List<Permission> permissions) {
        List<PermissionVO> vos = permissions.stream()
                .map(this::convertToVO)
                .collect(Collectors.toList());
        
        return buildTreeFromVO(vos);
    }
    
    /**
     * 从VO列表构建树
     */
    private List<PermissionVO> buildTreeFromVO(List<PermissionVO> permissions) {
        List<PermissionVO> result = new ArrayList<>();
        Map<Long, PermissionVO> permissionMap = new HashMap<>();
        
        // 构建映射
        for (PermissionVO permission : permissions) {
            permissionMap.put(permission.getId(), permission);
        }
        
        // 构建树
        for (PermissionVO permission : permissions) {
            Long parentId = permission.getParentId();
            if (parentId == null || parentId == 0) {
                // 根节点
                result.add(permission);
            } else {
                // 子节点
                PermissionVO parent = permissionMap.get(parentId);
                if (parent != null) {
                    if (parent.getChildren() == null) {
                        parent.setChildren(new ArrayList<>());
                    }
                    parent.getChildren().add(permission);
                }
            }
        }
        
        return result;
    }
    
    /**
     * 构建路由
     * 适配vue-vben-admin前端路由格式
     */
    private List<RouterVO> buildRouters(List<PermissionVO> menus) {
        List<RouterVO> routers = new ArrayList<>();
        
        for (PermissionVO menu : menus) {
            RouterVO router = new RouterVO();
            
            // 设置路由名称 - 强制使用首字母大写的驼峰格式
            String routeName = formatRouterName(menu.getName());
            router.setName(routeName);
            
            // 设置路由路径
            router.setPath(menu.getPath());
            
            // 特殊处理布局组件
            if ("Layout".equalsIgnoreCase(menu.getComponent()) 
                    || menu.getComponent() == null 
                    || "LAYOUT".equalsIgnoreCase(menu.getComponent())) {
                router.setComponent("LAYOUT");
                router.setLayout(true);
            } else {
                // 其他组件
                router.setComponent(menu.getComponent());
                router.setLayout(false);
            }
            
            // 设置重定向
            router.setRedirect(menu.getRedirect());
            
            // 设置Meta信息
            router.setMeta(new MetaVO(
                    menu.getName(),                 // 标题
                    menu.getIcon(),                 // 图标
                    menu.getHidden() != null ? menu.getHidden() : false,  // 是否隐藏菜单
                    menu.getKeepAlive() != null ? !menu.getKeepAlive() : true,   // 是否缓存
                    menu.getAffix() != null ? menu.getAffix() : false,    // 是否固定标签
                    menu.getCurrentActiveMenu(),    // 当前激活的菜单
                    menu.getOrderNum(),             // 排序
                    menu.getIgnoreRoute() != null ? menu.getIgnoreRoute() : false,   // 是否忽略路由
                    menu.getHidePathForChildren() != null ? menu.getHidePathForChildren() : false,  // 是否在子级菜单的完整path中忽略本级path
                    menu.getHideTab() != null ? menu.getHideTab() : false,    // 是否隐藏标签页
                    menu.getIsLink() != null ? menu.getIsLink() : false,      // 是否是外链
                    menu.getFrameSrc(),             // 内嵌iframe的地址
                    menu.getTransitionName(),       // 该路由切换的动画名
                    menu.getDynamicLevel() != null && menu.getDynamicLevel() > 0,  // 是否有动态父级菜单
                    menu.getPermissions(),          // 权限标识
                    menu.getRoles(),                // 角色列表
                    menu.getHideBreadcrumb() != null ? menu.getHideBreadcrumb() : false,  // 是否隐藏面包屑
                    menu.getType(),                 // 菜单类型
                    menu.getNoCache() != null ? menu.getNoCache() : false      // 是否不缓存
            ));
            
            // 处理子路由
            if (!CollectionUtils.isEmpty(menu.getChildren())) {
                router.setChildren(buildRouters(menu.getChildren()));
            }
            
            routers.add(router);
        }
        
        return routers;
    }

    /**
     * 格式化路由名称为vue-vben-admin期望的格式
     * 确保首字母大写的驼峰格式
     */
    private String formatRouterName(String name) {
        if (name == null || name.trim().isEmpty()) {
            return "NotNamed";
        }
        
        // 移除非字母数字字符，并将首字母大写
        String routeName = name.replaceAll("[^a-zA-Z0-9]", "");
        if (!routeName.isEmpty()) {
            routeName = Character.toUpperCase(routeName.charAt(0)) + 
                       (routeName.length() > 1 ? routeName.substring(1) : "");
        } else {
            routeName = "NotNamed";
        }
        
        return routeName;
    }

    @Override
    public boolean exists(QueryWrapper<Permission> queryWrapper) {
        return count(queryWrapper) > 0;
    }

    @Override
    public long count(QueryWrapper<Permission> queryWrapper) {
        return baseMapper.selectCount(queryWrapper);
    }

    @Override
    public Page<Permission> page(Page<Permission> page, QueryWrapper<Permission> queryWrapper) {
        return baseMapper.selectPage(page, queryWrapper);
    }

    @Override
    public List<Permission> list(QueryWrapper<Permission> queryWrapper) {
        return baseMapper.selectList(queryWrapper);
    }

    @Override
    public Permission getById(Long id) {
        return baseMapper.selectById(id);
    }

    @Override
    public boolean save(Permission entity) {
        return super.save(entity);
    }

    @Override
    public boolean saveBatch(List<Permission> entityList) {
        return super.saveBatch(entityList);
    }

    @Override
    public boolean update(Permission entity) {
        return super.updateById(entity);
    }

    @Override
    public boolean updateBatch(List<Permission> entityList) {
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
} 