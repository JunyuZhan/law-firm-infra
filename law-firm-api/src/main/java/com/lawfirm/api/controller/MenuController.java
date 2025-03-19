package com.lawfirm.api.controller;

import com.lawfirm.api.adaptor.system.MenuAdaptor;
import com.lawfirm.api.common.ResponseResult;
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
 */
@Tag(name = "菜单管理")
@RestController
@RequestMapping("/v1/menu")
@RequiredArgsConstructor
public class MenuController {

    private final MenuAdaptor menuAdaptor;

    @Operation(summary = "获取用户菜单")
    @GetMapping("/list")
    public ResponseResult<List<MenuVO>> getUserMenus() {
        return ResponseResult.success(menuAdaptor.getUserMenus());
    }
} 