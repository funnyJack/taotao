package com.taotao.service;

import com.taotao.pojo.*;

import java.util.Date;
import java.util.List;

//商品服务
public interface ItemService {
    TbItem findTbItemById(Long ItemId);
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

    PictureResult addPicture(String fileName, byte[] bytes);

    /**
     * 添加商品到数据库中
     * @param tbItem 商品基本信息
     * @param itemDesc 商品描述信息
     * @param paramKeyIds 规格参数项id集合
     * @param paramValue 规格参数值集合
     * @return 返回TaotaoResult
     */
    TaotaoResult addItem(TbItem tbItem, String itemDesc, List<Integer> paramKeyIds, List<String> paramValue);

    TbItemDesc findTbItemDescByItemId(Long itemId);

    /**
     * 根据商品id查询商品规格参数组和规格参数项和规格参数值信息
     * @param itemId 商品id
     * @return
     */
    List<TbItemParamGroup> findTbItemGroupByItemId(Long itemId);
}
