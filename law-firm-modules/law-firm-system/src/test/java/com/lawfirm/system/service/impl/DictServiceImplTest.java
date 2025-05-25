package com.lawfirm.system.service.impl;

import com.lawfirm.common.test.BaseIntegrationTest;
import com.lawfirm.system.SystemTestApplication;
import com.lawfirm.model.system.service.DictService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = SystemTestApplication.class)
class DictServiceImplTest extends BaseIntegrationTest {
    @Autowired
    private DictService dictService;

    @Test
    void testListAllDicts() {
        assertNotNull(dictService.listAllDicts());
    }
}