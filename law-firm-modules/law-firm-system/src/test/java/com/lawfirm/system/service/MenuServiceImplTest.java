package com.lawfirm.system.service;

import com.lawfirm.common.test.BaseIntegrationTest;
import com.lawfirm.common.test.TestApplication;
import com.lawfirm.model.system.service.MenuService;
import com.lawfirm.model.system.vo.MenuVO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = com.lawfirm.system.SystemTestApplication.class)
class MenuServiceImplTest extends BaseIntegrationTest {
    @Autowired
    private MenuService menuService;

    @Test
    void testGetMenusByRoleId() {
        Long testRoleId = 1L; // 可根据实际测试库调整
        List<MenuVO> menus = menuService.getMenusByRoleId(testRoleId);
        assertNotNull(menus);
    }

    @Test
    void testGetUserMenus() {
        List<MenuVO> menus = menuService.getUserMenus();
        assertNotNull(menus);
    }
}