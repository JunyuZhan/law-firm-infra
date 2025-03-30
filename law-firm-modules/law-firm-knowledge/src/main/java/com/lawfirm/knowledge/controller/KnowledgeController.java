package com.lawfirm.knowledge.controller;

import com.lawfirm.model.knowledge.service.convert.KnowledgeConvert;
import com.lawfirm.model.knowledge.dto.KnowledgeDTO;
import com.lawfirm.model.knowledge.entity.Knowledge;
import com.lawfirm.model.knowledge.service.KnowledgeService;
import com.lawfirm.model.knowledge.vo.KnowledgeVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 知识文档控制器
 */
@Slf4j
@Tag(name = "知识库", description = "知识库管理接口")
@RestController
@RequestMapping("/knowledge")
public class KnowledgeController {

    @Autowired
    private KnowledgeService knowledgeService;

    @Autowired
    private KnowledgeConvert knowledgeConvert;

    /**
     * 创建知识文档
     */
    @Operation(summary = "创建知识文档", description = "创建新的知识文档记录")
    @PostMapping
    public ResponseEntity<KnowledgeVO> addKnowledge(
            @Parameter(description = "知识文档信息") @RequestBody @Valid KnowledgeDTO knowledgeDTO) {
        Knowledge knowledge = knowledgeConvert.fromDTO(knowledgeDTO);
        boolean success = knowledgeService.save(knowledge);
        if (success) {
            return ResponseEntity.ok(knowledgeConvert.toVO(knowledgeService.getById(knowledge.getId())));
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * 更新知识文档
     */
    @Operation(summary = "更新知识文档", description = "根据ID更新知识文档")
    @PutMapping("/{id}")
    public ResponseEntity<KnowledgeVO> updateKnowledge(
            @Parameter(description = "知识文档ID") @PathVariable Long id, 
            @Parameter(description = "知识文档信息") @RequestBody @Valid KnowledgeDTO knowledgeDTO) {
        // 确保ID一致
        if (knowledgeDTO.getId() == null) {
            knowledgeDTO.setId(id);
        } else if (!id.equals(knowledgeDTO.getId())) {
            return ResponseEntity.badRequest().build();
        }
        
        // 检查知识文档是否存在
        Knowledge existingKnowledge = knowledgeService.getById(id);
        if (existingKnowledge == null) {
            return ResponseEntity.notFound().build();
        }
        
        // 更新知识文档
        Knowledge knowledge = knowledgeConvert.fromDTO(knowledgeDTO);
        boolean success = knowledgeService.update(knowledge);
        
        if (success) {
            return ResponseEntity.ok(knowledgeConvert.toVO(knowledgeService.getById(id)));
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * 根据ID获取知识文档
     */
    @Operation(summary = "获取知识文档", description = "根据ID获取知识文档详情")
    @GetMapping("/{id}")
    public ResponseEntity<KnowledgeVO> getKnowledge(
            @Parameter(description = "知识文档ID") @PathVariable Long id) {
        Knowledge knowledge = knowledgeService.getById(id);
        if (knowledge == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(knowledgeConvert.toVO(knowledge));
    }

    /**
     * 删除知识文档
     */
    @Operation(summary = "删除知识文档", description = "根据ID删除知识文档")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteKnowledge(
            @Parameter(description = "知识文档ID") @PathVariable Long id) {
        boolean success = knowledgeService.remove(id);
        return success ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    /**
     * 根据分类ID获取知识列表
     */
    @Operation(summary = "获取分类的知识列表", description = "根据分类ID获取知识文档列表")
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<KnowledgeVO>> getKnowledgeByCategory(
            @Parameter(description = "分类ID") @PathVariable Long categoryId) {
        List<Knowledge> knowledgeList = knowledgeService.listByCategoryId(categoryId);
        return ResponseEntity.ok(knowledgeConvert.toVOList(knowledgeList));
    }

    /**
     * 根据标签ID获取知识列表
     */
    @Operation(summary = "获取标签的知识列表", description = "根据标签ID获取知识文档列表")
    @GetMapping("/tag/{tagId}")
    public ResponseEntity<List<KnowledgeVO>> getKnowledgeByTag(
            @Parameter(description = "标签ID") @PathVariable Long tagId) {
        List<Knowledge> knowledgeList = knowledgeService.listByTagId(tagId);
        return ResponseEntity.ok(knowledgeConvert.toVOList(knowledgeList));
    }

    /**
     * 搜索知识
     */
    @Operation(summary = "搜索知识", description = "根据关键字搜索知识文档")
    @GetMapping("/search")
    public ResponseEntity<List<KnowledgeVO>> searchKnowledge(
            @Parameter(description = "搜索关键字") @RequestParam String keyword) {
        List<Knowledge> knowledgeList = knowledgeService.searchByKeyword(keyword);
        return ResponseEntity.ok(knowledgeConvert.toVOList(knowledgeList));
    }

    /**
     * 获取最新知识
     */
    @Operation(summary = "获取最新知识", description = "获取最新发布的知识文档列表")
    @GetMapping("/latest")
    public ResponseEntity<List<KnowledgeVO>> getLatestKnowledge(
            @Parameter(description = "限制数量") @RequestParam(required = false) Integer limit) {
        List<Knowledge> knowledgeList = knowledgeService.listLatest(limit);
        return ResponseEntity.ok(knowledgeConvert.toVOList(knowledgeList));
    }

    /**
     * 获取相关知识
     */
    @Operation(summary = "获取相关知识", description = "获取与指定知识相关的其他知识文档")
    @GetMapping("/{id}/related")
    public ResponseEntity<List<KnowledgeVO>> getRelatedKnowledge(
            @Parameter(description = "知识文档ID") @PathVariable Long id, 
            @Parameter(description = "分类ID") @RequestParam Long categoryId,
            @Parameter(description = "限制数量") @RequestParam(required = false) Integer limit) {
        List<Knowledge> knowledgeList = knowledgeService.listRelated(categoryId, id, limit);
        return ResponseEntity.ok(knowledgeConvert.toVOList(knowledgeList));
    }
} 