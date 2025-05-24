package com.lawfirm.cases.service;

import com.lawfirm.model.evidence.service.EvidenceService;
import com.lawfirm.model.evidence.vo.EvidenceVO;
import com.lawfirm.model.evidence.dto.EvidenceDTO;
import com.lawfirm.model.evidence.dto.EvidenceReviewDTO;
import com.lawfirm.model.evidence.service.EvidenceReviewService;
import com.lawfirm.model.evidence.vo.EvidenceReviewVO;
import com.lawfirm.model.evidence.service.EvidenceTraceService;
import com.lawfirm.model.evidence.dto.EvidenceTraceDTO;
import com.lawfirm.model.evidence.vo.EvidenceTraceVO;
import com.lawfirm.model.evidence.service.EvidenceAttachmentService;
import com.lawfirm.model.evidence.dto.EvidenceAttachmentDTO;
import com.lawfirm.model.evidence.vo.EvidenceAttachmentVO;
import com.lawfirm.model.evidence.service.EvidenceTagRelationService;
import com.lawfirm.model.evidence.dto.EvidenceTagRelationDTO;
import com.lawfirm.model.evidence.vo.EvidenceTagRelationVO;
import com.lawfirm.model.log.service.AuditService;
import com.lawfirm.model.log.dto.AuditLogDTO;
import com.lawfirm.model.log.enums.BusinessTypeEnum;
import com.lawfirm.model.log.enums.OperateTypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.ArrayList;
import com.lawfirm.common.util.excel.ExcelWriter;
import java.io.ByteArrayOutputStream;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.AreaBreak;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;

/**
 * 案件与证据集成业务Service
 */
@Service
public class CaseEvidenceBizService {
    @Autowired
    private EvidenceService evidenceService;

    @Autowired
    private EvidenceReviewService evidenceReviewService;

    @Autowired
    private EvidenceTraceService evidenceTraceService;

    @Autowired
    private EvidenceAttachmentService evidenceAttachmentService;

    @Autowired
    private EvidenceTagRelationService evidenceTagRelationService;

    @Autowired(required = false)
    private AuditService auditService;

    /**
     * 查询某案件下所有证据
     */
    public List<EvidenceVO> getEvidenceByCaseId(Long caseId) {
        return evidenceService.listEvidenceByCase(caseId);
    }

    /**
     * 查询单个证据详情
     */
    public EvidenceVO getEvidenceDetail(Long evidenceId) {
        return evidenceService.getEvidence(evidenceId);
    }

    /**
     * 新增证据
     */
    public Long addEvidence(EvidenceDTO dto) {
        return evidenceService.addEvidence(dto);
    }

    /**
     * 删除证据
     */
    public void deleteEvidence(Long evidenceId) {
        evidenceService.deleteEvidence(evidenceId);
    }

    /**
     * 新增证据审核记录
     */
    public Long addEvidenceReview(EvidenceReviewDTO dto) {
        return evidenceReviewService.addReview(dto);
    }

    /**
     * 查询证据的所有审核记录
     */
    public List<EvidenceReviewVO> listEvidenceReviews(Long evidenceId) {
        return evidenceReviewService.listByEvidence(evidenceId);
    }

    /**
     * 添加证据流转记录
     */
    public Long addEvidenceTrace(EvidenceTraceDTO dto) {
        return evidenceTraceService.addTrace(dto);
    }

    /**
     * 查询证据流转记录
     */
    public List<EvidenceTraceVO> listEvidenceTraces(Long evidenceId) {
        return evidenceTraceService.listByEvidence(evidenceId);
    }

    /**
     * 添加证据附件
     */
    public Long addEvidenceAttachment(EvidenceAttachmentDTO dto) {
        return evidenceAttachmentService.addAttachment(dto);
    }

    /**
     * 删除证据附件
     */
    public void deleteEvidenceAttachment(Long attachmentId) {
        evidenceAttachmentService.deleteAttachment(attachmentId);
    }

    /**
     * 查询证据所有附件
     */
    public List<EvidenceAttachmentVO> listEvidenceAttachments(Long evidenceId) {
        return evidenceAttachmentService.listByEvidence(evidenceId);
    }

    /**
     * 添加证据标签
     */
    public Long addEvidenceTag(EvidenceTagRelationDTO dto) {
        return evidenceTagRelationService.addTagRelation(dto);
    }

    /**
     * 删除证据标签
     */
    public void deleteEvidenceTag(Long tagRelationId) {
        evidenceTagRelationService.deleteTagRelation(tagRelationId);
    }

