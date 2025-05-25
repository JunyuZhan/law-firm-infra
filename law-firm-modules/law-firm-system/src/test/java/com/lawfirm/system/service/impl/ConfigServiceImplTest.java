package com.lawfirm.system.service.impl;

import com.lawfirm.common.test.BaseIntegrationTest;
import com.lawfirm.common.test.TestApplication;
import com.lawfirm.model.system.service.ConfigService;
import com.lawfirm.model.system.vo.ConfigVO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = com.lawfirm.system.SystemTestApplication.class)
class ConfigServiceImplTest extends BaseIntegrationTest {
    @Autowired
    private ConfigService configService;

    @Test
    void testListAllConfigs() {
        List<ConfigVO> configs = configService.listAllConfigs();
        assertNotNull(configs);
    }
} 