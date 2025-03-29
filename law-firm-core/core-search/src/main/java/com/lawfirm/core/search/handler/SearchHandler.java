package com.lawfirm.core.search.handler;

import com.lawfirm.model.search.dto.search.SearchRequestDTO;
import com.lawfirm.model.search.vo.SearchVO;

import java.io.IOException;
import java.util.List;

/**
 * 搜索处理接口
 * 处理Lucene的搜索操作
 */
public interface SearchHandler {
    
    /**
     * 执行搜索
     *
     * @param request 搜索请求
     * @return 搜索结果
     * @throws IOException 如果处理过程中发生IO错误
     */
    SearchVO search(SearchRequestDTO request) throws IOException;
    
    /**
     * 获取搜索建议
     *
     * @param indexName 索引名称
     * @param field 字段名
     * @param text 关键词
     * @return 建议列表
     * @throws IOException 如果处理过程中发生IO错误
     */
    List<String> suggest(String indexName, String field, String text) throws IOException;
} 