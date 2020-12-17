package com.taotao.service;

import com.taotao.pojo.ZtreeResult;

import java.util.List;

//商品分类服务
public interface ItemCatService {
//    根据当前id作为参数查询这个分类下的子集
    List<ZtreeResult> getZtreeResult(Long id);
}
