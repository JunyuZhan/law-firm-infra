package com.lawfirm.system.service.impl;

import com.lawfirm.common.test.BaseIntegrationTest;
import com.lawfirm.common.test.TestApplication;
import com.lawfirm.model.system.service.AlertService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = com.lawfirm.system.SystemTestApplication.class)
class AlertServiceImplTest extends BaseIntegrationTest {
    @Autowired
    private AlertService alertService;

    @Test
    void testSendAlert() {
        String alertId = alertService.sendAlert("SYSTEM", "ERROR", "测试告警");
        assertNotNull(alertId);
    }
} 