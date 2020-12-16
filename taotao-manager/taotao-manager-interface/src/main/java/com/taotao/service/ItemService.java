package com.taotao.service;

import com.taotao.pojo.LayuiResult;
import com.taotao.pojo.TbItem;

public interface ItemService {
    TbItem findTbTtemById(Long ItemId);
    LayuiResult findTbItemByPage(int page ,int limit);
}
