package com.taotao.service;

import com.taotao.pojo.TaotaoResult;

public interface ItemGroupService {
    TaotaoResult findTbItemGroupByCId(Long cId);

    /**
     * 根据商品分类id 添加商品规格参数组和规格参数项
     * @param cId
     * @param params
     * @return
     */
    TaotaoResult addGroup(Long cId, String params);
}
