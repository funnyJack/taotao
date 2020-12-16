package com.taotao.service.impl;

import com.taotao.mapper.TbItemMapper;
import com.taotao.pojo.LayuiResult;
import com.taotao.pojo.TbItem;
import com.taotao.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

}