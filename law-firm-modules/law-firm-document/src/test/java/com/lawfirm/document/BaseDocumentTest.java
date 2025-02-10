package com.lawfirm.document;

import com.lawfirm.document.config.TestConfig;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Import(TestConfig.class)
@ActiveProfiles("test")
public abstract class BaseDocumentTest {
    
    protected static final String TEST_FILE_PATH = "./test-storage/";
    protected static final String TEST_TEMP_PATH = "./test-storage/temp/";
    
    protected String getTestFilePath(String fileName) {
        return TEST_FILE_PATH + fileName;
    }
    
    protected String getTempFilePath(String fileName) {
        return TEST_TEMP_PATH + fileName;
    }
} 