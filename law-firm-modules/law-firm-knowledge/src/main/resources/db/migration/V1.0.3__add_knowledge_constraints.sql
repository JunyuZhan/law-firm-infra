-- 添加知识模块与其他模块的外键约束

-- 添加知识文档与作者（员工）的外键约束
ALTER TABLE `knowledge_document` 
ADD CONSTRAINT `fk_knowledge_author` FOREIGN KEY (`author_id`) 
REFERENCES `personnel_employee` (`id`);

-- 添加知识文档与分类的外键约束
ALTER TABLE `knowledge_document` 
ADD CONSTRAINT `fk_knowledge_category` FOREIGN KEY (`category_id`) 
REFERENCES `knowledge_category` (`id`);

-- 添加知识标签关联与知识文档的外键约束
ALTER TABLE `knowledge_tag_relation` 
ADD CONSTRAINT `fk_tag_relation_knowledge` FOREIGN KEY (`knowledge_id`) 
REFERENCES `knowledge_document` (`id`);

-- 添加知识标签关联与标签的外键约束
ALTER TABLE `knowledge_tag_relation` 
ADD CONSTRAINT `fk_tag_relation_tag` FOREIGN KEY (`tag_id`) 
REFERENCES `knowledge_tag` (`id`);

-- 添加知识附件与知识文档的外键约束
ALTER TABLE `knowledge_attachment` 
ADD CONSTRAINT `fk_attachment_knowledge` FOREIGN KEY (`knowledge_id`) 
REFERENCES `knowledge_document` (`id`); 