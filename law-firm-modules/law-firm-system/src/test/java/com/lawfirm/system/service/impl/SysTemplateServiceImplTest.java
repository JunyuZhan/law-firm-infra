package com.lawfirm.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lawfirm.common.core.exception.BusinessException;
import com.lawfirm.model.system.entity.SysTemplate;
import com.lawfirm.model.system.dto.SysTemplateDTO;
import com.lawfirm.model.system.vo.SysTemplateVO;
import com.lawfirm.system.mapper.SysTemplateMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SysTemplateServiceImplTest {

    @Mock
    private SysTemplateMapper templateMapper;

    @InjectMocks
    private SysTemplateServiceImpl templateService;

    private final String testUploadPath = "target/test-upload";

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(templateService, "uploadPath", testUploadPath);
    }

    @Test
    void create_Success() {
        // 准备测试数据
        SysTemplateDTO templateDTO = new SysTemplateDTO();
        templateDTO.setCode("TEST001");
        templateDTO.setName("测试模板");

        when(templateMapper.selectCount(any())).thenReturn(0L);
        when(templateMapper.insert(any())).thenReturn(1);

        // 执行测试
        SysTemplateVO result = templateService.create(templateDTO);
        
        // 验证结果
        assertNotNull(result);
        assertEquals("TEST001", result.getCode());
        assertEquals("测试模板", result.getName());
        verify(templateMapper).selectCount(any());
        verify(templateMapper).insert(any());
    }

    @Test
    void create_DuplicateCode() {
        // 准备测试数据
        SysTemplateDTO templateDTO = new SysTemplateDTO();
        templateDTO.setCode("TEST001");
        templateDTO.setName("测试模板");

        when(templateMapper.selectCount(any())).thenReturn(1L);

        // 执行测试并验证结果
        assertThrows(BusinessException.class, () -> templateService.create(templateDTO));
        verify(templateMapper).selectCount(any());
        verify(templateMapper, never()).insert(any());
    }

    @Test
    void uploadTemplate_Success() throws IOException {
        // 准备测试数据
        byte[] content = "test content".getBytes();
        MockMultipartFile file = new MockMultipartFile(
            "file", 
            "test.docx",
            "application/vnd.openxmlformats-officedocument.wordprocessingml.document",
            content
        );

        // 创建测试目录
        Files.createDirectories(Path.of(testUploadPath));

        // 执行测试
        SysTemplateVO result = templateService.uploadTemplate(file);

        // 验证结果
        assertNotNull(result);
        assertNotNull(result.getCode());
        assertEquals("test", result.getName());
        assertEquals("DOCX", result.getType());
        assertTrue(result.getContent().endsWith(".docx"));
        verify(templateMapper).insert(any());
    }

    @Test
    void uploadTemplate_InvalidExtension() {
        // 准备测试数据
        MockMultipartFile file = new MockMultipartFile(
            "file", 
            "test.exe",
            "application/octet-stream",
            "test content".getBytes()
        );

        // 执行测试并验证结果
        assertThrows(BusinessException.class, () -> templateService.uploadTemplate(file));
        verify(templateMapper, never()).insert(any());
    }

    @Test
    void listByType_Success() {
        // 准备测试数据
        String type = "DOCX";
        List<SysTemplate> templates = Arrays.asList(
            createTestTemplate(1L, "测试模板1"),
            createTestTemplate(2L, "测试模板2")
        );

        when(templateMapper.selectList(any())).thenReturn(templates);

        // 执行测试
        List<SysTemplateVO> result = templateService.listByType(type);

        // 验证结果
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("测试模板1", result.get(0).getName());
        assertEquals("测试模板2", result.get(1).getName());
        verify(templateMapper).selectList(any());
    }

    private SysTemplate createTestTemplate(Long id, String name) {
        SysTemplate template = new SysTemplate();
        template.setId(id);
        template.setName(name);
        template.setCode("TEST" + id);
        template.setType("DOCX");
        template.setStatus(1);
        return template;
    }
} 