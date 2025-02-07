package com.lawfirm.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lawfirm.common.data.service.impl.BaseServiceImpl;
import com.lawfirm.common.core.exception.BusinessException;
import com.lawfirm.model.system.entity.SysTemplate;
import com.lawfirm.model.system.vo.SysTemplateVO;
import com.lawfirm.model.system.dto.SysTemplateDTO;
import com.lawfirm.system.mapper.SysTemplateMapper;
import com.lawfirm.system.service.SysTemplateService;
import com.lawfirm.common.data.vo.BaseVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.Collections;

/**
 * 系统模板服务实现类
 */
@Service
@RequiredArgsConstructor
public class SysTemplateServiceImpl extends BaseServiceImpl<SysTemplateMapper, SysTemplate, SysTemplateVO> implements SysTemplateService {

    private final SysTemplateMapper templateMapper;

    @Value("${upload.template.path:/data/upload/template}")
    private String uploadPath;

    @Override
    protected SysTemplate createEntity() {
        return new SysTemplate();
    }

    @Override
    protected SysTemplateVO createVO() {
        return new SysTemplateVO();
    }

    private SysTemplateVO dtoToVO(SysTemplateDTO dto) {
        if (dto == null) {
            return null;
        }
        SysTemplateVO vo = createVO();
        BeanUtils.copyProperties(dto, vo);
        return vo;
    }

    private SysTemplate dtoToEntity(SysTemplateDTO dto) {
        if (dto == null) {
            return null;
        }
        SysTemplate entity = createEntity();
        BeanUtils.copyProperties(dto, entity);
        return entity;
    }

    @Override
    public SysTemplateVO entityToVO(SysTemplate entity) {
        if (entity == null) {
            return null;
        }
        SysTemplateVO vo = createVO();
        BeanUtils.copyProperties(entity, vo);
        return vo;
    }

    @Override
    public SysTemplate voToEntity(SysTemplateVO vo) {
        if (vo == null) {
            return null;
        }
        SysTemplate entity = createEntity();
        BeanUtils.copyProperties(vo, entity);
        return entity;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SysTemplateVO create(SysTemplateDTO templateDTO) {
        // 校验模板编码是否已存在
        if (templateMapper.selectCount(new LambdaQueryWrapper<SysTemplate>()
                .eq(SysTemplate::getCode, templateDTO.getCode())) > 0) {
            throw new BusinessException("模板编码已存在");
        }
        
        // 保存模板
        SysTemplate entity = dtoToEntity(templateDTO);
        save(entity);
        return entityToVO(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SysTemplateVO update(SysTemplateDTO templateDTO) {
        // 校验模板是否存在
        if (!exists(templateDTO.getId())) {
            throw new BusinessException("模板不存在");
        }

        // 校验模板编码是否已存在
        SysTemplate existingTemplate = getById(templateDTO.getId());
        if (!existingTemplate.getCode().equals(templateDTO.getCode())) {
            if (templateMapper.selectCount(new LambdaQueryWrapper<SysTemplate>()
                    .eq(SysTemplate::getCode, templateDTO.getCode())) > 0) {
                throw new BusinessException("模板编码已存在");
            }
        }
        
        // 更新模板
        SysTemplate entity = dtoToEntity(templateDTO);
        updateById(entity);
        return entityToVO(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteTemplate(Long id) {
        // 校验模板是否存在
        if (!exists(id)) {
            throw new BusinessException("模板不存在");
        }
        
        // 删除模板
        removeById(id);
    }

    @Override
    public SysTemplateVO getByCode(String code) {
        SysTemplate template = templateMapper.selectOne(new LambdaQueryWrapper<SysTemplate>()
                .eq(SysTemplate::getCode, code));
        return entityToVO(template);
    }

    @Override
    public List<SysTemplateVO> listByType(String type) {
        List<SysTemplate> templates = templateMapper.selectList(new LambdaQueryWrapper<SysTemplate>()
                .eq(SysTemplate::getType, type));
        return entityListToVOList(templates);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SysTemplateVO uploadTemplate(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new BusinessException("上传文件不能为空");
        }

        // 校验文件类型
        String originalFilename = file.getOriginalFilename();
        if (!StringUtils.hasText(originalFilename)) {
            throw new BusinessException("文件名不能为空");
        }

        // 获取文件扩展名
        String extension = getExtension(originalFilename);
        if (!isValidTemplateExtension(extension)) {
            throw new BusinessException("不支持的文件类型");
        }

        try {
            // 生成存储路径
            String relativePath = generateRelativePath(extension);
            Path fullPath = Paths.get(uploadPath, relativePath);

            // 确保目录存在
            Files.createDirectories(fullPath.getParent());

            // 保存文件
            file.transferTo(fullPath.toFile());

            // 创建模板记录
            SysTemplate template = new SysTemplate();
            template.setName(getFileNameWithoutExtension(originalFilename));
            template.setCode(UUID.randomUUID().toString().replace("-", ""));
            template.setType(extension.toUpperCase());
            template.setContent(relativePath);
            template.setStatus(1);
            save(template);

            return entityToVO(template);
        } catch (IOException e) {
            throw new BusinessException("文件上传失败：" + e.getMessage());
        }
    }

    /**
     * 生成相对存储路径
     */
    private String generateRelativePath(String extension) {
        String datePath = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
        String fileName = UUID.randomUUID().toString() + "." + extension;
        return datePath + "/" + fileName;
    }

    /**
     * 获取不带扩展名的文件名
     */
    private String getFileNameWithoutExtension(String fileName) {
        int lastDotIndex = fileName.lastIndexOf('.');
        return lastDotIndex > 0 ? fileName.substring(0, lastDotIndex) : fileName;
    }

    /**
     * 获取文件扩展名
     */
    private String getExtension(String fileName) {
        int lastDotIndex = fileName.lastIndexOf('.');
        return lastDotIndex > 0 ? fileName.substring(lastDotIndex + 1).toLowerCase() : "";
    }

    /**
     * 校验模板文件扩展名
     */
    private boolean isValidTemplateExtension(String extension) {
        if (!StringUtils.hasText(extension)) {
            return false;
        }
        // 支持的模板文件类型
        return List.of("doc", "docx", "xls", "xlsx", "pdf", "txt", "rtf")
                .contains(extension.toLowerCase());
    }

    @Override
    public boolean exists(Long id) {
        return id != null && templateMapper.selectById(id) != null;
    }
} 