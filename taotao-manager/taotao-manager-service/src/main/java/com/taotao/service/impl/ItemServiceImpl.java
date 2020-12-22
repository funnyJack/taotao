package com.taotao.service.impl;

import com.taotao.mapper.TbItemDescMapper;
import com.taotao.mapper.TbItemMapper;
import com.taotao.pojo.*;
import com.taotao.service.ItemService;
import com.taotao.utils.IDUtils;
import com.taotao.utils.UploadUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {
    @Autowired
    private TbItemMapper tbItemMapper;
    @Autowired
    private TbItemDescMapper tbItemDescMapper;

    public TbItem findTbTtemById(Long ItemId) {
        TbItem tbItem = tbItemMapper.findTbTtemById(ItemId);
        return tbItem;
    }

    @Override
    public LayuiResult findTbItemByPage(int page, int limit) {
        LayuiResult result = new LayuiResult();
        result.setCode(0);
        result.setMsg("");
        int count = tbItemMapper.findTbItemByCount();
        result.setCount(count);
        List<TbItem> data = tbItemMapper.finTbItemByPage((page-1)*limit,limit);
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
        int count = tbItemMapper.updateItemByIds(ids,type,date);
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
        int count = tbItemMapper.findTbItemByLikeConut(title,priceMin,priceMax,cId);
        //设置查询结果集的数量
        result.setCount(count);
        List<TbItem> data = tbItemMapper.findTbItemByLike(title,priceMin,priceMax,cId,(page-1)*limit,limit);
        result.setData(data);
        return result;
    }

    @Override
    public PictureResult addPicture(String fileName, byte[] bytes) {
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        String filePath = format.format(date);
        String filename = IDUtils.genImageName()+fileName.substring(fileName.lastIndexOf("."));
        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        boolean b = UploadUtils.savePicture(bis,filePath,filename);
        if (b){
            PictureResult result = new PictureResult();
            result.setCode(0);
            result.setMsg("");
            PictureData data = new PictureData();
            data.setSrc("http://localhost/"+filePath+"/"+filename);
            result.setData(data);
            System.out.println(data.getSrc());
            return result;
        }
        return null;
    }

    @Override
    public TaotaoResult addItem(TbItem tbItem, String itemDesc) {
//        生成一个商品id
        Long itemId = IDUtils.genItemId();
        Date date = new Date();
        tbItem.setId(itemId);
        tbItem.setStatus((byte) 1);
        tbItem.setCreated(date);
        tbItem.setUpdated(date);
//        商品基本信息准备完毕
        int i = tbItemMapper.addItem(tbItem);
        if (i<=0){
            return TaotaoResult.build(500,"添加商品基本信息失败");
        }
        TbItemDesc tbItemDesc = new TbItemDesc();
        tbItemDesc.setItemId(itemId);
        tbItemDesc.setCreated(date);
        tbItemDesc.setUpdated(date);
        tbItemDesc.setItemDesc(itemDesc);
//        商品描述信息准备完毕
        int j = tbItemDescMapper.addItemDesc(tbItemDesc);
        if (j<=0){
            return TaotaoResult.build(500,"添加商品描述信息失败");
        }
        return TaotaoResult.build(200,"添加商品描述信息成功");
    }

}