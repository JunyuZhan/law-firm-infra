package com.lawfirm.model.document.index;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 文档索引实体
 */
@Data
@Accessors(chain = true)
@Document(indexName = "documents")
@Setting(settingPath = "elasticsearch/document-settings.json")
public class DocumentIndex {
    
    @Id
    private Long id;                  // 文档ID
    
    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String documentName;      // 文档名称
    
    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String content;           // 文档内容
    
    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String summary;           // 文档摘要
    
    @Field(type = FieldType.Keyword)
    private String documentNumber;    // 文档编号
    
    @Field(type = FieldType.Keyword)
    private String documentType;      // 文档类型
    
    @Field(type = FieldType.Long)
    private Long categoryId;          // 分类ID
    
    @Field(type = FieldType.Long)
    private Long caseId;             // 案件ID
    
    @Field(type = FieldType.Long)
    private Long contractId;          // 合同ID
    
    @Field(type = FieldType.Long)
    private Long clientId;            // 客户ID
    
    @Field(type = FieldType.Keyword)
    private List<String> tags;        // 标签
    
    @Field(type = FieldType.Keyword)
    private String status;            // 状态
    
    @Field(type = FieldType.Date)
    private LocalDateTime createTime; // 创建时间
    
    @Field(type = FieldType.Keyword)
    private String createBy;          // 创建人
    
    @Field(type = FieldType.Date)
    private LocalDateTime updateTime; // 更新时间
    
    @Field(type = FieldType.Keyword)
    private String updateBy;          // 更新人
} 