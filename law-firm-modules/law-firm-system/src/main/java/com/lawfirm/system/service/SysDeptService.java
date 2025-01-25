package com.lawfirm.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lawfirm.common.log.annotation.Log;
import com.lawfirm.common.log.enums.BusinessType;
import com.lawfirm.model.system.entity.SysDept;

import java.util.List;

/**
 * 系统部门服务接口
 */
public interface SysDeptService extends IService<SysDept> {

    /**
     * 创建部门
     */
    @Log(module = "部门管理", businessType = BusinessType.INSERT, description = "创建部门")
    void createDept(SysDept dept);

    /**
     * 更新部门
     */
    @Log(module = "部门管理", businessType = BusinessType.UPDATE, description = "更新部门")
    void updateDept(SysDept dept);

    /**
     * 删除部门
     */
    @Log(module = "部门管理", businessType = BusinessType.DELETE, description = "删除部门")
    void deleteDept(Long id);

    /**
     * 查询子部门列表
     */
    List<SysDept> listChildren(Long parentId);

    /**
     * 查询部门路径
     */
    List<SysDept> getPath(Long id);

    /**
     * 查询部门树
     */
    List<SysDept> getTree();

    /**
     * 移动部门
     */
    @Log(module = "部门管理", businessType = BusinessType.UPDATE, description = "移动部门")
    void moveDept(Long id, Long parentId);

    /**
     * 调整部门顺序
     */
    @Log(module = "部门管理", businessType = BusinessType.UPDATE, description = "调整部门顺序")
    void reorderDept(Long id, Integer orderNum);
} 