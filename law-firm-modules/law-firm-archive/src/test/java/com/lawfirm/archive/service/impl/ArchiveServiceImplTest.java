package com.lawfirm.archive.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lawfirm.archive.mapper.ArchiveMapper;
import com.lawfirm.archive.model.entity.Archive;
import com.lawfirm.archive.model.enums.ArchiveStatusEnum;
import com.lawfirm.common.core.exception.BusinessException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ArchiveServiceImplTest {

    @Mock
    private ArchiveMapper archiveMapper;

    @InjectMocks
    private ArchiveServiceImpl archiveService;

    private Archive testArchive;

    @BeforeEach
    void setUp() {
        testArchive = new Archive();
        testArchive.setId(1L);
        testArchive.setArchiveNumber("TEST001");
        testArchive.setArchiveName("测试档案");
        testArchive.setStatus(ArchiveStatusEnum.IN_STORAGE.name());
        testArchive.setFilingTime(LocalDateTime.now());
        
        // 设置BaseMapper
        ReflectionTestUtils.setField(archiveService, "baseMapper", archiveMapper);
    }

    @Test
    void createArchive_Success() {
        // 配置Mock行为
        when(archiveMapper.selectOne(any())).thenReturn(null);
        when(archiveMapper.insert(any(Archive.class))).thenReturn(1);

        Archive result = archiveService.createArchive(testArchive);

        assertNotNull(result);
        assertEquals(testArchive.getArchiveNumber(), result.getArchiveNumber());
        verify(archiveMapper, times(1)).insert(any(Archive.class));
    }

    @Test
    void getArchiveById_Success() {
        when(archiveMapper.selectById(1L)).thenReturn(testArchive);

        Archive result = archiveService.getArchiveById(1L);

        assertNotNull(result);
        assertEquals(testArchive.getId(), result.getId());
        verify(archiveMapper, times(1)).selectById(1L);
    }

    @Test
    void getArchiveById_NotFound() {
        when(archiveMapper.selectById(1L)).thenReturn(null);

        assertThrows(BusinessException.class, () -> archiveService.getArchiveById(1L));
        verify(archiveMapper, times(1)).selectById(1L);
    }

    @Test
    void getArchivesByStatus_Success() {
        List<Archive> archives = Arrays.asList(testArchive);
        when(archiveMapper.selectList(any())).thenReturn(archives);

        List<Archive> results = archiveService.getArchivesByStatus(ArchiveStatusEnum.IN_STORAGE);

        assertNotNull(results);
        assertEquals(1, results.size());
        assertEquals(testArchive.getArchiveNumber(), results.get(0).getArchiveNumber());
        verify(archiveMapper, times(1)).selectList(any());
    }

    @Test
    void borrowArchive_Success() {
        when(archiveMapper.selectById(1L)).thenReturn(testArchive);
        when(archiveMapper.updateById(any(Archive.class))).thenReturn(1);

        archiveService.borrowArchive(1L, "测试人员", LocalDateTime.now().plusDays(7), "测试借阅");

        verify(archiveMapper, times(1)).selectById(1L);
        verify(archiveMapper, times(1)).updateById(any(Archive.class));
    }

    @Test
    void borrowArchive_AlreadyBorrowed() {
        testArchive.setStatus(ArchiveStatusEnum.BORROWED.name());
        when(archiveMapper.selectById(1L)).thenReturn(testArchive);

        assertThrows(BusinessException.class, () -> 
            archiveService.borrowArchive(1L, "测试人员", LocalDateTime.now().plusDays(7), "测试借阅"));
        
        verify(archiveMapper, times(1)).selectById(1L);
        verify(archiveMapper, never()).updateById(any(Archive.class));
    }
} 