    /**
     * 查询证据所有标签
     */
    public List<EvidenceTagRelationVO> listEvidenceTags(Long evidenceId) {
        return evidenceTagRelationService.listByEvidence(evidenceId);
    }

    /**
     * 批量删除证据
     */
    public void batchDeleteEvidence(List<Long> evidenceIds) {
        for (Long id : evidenceIds) {
            evidenceService.deleteEvidence(id);
        }
        // 操作日志（如有审计服务）
        if (auditService != null) {
            AuditLogDTO logDTO = AuditLogDTO.builder()
                .businessType(BusinessTypeEnum.CASE)
                .operateType(OperateTypeEnum.DELETE)
                .module("案件证据")
                .description("批量删除证据")
                .afterData(evidenceIds != null ? evidenceIds.toString() : null)
                .build();
            auditService.log(logDTO);
        }
    }

    /**
     * 批量新增证据
     */
    public List<Long> batchAddEvidence(List<EvidenceDTO> dtos) {
        List<Long> ids = new ArrayList<>();
        for (EvidenceDTO dto : dtos) {
            ids.add(evidenceService.addEvidence(dto));
        }
        if (auditService != null) {
            AuditLogDTO logDTO = AuditLogDTO.builder()
                .businessType(BusinessTypeEnum.CASE)
                .operateType(OperateTypeEnum.CREATE)
                .module("案件证据")
                .description("批量新增证据")
                .afterData(ids.toString())
                .build();
            auditService.log(logDTO);
        }
        return ids;
    }

    /**
     * 批量归档证据
     */
    public void batchArchiveEvidence(List<Long> evidenceIds) {
        for (Long id : evidenceIds) {
            evidenceService.archiveEvidence(id); // 需在evidenceService实现
        }
        if (auditService != null) {
            AuditLogDTO logDTO = AuditLogDTO.builder()
                .businessType(BusinessTypeEnum.CASE)
                .operateType(OperateTypeEnum.UPDATE)
                .module("案件证据")
                .description("批量归档证据")
                .afterData(evidenceIds.toString())
                .build();
            auditService.log(logDTO);
        }
    }

    /**
     * 批量添加证据标签
     */
    public void batchAddEvidenceTags(List<EvidenceTagRelationDTO> dtos) {
        for (EvidenceTagRelationDTO dto : dtos) {
            evidenceTagRelationService.addTagRelation(dto);
        }
        if (auditService != null) {
            AuditLogDTO logDTO = AuditLogDTO.builder()
                .businessType(BusinessTypeEnum.CASE)
                .operateType(OperateTypeEnum.CREATE)
                .module("案件证据")
                .description("批量添加证据标签")
                .afterData(dtos.toString())
                .build();
            auditService.log(logDTO);
        }
    }

    /**
     * 批量删除证据标签
     */
    public void batchDeleteEvidenceTags(List<Long> tagRelationIds) {
        for (Long id : tagRelationIds) {
            evidenceTagRelationService.deleteTagRelation(id);
        }
        if (auditService != null) {
            AuditLogDTO logDTO = AuditLogDTO.builder()
                .businessType(BusinessTypeEnum.CASE)
                .operateType(OperateTypeEnum.DELETE)
                .module("案件证据")
                .description("批量删除证据标签")
                .afterData(tagRelationIds.toString())
                .build();
            auditService.log(logDTO);
        }
    }

    /**
     * 批量导出证据
     */
    public byte[] exportEvidenceBatch(List<Long> evidenceIds) {
        List<EvidenceVO> evidences = new ArrayList<>();
        for (Long id : evidenceIds) {
            EvidenceVO vo = evidenceService.getEvidence(id);
            if (vo != null) {
                evidences.add(vo);
            }
        }
        // 构造表头
        List<List<String>> excelData = new ArrayList<>();
        List<String> header = List.of("证据ID", "案件ID", "证据名称", "类型", "来源", "证明事项", "提交人ID", "提交时间", "质证情况", "是否归档", "归档时间");
        excelData.add(header);
        // 填充数据
        for (EvidenceVO vo : evidences) {
            List<String> row = new ArrayList<>();
            row.add(String.valueOf(vo.getId()));
            row.add(String.valueOf(vo.getCaseId()));
            row.add(vo.getName());
            row.add(vo.getType() != null ? vo.getType().toString() : "");
            row.add(vo.getSource());
            row.add(vo.getProofMatter());
            row.add(vo.getSubmitterId() != null ? vo.getSubmitterId().toString() : "");
            row.add(vo.getSubmitTime() != null ? vo.getSubmitTime().toString() : "");
            row.add(vo.getChallengeStatus());
            row.add(vo.getArchived() != null && vo.getArchived() ? "是" : "否");
            row.add(vo.getArchiveTime() != null ? vo.getArchiveTime().toString() : "");
            excelData.add(row);
        }
        // 写入Excel到字节流
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ExcelWriter.write(excelData, out);
        byte[] fileBytes = out.toByteArray();
        if (auditService != null) {
            AuditLogDTO logDTO = AuditLogDTO.builder()
                .businessType(BusinessTypeEnum.CASE)
                .operateType(OperateTypeEnum.EXPORT)
                .module("案件证据")
                .description("批量导出证据")
                .afterData(evidenceIds.toString())
                .build();
            auditService.log(logDTO);
        }
        return fileBytes;
    }

