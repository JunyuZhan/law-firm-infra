package com.lawfirm.system.service.impl;

import com.lawfirm.common.test.BaseIntegrationTest;
import com.lawfirm.system.SystemTestApplication;
import com.lawfirm.model.system.service.UpgradeService;
import com.lawfirm.model.system.dto.upgrade.UpgradeQueryDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = SystemTestApplication.class)
class UpgradeServiceImplTest extends BaseIntegrationTest {
    @Autowired
    private UpgradeService upgradeService;

    @Test
    void testSelectUpgradeList() {
        UpgradeQueryDTO query = new UpgradeQueryDTO();
        assertNotNull(upgradeService.selectUpgradeList(query));
    }
} 