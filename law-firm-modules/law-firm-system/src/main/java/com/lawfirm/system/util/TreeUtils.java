package com.lawfirm.system.util;

import com.lawfirm.model.system.entity.SysMenu;
import com.lawfirm.system.model.dto.SysMenuDTO;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 树形结构工具类
 */
public class TreeUtils {

    /**
     * 构建菜单树 - 从实体构建
     */
    public static List<SysMenuDTO> buildMenuTreeFromEntity(List<SysMenu> menus) {
        List<SysMenuDTO> menuDTOs = menus.stream().map(menu -> {
            SysMenuDTO dto = new SysMenuDTO();
            BeanUtils.copyProperties(menu, dto);
            return dto;
        }).collect(Collectors.toList());

        return buildMenuTree(menuDTOs);
    }

    /**
     * 构建菜单树 - 从DTO构建
     */
    public static List<SysMenuDTO> buildMenuTree(List<SysMenuDTO> menuDTOs) {
        Map<Long, List<SysMenuDTO>> parentIdMap = menuDTOs.stream()
                .collect(Collectors.groupingBy(SysMenuDTO::getParentId));

        menuDTOs.forEach(menu -> menu.setChildren(parentIdMap.get(menu.getId())));

        return menuDTOs.stream()
                .filter(menu -> menu.getParentId() == 0)
                .collect(Collectors.toList());
    }

    /**
     * 构建树形结构
     */
    public static <T> List<T> buildTree(List<T> list, String idField, String parentIdField, String childrenField) {
        Map<Object, List<T>> parentIdMap = list.stream()
                .collect(Collectors.groupingBy(item -> {
                    try {
                        return item.getClass().getDeclaredField(parentIdField).get(item);
                    } catch (Exception e) {
                        return null;
                    }
                }));

        list.forEach(item -> {
            try {
                Object id = item.getClass().getDeclaredField(idField).get(item);
                item.getClass().getDeclaredField(childrenField).set(item, parentIdMap.get(id));
            } catch (Exception ignored) {
            }
        });

        return list.stream()
                .filter(item -> {
                    try {
                        return item.getClass().getDeclaredField(parentIdField).get(item) == null;
                    } catch (Exception e) {
                        return false;
                    }
                })
                .collect(Collectors.toList());
    }
} 