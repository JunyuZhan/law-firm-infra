package com.lawfirm.api.adaptor.personnel;

import com.lawfirm.api.adaptor.BaseAdaptor;
import com.lawfirm.model.organization.service.OrganizationAuthBridge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 人事组织权限管理适配器
 */
@Component
public class PersonnelOrganizationAuthAdaptor extends BaseAdaptor {

    @Autowired
    private OrganizationAuthBridge organizationAuthBridge;

    /**
     * 检查用户是否有组织权限
     */
    public boolean checkOrganizationPermission(Long userId, Long organizationId, String permission) {
        return organizationAuthBridge.checkOrganizationPermission(userId, organizationId, permission);
    }

    /**
     * 获取用户有权限的组织列表
     */
    public List<Long> getUserAuthorizedOrganizations(Long userId, String permission) {
        return organizationAuthBridge.getUserAuthorizedOrganizations(userId, permission);
    }

    /**
     * 获取组织有权限的用户列表
     */
    public List<Long> getOrganizationAuthorizedUsers(Long organizationId, String permission) {
        return organizationAuthBridge.getOrganizationAuthorizedUsers(organizationId, permission);
    }

    /**
     * 授予用户组织权限
     */
    public void grantOrganizationPermission(Long userId, Long organizationId, String permission) {
        organizationAuthBridge.grantOrganizationPermission(userId, organizationId, permission);
    }

    /**
     * 撤销用户组织权限
     */
    public void revokeOrganizationPermission(Long userId, Long organizationId, String permission) {
        organizationAuthBridge.revokeOrganizationPermission(userId, organizationId, permission);
    }

    /**
     * 检查用户是否是组织管理员
     */
    public boolean isOrganizationAdmin(Long userId, Long organizationId) {
        return organizationAuthBridge.isOrganizationAdmin(userId, organizationId);
    }

    /**
     * 检查用户是否是组织成员
     */
    public boolean isOrganizationMember(Long userId, Long organizationId) {
        return organizationAuthBridge.isOrganizationMember(userId, organizationId);
    }

    /**
     * 获取用户的所有组织权限
     */
    public List<String> getUserOrganizationPermissions(Long userId, Long organizationId) {
        return organizationAuthBridge.getUserOrganizationPermissions(userId, organizationId);
    }
} 