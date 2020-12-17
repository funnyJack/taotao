package com.taotao.service.impl;

import com.taotao.mapper.TbItemMapper;
import com.taotao.pojo.LayuiResult;
import com.taotao.pojo.TaotaoResult;
import com.taotao.pojo.TbItem;
import com.taotao.service.ItemService;
import org.apache.ibatis.jdbc.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {
    @Autowired
    private TbItemMapper itemMapper;

    public TbItem findTbTtemById(Long ItemId) {
        TbItem tbItem = itemMapper.findTbTtemById(ItemId);
        return tbItem;
    }

    @Override
    public LayuiResult findTbItemByPage(int page, int limit) {
        LayuiResult result = new LayuiResult();
        result.setCode(0);
        result.setMsg("");
        int count = itemMapper.findTbItemByCount();
        result.setCount(count);
        List<TbItem> data = itemMapper.finTbItemByPage((page-1)*limit,limit);
        result.setData(data);
        return result;
    }

    @Override
    public LayuiResult itemDelete(List<TbItem> tbItems) {
        return null;
    }

    @Override
    public TaotaoResult updateItem(List<TbItem> tbItems, int type, Date date) {
        if(tbItems.size()<=0){
            return TaotaoResult.build(500,"请先勾选，再操作",null);
        }
//        需要修改的商品id放入集合
        List<Long> ids = new ArrayList<Long>();
        for (TbItem tbItem: tbItems) {
            ids.add(tbItem.getId());
        }
        int count = itemMapper.updateItemByIds(ids,type,date);
//        count>0才表示我们修改了数据库里面的数据
        if(count>0&&type ==0 ){
            return TaotaoResult.build(200,"商品下架成功",null);

        }
        else if (count>0&&type ==1){
            return TaotaoResult.build(200,"商品上架成功",null);

        }
        else if (count>0&&type ==2){
            return TaotaoResult.build(200,"商品删除成功",null);
        }
        return TaotaoResult.build(500,"商品修改失败",null);
    }

    @Override
    public LayuiResult getLikeItem(Integer page, Integer limit, String title, Integer priceMin, Integer priceMax, Long cId) {
        if (priceMin == null){
            priceMin = 0;
        }
        if (priceMax == null){
            priceMax = 999999999;
        }

        LayuiResult result = new LayuiResult();
        result.setCode(0);
        result.setMsg("");
        int count = itemMapper.findTbItemByLikeConut(title,priceMin,priceMax,cId);
        //设置查询结果集的数量
        result.setCount(count);
        List<TbItem> data = itemMapper.findTbItemByLike(title,priceMin,priceMax,cId,(page-1)*limit,limit);
        result.setData(data);
        return result;
    }

}