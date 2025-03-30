package com.lawfirm.knowledge.controller;

import com.lawfirm.model.knowledge.entity.KnowledgeAttachment;
import com.lawfirm.model.knowledge.service.KnowledgeAttachmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 知识附件控制器
 */
@Slf4j
@Tag(name = "知识附件", description = "知识附件管理接口")
@RestController
@RequestMapping("/knowledge/attachment")
public class KnowledgeAttachmentController {

    @Autowired
    private KnowledgeAttachmentService attachmentService;

    /**
     * 获取知识文档的附件列表
     */
    @Operation(summary = "获取附件列表", description = "根据知识文档ID获取附件列表")
    @GetMapping("/list/{knowledgeId}")
    public ResponseEntity<List<KnowledgeAttachment>> getAttachmentsByKnowledgeId(
            @Parameter(description = "知识文档ID") @PathVariable Long knowledgeId) {
        List<KnowledgeAttachment> attachments = attachmentService.listByKnowledgeId(knowledgeId);
        return ResponseEntity.ok(attachments);
    }

    /**
     * 添加附件信息
     */
    @Operation(summary = "添加附件", description = "添加新的知识附件")
    @PostMapping
    public ResponseEntity<Boolean> addAttachment(
            @Parameter(description = "附件信息") @RequestBody KnowledgeAttachment attachment) {
        boolean success = attachmentService.save(attachment);
        return ResponseEntity.ok(success);
    }

    /**
     * 更新附件信息
     */
    @Operation(summary = "更新附件", description = "根据ID更新知识附件信息")
    @PutMapping("/{id}")
    public ResponseEntity<Boolean> updateAttachment(
            @Parameter(description = "附件ID") @PathVariable Long id,
            @Parameter(description = "附件信息") @RequestBody KnowledgeAttachment attachment) {
        // 确保ID一致
        if (attachment.getId() == null) {
            attachment.setId(id);
        } else if (!id.equals(attachment.getId())) {
            return ResponseEntity.badRequest().build();
        }
        
        boolean success = attachmentService.updateById(attachment);
        return ResponseEntity.ok(success);
    }

    /**
     * 删除附件
     */
    @Operation(summary = "删除附件", description = "根据ID删除知识附件")
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteAttachment(
            @Parameter(description = "附件ID") @PathVariable Long id) {
        try {
            boolean success = attachmentService.removeById(id);
            return ResponseEntity.ok(success);
        } catch (Exception e) {
            log.error("删除附件失败", e);
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * 统计知识附件数量
     */
    @Operation(summary = "统计附件数量", description = "统计指定知识文档的附件数量")
    @GetMapping("/count/{knowledgeId}")
    public ResponseEntity<Integer> countAttachments(
            @Parameter(description = "知识文档ID") @PathVariable Long knowledgeId) {
        Integer count = attachmentService.countByKnowledgeId(knowledgeId);
        return ResponseEntity.ok(count);
    }

    /**
     * 根据文件类型查询附件列表
     */
    @Operation(summary = "根据文件类型查询", description = "根据文件类型查询附件列表")
    @GetMapping("/type/{fileType}")
    public ResponseEntity<List<KnowledgeAttachment>> getAttachmentsByFileType(
            @Parameter(description = "文件类型") @PathVariable String fileType) {
        List<KnowledgeAttachment> attachments = attachmentService.listByFileType(fileType);
        return ResponseEntity.ok(attachments);
    }
} 