package com.lawfirm.knowledge.controller;

import com.lawfirm.model.knowledge.service.convert.KnowledgeConvert;
import com.lawfirm.model.knowledge.entity.KnowledgeTag;
import com.lawfirm.model.knowledge.service.KnowledgeTagService;
import com.lawfirm.model.knowledge.vo.KnowledgeTagVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 知识标签控制器
 */
@Slf4j
@Tag(name = "知识标签管理", description = "知识标签的增删改查接口")
@RestController("knowledgeTagController")
@RequestMapping("/api/knowledge/tag")
public class KnowledgeTagController {

    @Autowired
    private KnowledgeTagService tagService;

    @Autowired
    private KnowledgeConvert knowledgeConvert;

    /**
     * 获取所有标签
     */
    @Operation(summary = "获取所有标签", description = "获取所有知识标签列表")
    @GetMapping
    public ResponseEntity<List<KnowledgeTagVO>> getAllTags() {
        List<KnowledgeTag> tags = tagService.list();
        return ResponseEntity.ok(knowledgeConvert.toTagVOList(tags));
    }

    /**
     * 根据ID获取标签
     */
    @Operation(summary = "获取标签详情", description = "根据ID获取标签详情")
    @GetMapping("/{id}")
    public ResponseEntity<KnowledgeTagVO> getTagById(
            @Parameter(description = "标签ID") @PathVariable Long id) {
        KnowledgeTag tag = tagService.getById(id);
        if (tag == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(knowledgeConvert.toTagVO(tag));
    }

    /**
     * 创建标签
     */
    @Operation(summary = "创建标签", description = "创建新的知识标签")
    @PostMapping
    public ResponseEntity<KnowledgeTagVO> createTag(
            @Parameter(description = "标签信息") @RequestBody KnowledgeTag tag) {
        boolean success = tagService.save(tag);
        if (success) {
            KnowledgeTag savedTag = tagService.getById(tag.getId());
            return ResponseEntity.ok(knowledgeConvert.toTagVO(savedTag));
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * 更新标签
     */
    @Operation(summary = "更新标签", description = "根据ID更新知识标签")
    @PutMapping("/{id}")
    public ResponseEntity<KnowledgeTagVO> updateTag(
            @Parameter(description = "标签ID") @PathVariable Long id,
            @Parameter(description = "标签信息") @RequestBody KnowledgeTag tag) {
        // 确保ID一致
        if (tag.getId() == null) {
            tag.setId(id);
        } else if (!id.equals(tag.getId())) {
            return ResponseEntity.badRequest().build();
        }
        
        // 检查标签是否存在
        KnowledgeTag existingTag = tagService.getById(id);
        if (existingTag == null) {
            return ResponseEntity.notFound().build();
        }
        
        boolean success = tagService.updateById(tag);
        if (success) {
            KnowledgeTag updatedTag = tagService.getById(id);
            return ResponseEntity.ok(knowledgeConvert.toTagVO(updatedTag));
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * 删除标签
     */
    @Operation(summary = "删除标签", description = "根据ID删除知识标签")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTag(
            @Parameter(description = "标签ID") @PathVariable Long id) {
        try {
            boolean success = tagService.removeById(id);
            return success ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
        } catch (RuntimeException e) {
            log.error("删除标签失败: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * 获取热门标签
     */
    @Operation(summary = "获取热门标签", description = "获取使用最多的热门标签")
    @GetMapping("/hot")
    public ResponseEntity<List<KnowledgeTagVO>> getHotTags(
            @Parameter(description = "限制数量") @RequestParam(required = false, defaultValue = "10") Integer limit) {
        List<KnowledgeTag> hotTags = tagService.listHotTags(limit);
        return ResponseEntity.ok(knowledgeConvert.toTagVOList(hotTags));
    }
} 