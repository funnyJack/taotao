package com.taotao.search.service;

import com.taotao.pojo.SearchItem;
import com.taotao.pojo.SearchResult;
import com.taotao.pojo.TaotaoResult;

public interface SearchService {
    TaotaoResult importSolr();

    /**
     * 根据页面传递过来的条件来搜索商品信息
     * @param query
     * @param page
     * @return
     */
    SearchResult findItemSearch(String query, Integer page);
    void addSearchItem(SearchItem item);
}
