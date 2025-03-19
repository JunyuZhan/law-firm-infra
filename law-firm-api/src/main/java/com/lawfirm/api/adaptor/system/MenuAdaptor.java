package com.lawfirm.api.adaptor.system;

import com.lawfirm.api.adaptor.BaseAdaptor;
import com.lawfirm.model.system.vo.MenuVO;
import com.lawfirm.model.system.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 菜单适配器
 */
@Component
@RequiredArgsConstructor
public class MenuAdaptor extends BaseAdaptor {

    private final MenuService menuService;

    /**
     * 获取用户菜单
     */
    public List<MenuVO> getUserMenus() {
        return menuService.getUserMenus().stream()
            .map(menu -> convert(menu, MenuVO.class))
            .collect(Collectors.toList());
    }
} 