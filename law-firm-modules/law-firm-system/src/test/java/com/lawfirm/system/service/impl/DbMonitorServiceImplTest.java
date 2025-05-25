package com.lawfirm.system.service.impl;

import com.lawfirm.common.test.BaseIntegrationTest;
import com.lawfirm.common.test.TestApplication;
import com.lawfirm.model.system.service.MonitorService;
import com.lawfirm.model.system.dto.monitor.MonitorQueryDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = com.lawfirm.system.SystemTestApplication.class)
class DbMonitorServiceImplTest extends BaseIntegrationTest {
    @Autowired
    private MonitorService monitorService;

    @Test
    void testGetMonitorData() {
        MonitorQueryDTO query = new MonitorQueryDTO();
        assertNotNull(monitorService.getMonitorData(query));
    }
} 