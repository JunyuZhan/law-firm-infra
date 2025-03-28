package com.lawfirm.api.controller;

import com.lawfirm.api.adaptor.system.MenuAdaptor;
import com.lawfirm.common.core.api.CommonResult;
import com.lawfirm.model.system.vo.MenuVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 菜单控制器
 * 适配Vue-Vben-Admin
 */
@Tag(name = "菜单管理")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MenuController {

    private final MenuAdaptor menuAdaptor;
    
    /**
     * 兼容旧版接口
     */
    @Operation(summary = "获取用户菜单")
    @GetMapping("/v1/menu/list")
    public CommonResult<List<MenuVO>> getUserMenusLegacy() {
        return CommonResult.success(menuAdaptor.getUserMenus());
    }

    /**
     * Vben-Admin菜单接口
     */
    @Operation(summary = "获取用户菜单")
    @GetMapping("/getMenuList")
    public CommonResult<List<MenuVO>> getMenuList() {
        return CommonResult.success(menuAdaptor.getUserMenus());
    }
} 