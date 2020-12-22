package com.taotao.content.service;

import com.taotao.pojo.Ad1Node;
import com.taotao.pojo.LayuiResult;
import com.taotao.pojo.TbContent;
import com.taotao.pojo.ZtreeResult;

import java.util.List;

public interface ItemContentService {
    List<ZtreeResult> getZtreeResult(Long id);

    /**
     * 根据内容分类id查询内容信息
     * @param categoryId
     * @param page
     * @param limit
     * @return
     */
    LayuiResult findContentByCategoryId(Long categoryId, Integer page, Integer limit);

    /**
     * 根据内容id删除指定内容信息
     * @param tbContents
     * @param page
     * @param limit
     * @return
     */
    LayuiResult deleteContentByCategoryId(List<TbContent> tbContents, Integer page, Integer limit);

    /**
     * 新添加一个信息
     * @param tbContent
     * @param page
     * @param limit
     * @return
     */
    LayuiResult addContent(TbContent tbContent, Integer page, Integer limit);

    List<Ad1Node> showAd1Node();
}
