package com.taotao.service;

import com.taotao.pojo.LayuiResult;
import com.taotao.pojo.TaotaoResult;
import com.taotao.pojo.TbItem;

import java.util.Date;
import java.util.List;

//商品服务
public interface ItemService {
    TbItem findTbTtemById(Long ItemId);
    LayuiResult findTbItemByPage(int page ,int limit);

    LayuiResult itemDelete(List<TbItem> tbItems);

    /**
     * 批量修改商品信息
     * @param tbItems 需要修改的商品对象
     * @param type  0：下架，1：上架，2：删除
     * @param date 修改日期
     * @return 页面需要的json格式，web会解析这个json，吧需要的数据展示到web上面
     */
    TaotaoResult updateItem(List<TbItem> tbItems, int type, Date date);

    LayuiResult getLikeItem(Integer page, Integer limit, String title, Integer priceMin, Integer priceMax, Long cId);
}
