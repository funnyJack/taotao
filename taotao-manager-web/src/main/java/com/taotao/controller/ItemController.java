package com.taotao.controller;

import com.taotao.pojo.LayuiResult;
import com.taotao.pojo.PictureResult;
import com.taotao.pojo.TaotaoResult;
import com.taotao.pojo.TbItem;
import com.taotao.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/item")
public class ItemController {
    @Autowired
    private ItemService itemService;

    @RequestMapping("/{itemId}")
    @ResponseBody
    public TbItem findTbItem(@PathVariable Long itemId){
       TbItem result = itemService.findTbItemById(itemId);
        return result;
    }
    @RequestMapping("/showItemPage")
    @ResponseBody
    public LayuiResult showItemPage(Integer page,Integer limit){
        LayuiResult result = itemService.findTbItemByPage(page, limit);
        return result;
    }
    @RequestMapping("/itemDelete")
    @ResponseBody
    public TaotaoResult itemDelete(@RequestBody List<TbItem> tbItems){
        Date date = new Date();
        TaotaoResult result =  itemService.updateItem(tbItems,2,date);
        return result;
    }
    @RequestMapping("/commodityUpperShelves")
    @ResponseBody
    public TaotaoResult commodityUpperShelves(@RequestBody List<TbItem> tbItems){
        Date date = new Date();
        TaotaoResult result =  itemService.updateItem(tbItems,1,date);
        return result;
    }
    @RequestMapping("/commodityLowerShelves")
    @ResponseBody
    public TaotaoResult commodityLowerShelves(@RequestBody List<TbItem> tbItems){
        Date date = new Date();
        TaotaoResult result =  itemService.updateItem(tbItems,0,date);
        return result;
    }
    @RequestMapping("/searchItem")
    @ResponseBody
    public LayuiResult searchItem(Integer page,Integer limit,String title,Integer priceMin,Integer priceMax,Long cId){
        LayuiResult result = itemService.getLikeItem(page,limit,title,priceMin,priceMax,cId);
        return result;
    }
    @RequestMapping("/fileUpload")
    @ResponseBody
    public PictureResult fileUpload(MultipartFile file){
        try {
            byte[] bytes = file.getBytes();
            String fileName = file.getOriginalFilename();
            PictureResult result =  itemService.addPicture(fileName,bytes);
            return result;
        }catch (IOException e)
        {e.printStackTrace();}
    return null;
    }

    @RequestMapping("/addItem")
    @ResponseBody
    public TaotaoResult addItem(TbItem tbItem, String itemDesc, @RequestParam(value = "paramKeyIds[]",required = false) List<Integer> paramKeyIds,@RequestParam(value = "paramValue[]",required = false) List<String> paramValue ){
        TaotaoResult result =   itemService.addItem(tbItem,itemDesc,paramKeyIds,paramValue);
        return result;
    }
}