    /**
     * 批量导出证据为PDF
     */
    public byte[] exportEvidenceBatchPdf(List<Long> evidenceIds) {
        List<EvidenceVO> evidences = new ArrayList<>();
        for (Long id : evidenceIds) {
            EvidenceVO vo = evidenceService.getEvidence(id);
            if (vo != null) {
                evidences.add(vo);
            }
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(out);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);
        String[] headers = {"证据ID", "案件ID", "证据名称", "类型", "来源", "证明事项", "提交人ID", "提交时间", "质证情况", "是否归档", "归档时间"};
        int pageSize = 20;
        int count = 0;
        Table table = new Table(headers.length);
        for (String header : headers) {
            table.addHeaderCell(new Cell().add(new Paragraph(header)));
        }
        for (EvidenceVO vo : evidences) {
            table.addCell(new Paragraph(String.valueOf(vo.getId())));
            table.addCell(new Paragraph(String.valueOf(vo.getCaseId())));
            table.addCell(new Paragraph(vo.getName()));
            table.addCell(new Paragraph(vo.getType() != null ? vo.getType().toString() : ""));
            table.addCell(new Paragraph(vo.getSource()));
            table.addCell(new Paragraph(vo.getProofMatter()));
            table.addCell(new Paragraph(vo.getSubmitterId() != null ? vo.getSubmitterId().toString() : ""));
            table.addCell(new Paragraph(vo.getSubmitTime() != null ? vo.getSubmitTime().toString() : ""));
            table.addCell(new Paragraph(vo.getChallengeStatus()));
            table.addCell(new Paragraph(vo.getArchived() != null && vo.getArchived() ? "是" : "否"));
            table.addCell(new Paragraph(vo.getArchiveTime() != null ? vo.getArchiveTime().toString() : ""));
            count++;
            if (count % pageSize == 0) {
                document.add(table);
                document.add(new AreaBreak(PageSize.A4));
                table = new Table(headers.length);
                for (String header : headers) {
                    table.addHeaderCell(new Cell().add(new Paragraph(header)));
                }
            }
        }
        if (count % pageSize != 0) {
            document.add(table);
        }
        document.close();
        // 加水印
        try {
            String watermarkText = "仅供内部使用";
            PdfFont font = PdfFontFactory.createFont("STSongStd-Light", "UniGB-UCS2-H", PdfFontFactory.EmbeddingStrategy.PREFER_NOT_EMBEDDED);
            for (int i = 1; i <= pdf.getNumberOfPages(); i++) {
                PdfPage page = pdf.getPage(i);
                PdfCanvas canvas = new PdfCanvas(page);
                canvas.saveState();
                canvas.setFontAndSize(font, 40);
                canvas.setColor(ColorConstants.LIGHT_GRAY, true);
                // 旋转和平移水印
                canvas.beginText();
                canvas.setTextMatrix((float)Math.cos(Math.PI/6), (float)Math.sin(Math.PI/6),
                                     (float)-Math.sin(Math.PI/6), (float)Math.cos(Math.PI/6),
                                     200, 400);
                canvas.showText(watermarkText);
                canvas.endText();
                canvas.restoreState();
            }
        } catch (Exception e) {
            // 忽略水印异常，保证主流程
        }
        byte[] fileBytes = out.toByteArray();
        if (auditService != null) {
            AuditLogDTO logDTO = AuditLogDTO.builder()
                .businessType(BusinessTypeEnum.CASE)
                .operateType(OperateTypeEnum.EXPORT)
                .module("案件证据")
                .description("批量导出证据PDF（分页+水印）")
                .afterData(evidenceIds.toString())
                .build();
            auditService.log(logDTO);
        }
        return fileBytes;
    }
} 