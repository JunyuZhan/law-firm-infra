package com.lawfirm.system.controller;

import com.lawfirm.model.system.service.MenuService;
import com.lawfirm.model.system.vo.MenuVO;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import lombok.RequiredArgsConstructor;
import com.lawfirm.system.constant.SystemConstants;

@Tag(name = "菜单管理", description = "菜单相关接口")
@RestController
@RequestMapping(SystemConstants.API_PREFIX + "/menu")
@RequiredArgsConstructor
public class MenuController {
    private final MenuService menuService;

    @Operation(summary = "根据角色ID获取菜单列表")
    @GetMapping("/role/{roleId}/menus")
    public List<MenuVO> getMenusByRoleId(@PathVariable Long roleId) {
        return menuService.getMenusByRoleId(roleId);
    }
} 