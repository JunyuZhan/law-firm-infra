package com.lawfirm.api.adaptor.system;

import com.lawfirm.api.adaptor.BaseAdaptor;
import com.lawfirm.model.system.vo.MenuVO;
import com.lawfirm.model.system.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 菜单适配器
 */
@Component
public class MenuAdaptor extends BaseAdaptor {

    private final MenuService menuService;

    @Autowired
    public MenuAdaptor(@Qualifier("menuServiceImpl") MenuService menuService) {
        this.menuService = menuService;
    }

    /**
     * 获取用户菜单
     */
    public List<MenuVO> getUserMenus() {
        return menuService.getUserMenus().stream()
            .map(menu -> convert(menu, MenuVO.class))
            .collect(Collectors.toList());
    }
} 