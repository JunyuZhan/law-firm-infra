package com.lawfirm.system.service.impl;

import com.lawfirm.common.test.BaseIntegrationTest;
import com.lawfirm.system.SystemTestApplication;
import com.lawfirm.model.system.service.DictItemService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = SystemTestApplication.class)
class DictItemServiceImplTest extends BaseIntegrationTest {
    @Autowired
    private DictItemService dictItemService;

    @Test
    void testListDictItemsByDictId() {
        assertNotNull(dictItemService.listDictItemsByDictId(1L)); // 1L可根据实际测试库调整
    }
} 